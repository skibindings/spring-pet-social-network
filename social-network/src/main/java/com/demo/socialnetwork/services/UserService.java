package com.demo.socialnetwork.services;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.socialnetwork.controllers.models.ChatWithUserProfileModel;
import com.demo.socialnetwork.controllers.models.RegisterModel;
import com.demo.socialnetwork.documents.MongoDepartmentBlogs;
import com.demo.socialnetwork.documents.embedded.MongoChat;
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
	
	public void sendFriendRequest(String usernameFrom, String usernameTo);
	
	public void declineFriendRequest(String username, String usernameRequest);
	
	public void confirmFriendRequest(String username, String usernameRequest);
	
	public void deleteFriendPair(String username1, String username2);
	
	public List<ChatWithUserProfileModel> getAllChatsWithProfilesSortedByLastMessageDate(String mongoId);

	public ChatWithUserProfileModel getChatWithProfileByCorrespUsername(String mongoId, String username);
	
	public Set<UserProfile> searchUsers(String searchQuery);
}
