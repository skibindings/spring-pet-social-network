package com.demo.socialnetwork.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.socialnetwork.documents.MongoDepartmentBlogs;
import com.demo.socialnetwork.documents.embedded.MongoBlog;
import com.demo.socialnetwork.entities.Department;
import com.demo.socialnetwork.entities.UserProfile;
import com.demo.socialnetwork.repos.DepartmentRepo;
import com.demo.socialnetwork.repos.MongoDepartmentBlogsRepo;
import com.demo.socialnetwork.repos.UserProfileRepo;

@Service
public class DepartmentServiceImpl implements DepartmentService {
	
	@Autowired
	private MongoDepartmentBlogsRepo mongoDepBlogsRepo;
	@Autowired
	private DepartmentRepo departmentRepo;
	@Autowired
	private UserProfileRepo userProfileRepo;
	
	@Override
	@Transactional(transactionManager = "transactionManager")
	public Department getDepartmentInfo(int id) {
		Optional<Department> departmentOpt = departmentRepo.findById(id);
		if(departmentOpt.isPresent()) {
			Department dep = departmentOpt.get();
			dep.getUsers();
			return dep;
		}
		return null;
	}

	@Override
	@Transactional(transactionManager = "transactionManager")
	public MongoDepartmentBlogs getDepartmentPosts(String mongoId) {
		return mongoDepBlogsRepo.findById(mongoId).get();
	}

	@Override
	@Transactional(transactionManager = "transactionManager")
	public void addBlog(int departmentId, MongoBlog blog) {
		Optional<Department> departmentOpt = departmentRepo.findById(departmentId);
		if(departmentOpt.isPresent()) {
			mongoDepBlogsRepo.addBlog(departmentOpt.get().getMongoId(), blog);
		}
	}

	@Override
	@Transactional(transactionManager = "transactionManager")
	public void updateDepartment(Department department) {
		departmentRepo.save(department);
		
	}

	@Override
	@Transactional(transactionManager = "transactionManager")
	public void addMember(int departmentId, String username) {
		Optional<Department> departmentOpt = departmentRepo.findById(departmentId);
		Optional<UserProfile> userProfileOpt = userProfileRepo.findById(username);
		if(departmentOpt.isPresent() && userProfileOpt.isPresent()) {
			Department department = departmentOpt.get();
			UserProfile userProfile = userProfileOpt.get();
			department.addUser(userProfile);
			departmentRepo.save(department);
		}
	}

	@Override
	public void removeMember(int departmentId, String username) {
		Optional<Department> departmentOpt = departmentRepo.findById(departmentId);
		//Optional<UserProfile> userProfileOpt = userProfileRepo.findById(username);
		if(departmentOpt.isPresent()) {
			Department department = departmentOpt.get();
			//UserProfile userProfile = userProfileOpt.get();
			department.rempoveUser(username);
			departmentRepo.save(department);
		}
	}
}
