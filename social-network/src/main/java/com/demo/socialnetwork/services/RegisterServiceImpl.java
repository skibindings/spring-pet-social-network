package com.demo.socialnetwork.services;

import java.util.Arrays;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.socialnetwork.controllers.models.RegisterModel;
import com.demo.socialnetwork.documents.MongoUserChats;
import com.demo.socialnetwork.documents.embedded.MongoChat;
import com.demo.socialnetwork.documents.embedded.MongoPersonalMessage;
import com.demo.socialnetwork.entities.UserAuthority;
import com.demo.socialnetwork.repos.MongoUserChatsRepo;
import com.demo.socialnetwork.repos.UserAuthorityRepo;
import com.demo.socialnetwork.repos.UserProfileRepo;
import com.demo.socialnetwork.repos.UserSecurityRepo;

@Service
public class RegisterServiceImpl implements RegisterService {

	@Autowired
	private UserSecurityRepo userSecRepo;
	@Autowired
	private UserAuthorityRepo userAuthRepo;
	@Autowired
	private UserProfileRepo userProfileRepo;
	@Autowired
	private MongoUserChatsRepo mongoUserChatsRepo;

	@Override
	@Transactional(transactionManager = "transactionManager")
	public int registerUser(RegisterModel newUser) {
		
		if(userSecRepo.existsById(newUser.getUserSecurity().getUsername())) {
			return 1; // user with such a login exists
		}
		
		newUser.getUserSecurity().setEnabled(true);
		
		newUser.getUserProfile().setUsername(newUser.getUserSecurity().getUsername());
		
		UserAuthority newUserAuthority = new UserAuthority(newUser.getUserSecurity().getUsername(),"ROLE_EMPLOYEE");
		
		String password_unprocessed = newUser.getUserSecurity().getPassword();
		String password_processed = "{noop}"+password_unprocessed;
		newUser.getUserSecurity().setPassword(password_processed);
		
		MongoUserChats userChats = new MongoUserChats();
		mongoUserChatsRepo.save(userChats);
		
		newUser.getUserProfile().setMongoId(userChats.getId());
		
		userSecRepo.save(newUser.getUserSecurity());
		userAuthRepo.save(newUserAuthority);
		userProfileRepo.save(newUser.getUserProfile());
		
		return 0;
	}
}
