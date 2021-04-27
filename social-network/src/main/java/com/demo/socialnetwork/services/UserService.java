package com.demo.socialnetwork.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.socialnetwork.controllers.models.RegisterModel;
import com.demo.socialnetwork.documents.MongoDepartmentBlogs;
import com.demo.socialnetwork.entities.Department;
import com.demo.socialnetwork.entities.UserProfile;
import com.demo.socialnetwork.repos.MongoUserChatsRepo;
import com.demo.socialnetwork.repos.UserSecurityRepo;

public interface UserService {
	
	public List<UserProfile> getAllUserProfiles();
	
	public UserProfile getUserProfile (String username);
	
	public void updateUserProfile(UserProfile updatedUserProfile);
	
	public void sendMessage(String usernameFrom, String usernameTo, String text);
	
	public void createChat(String username1, String username2);
	
	public boolean chatExists(String username1, String username2);
}
