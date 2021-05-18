package com.demo.socialnetwork.services;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.socialnetwork.controllers.models.ChatWithUserProfileModel;
import com.demo.socialnetwork.controllers.models.RegisterModel;
import com.demo.socialnetwork.documents.MongoDepartmentBlogs;
import com.demo.socialnetwork.documents.MongoUserChats;
import com.demo.socialnetwork.documents.embedded.MongoChat;
import com.demo.socialnetwork.documents.embedded.MongoPersonalMessage;
import com.demo.socialnetwork.entities.Department;
import com.demo.socialnetwork.entities.FriendRequest;
import com.demo.socialnetwork.entities.UserAuthority;
import com.demo.socialnetwork.entities.UserProfile;
import com.demo.socialnetwork.repos.DepartmentRepo;
import com.demo.socialnetwork.repos.FriendRequestRepo;
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
	private FriendRequestRepo friendRequestsRepo;
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

	@Override
	@Transactional(transactionManager = "transactionManager")
	public void sendFriendRequest(String usernameFrom, String usernameTo) {
		Optional<UserProfile> userFromOpt = userProfileRepo.findById(usernameFrom);
		Optional<UserProfile> userToOpt = userProfileRepo.findById(usernameTo);
		
		if(userFromOpt.isPresent() && userToOpt.isPresent()) {
			UserProfile userFrom = userFromOpt.get();
			UserProfile userTo = userToOpt.get();
			
			userFrom.getFriendRequestsFromMe();
			userTo.getFriendRequestsToMe();
			
			FriendRequest friendRequest = new FriendRequest("", userFrom, userTo);
			friendRequestsRepo.save(friendRequest);
		}
		
	}

	@Override
	@Transactional(transactionManager = "transactionManager")
	public void declineFriendRequest(String username, String usernameRequest) {
		Optional<UserProfile> userOpt = userProfileRepo.findById(username);
		Optional<UserProfile> userRequestOpt = userProfileRepo.findById(usernameRequest);
		
		if(userOpt.isPresent() && userRequestOpt.isPresent()) {
			UserProfile user = userOpt.get();
			UserProfile userRequest = userRequestOpt.get();
			
			userRequest.getFriendRequestsFromMe();
			user.getFriendRequestsToMe();
			
			friendRequestsRepo.deleteByFromAndTo(userRequest, user);
		}
	}

	@Override
	@Transactional(transactionManager = "transactionManager")
	public void confirmFriendRequest(String username, String usernameRequest) {
		Optional<UserProfile> userOpt = userProfileRepo.findById(username);
		Optional<UserProfile> userRequestOpt = userProfileRepo.findById(usernameRequest);
		
		if(userOpt.isPresent() && userRequestOpt.isPresent()) {
			UserProfile user = userOpt.get();
			UserProfile userRequest = userRequestOpt.get();

			user.getFriendRequestsToMe();
			user.getFriends();
			userRequest.getFriendRequestsFromMe();
			userRequest.getFriends();
			
			friendRequestsRepo.deleteByFromAndTo(userRequest, user);
			user.addFriend(userRequest);
			userRequest.addFriend(user);
			
			userProfileRepo.save(user);
			userProfileRepo.save(userRequest);
		}
		
	}

	@Override
	@Transactional(transactionManager = "transactionManager")
	public void deleteFriendPair(String username1, String username2) {
		Optional<UserProfile> user1Opt = userProfileRepo.findById(username1);
		Optional<UserProfile> user2Opt = userProfileRepo.findById(username2);
		
		if(user1Opt.isPresent() && user2Opt.isPresent()) {
			UserProfile user1 = user1Opt.get();
			UserProfile user2 = user2Opt.get();
			
			user1.removeFriend(user2);
			user2.removeFriend(user1);
			
			userProfileRepo.save(user1);
			userProfileRepo.save(user2);
		}
	}

	@Override
	@Transactional(transactionManager = "transactionManager")
	public List<ChatWithUserProfileModel> getAllChatsWithProfilesSortedByLastMessageDate(String mongoId) {
		Optional<MongoUserChats> userChatsOpt = mongoUserChatsRepo.findById(mongoId);
		
		if(userChatsOpt.isPresent()) {
			MongoUserChats userChats = userChatsOpt.get();
			List<MongoChat> chats = userChats.getChats();
			chats.sort((MongoChat c1, MongoChat c2) -> c2.getLastMessageTimestamp().compareTo(c1.getLastMessageTimestamp()));
			
			List<ChatWithUserProfileModel> chatsWithProfiles = chats.stream().collect(
				    Collectors.mapping(
				    	      p -> new ChatWithUserProfileModel(p,userProfileRepo.findById(p.getCorrespUsername()).get()),
				    	      Collectors.toList()));
			
			return chatsWithProfiles;
		}
		
		return null;
	}

	@Override
	@Transactional(transactionManager = "transactionManager")
	public ChatWithUserProfileModel getChatWithProfileByCorrespUsername(String mongoId, String username) {
		List<ChatWithUserProfileModel> chatsWithProfiles = getAllChatsWithProfilesSortedByLastMessageDate(mongoId);
		ChatWithUserProfileModel chatWithProfile = chatsWithProfiles.stream()
		  .filter(cp -> cp.getChat().getCorrespUsername().equals(username))
		  .findAny()
		  .orElse(null);
		return chatWithProfile;
	}

	@Override
	@Transactional(transactionManager = "transactionManager")
	public Set<UserProfile> searchUsers(String searchQuery) {
		List<UserProfile> searchedByName = userProfileRepo.findByNameContainingIgnoreCase(searchQuery);
		List<UserProfile> searchedBySurame = userProfileRepo.findBySurnameContainingIgnoreCase(searchQuery);
		
		Set<UserProfile> searchedUsers = new HashSet<UserProfile>();
		searchedByName.forEach(c -> searchedUsers.add(c));
		searchedBySurame.forEach(c -> searchedUsers.add(c));
		
		return searchedUsers;
	}
	
	
}
