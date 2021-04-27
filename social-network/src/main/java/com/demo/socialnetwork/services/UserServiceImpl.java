package com.demo.socialnetwork.services;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.socialnetwork.controllers.models.RegisterModel;
import com.demo.socialnetwork.documents.MongoDepartmentBlogs;
import com.demo.socialnetwork.documents.MongoUserChats;
import com.demo.socialnetwork.documents.embedded.MongoChat;
import com.demo.socialnetwork.documents.embedded.MongoPersonalMessage;
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
public class UserServiceImpl implements UserService {

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
	public List<UserProfile> getAllUserProfiles() {
		return userProfileRepo.findAll();
	}

	@Override
	@Transactional(transactionManager = "transactionManager")
	public UserProfile getUserProfile (String username) {
		Optional<UserProfile> userProfileOpt = userProfileRepo.findById(username);
		
		if(userProfileOpt.isPresent()) {
			UserProfile userProfile = userProfileOpt.get();
			userProfile.getFriendRequestsToMe();
			userProfile.getFriendRequestsFromMe();
			userProfile.getFriends();
			userProfile.getSecurity().getAuthorities();
			return userProfileOpt.get();
		}
		
		return null;
	}

	@Override
	public void updateUserProfile(UserProfile updatedUserProfile) {
		userProfileRepo.save(updatedUserProfile);
	}

	@Override
	@Transactional(transactionManager = "transactionManager")
	public void sendMessage(String usernameFrom, String usernameTo, String text) {
		Optional<UserProfile> fromUserOpt = userProfileRepo.findById(usernameFrom);
		Optional<UserProfile> toUserOpt = userProfileRepo.findById(usernameTo);
		
		if(fromUserOpt.isPresent() && toUserOpt.isPresent()) {
			mongoUserChatsRepo.sendPersonalMessage(fromUserOpt.get(), toUserOpt.get(), text);
		}
	}
	
	@Override
	@Transactional(transactionManager = "transactionManager")
	public void createChat(String username1, String username2) {
		Optional<UserProfile> user1Opt = userProfileRepo.findById(username1);
		Optional<UserProfile> user2Opt = userProfileRepo.findById(username2);
		
		if(user1Opt.isPresent() && user2Opt.isPresent()) {
			mongoUserChatsRepo.createChat(user1Opt.get(), user2Opt.get());
		}
	}
	
	@Override
	@Transactional(transactionManager = "transactionManager")
	public boolean chatExists(String username1, String username2) {
		Optional<UserProfile> user1Opt = userProfileRepo.findById(username1);
		Optional<UserProfile> user2Opt = userProfileRepo.findById(username2);
		
		if(user1Opt.isPresent() && user2Opt.isPresent()) {
			return mongoUserChatsRepo.chatExists(user1Opt.get(), user2Opt.get());
		}
		return false;
	}
}
