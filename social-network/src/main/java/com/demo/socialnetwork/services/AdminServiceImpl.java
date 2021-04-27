package com.demo.socialnetwork.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.socialnetwork.documents.MongoDepartmentBlogs;
import com.demo.socialnetwork.entities.Department;
import com.demo.socialnetwork.entities.UserAuthority;
import com.demo.socialnetwork.entities.UserProfile;
import com.demo.socialnetwork.repos.DepartmentRepo;
import com.demo.socialnetwork.repos.MongoDepartmentBlogsRepo;
import com.demo.socialnetwork.repos.MongoUserChatsRepo;
import com.demo.socialnetwork.repos.UserAuthorityRepo;
import com.demo.socialnetwork.repos.UserProfileRepo;
import com.demo.socialnetwork.repos.UserSecurityRepo;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private UserSecurityRepo userSecRepo;
	@Autowired
	private UserAuthorityRepo userAuthRepo;
	@Autowired
	private UserProfileRepo userProfileRepo;
	@Autowired
	private MongoUserChatsRepo mongoUserChatsRepo;
	@Autowired
	private MongoDepartmentBlogsRepo mongoDepBlogsRepo;
	@Autowired
	private DepartmentRepo departmentRepo;
	
	@Override
	@Transactional(transactionManager = "transactionManager")
	public void deleteUserProfile(String username) {
		Optional<UserProfile> userOpt = userProfileRepo.findById(username);
		if(userOpt.isPresent()) {
			UserProfile user = userOpt.get();
			if(user.isManager()) {
				Department dep = user.getDep();
				dep.getUsers();
				dep.removeAllUsers();
				dep.setLeader(null);
				mongoDepBlogsRepo.deleteById(dep.getMongoId());
				departmentRepo.deleteById(dep.getId());
			}
			mongoUserChatsRepo.deleteById(user.getMongoId());
			userSecRepo.deleteById(username);
			userProfileRepo.deleteById(username);
			userAuthRepo.deleteByUsername(username);
		}
	}

	@Override
	@Transactional(transactionManager = "transactionManager")
	public void promoteUser(String username) {
		Optional<UserProfile> userOpt = userProfileRepo.findById(username);
		if(userOpt.isPresent()) {
			
			UserProfile user = userOpt.get();
			
			if(!user.isManager()) {
				UserAuthority newUserAuthority = new UserAuthority(user.getUsername(),"ROLE_MANAGER");
				userAuthRepo.save(newUserAuthority);
				
				Department dep = new Department("Новый департамент","Пустое описание");
				
				MongoDepartmentBlogs depBlogs = new MongoDepartmentBlogs();
				mongoDepBlogsRepo.save(depBlogs);
				
				dep.setMongoId(depBlogs.getId());
				dep.setLeader(user);
				
				departmentRepo.save(dep);
				
				dep.addUser(user);
				
				departmentRepo.save(dep);
				
				//userProfileRepo.save(user);
			}
		}
	}

	@Override
	@Transactional(transactionManager = "transactionManager")
	public void demoteUser(String username) {
		Optional<UserProfile> userOpt = userProfileRepo.findById(username);
		if(userOpt.isPresent()) {
			UserProfile user = userOpt.get();
			if(user.isManager()) {
				userAuthRepo.deleteByUsernameAndAuthority(user.getUsername(),"ROLE_MANAGER");

				Department dep = user.getDep();
				dep.getUsers();
				dep.removeAllUsers();
				dep.setLeader(null);
				
				mongoDepBlogsRepo.deleteById(dep.getMongoId());
				departmentRepo.deleteById(dep.getId());
			}
		}
	}
}
