package com.demo.socialnetwork.controllers;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.socialnetwork.entities.UserSecurity;
import com.demo.socialnetwork.documents.MongoUserChats;
import com.demo.socialnetwork.documents.embedded.MongoChat;
import com.demo.socialnetwork.documents.embedded.MongoPersonalMessage;
import com.demo.socialnetwork.entities.UserAuthority;
import com.demo.socialnetwork.repos.MongoUserChatsRepo;
import com.demo.socialnetwork.repos.UserSecurityRepo;

@RestController
@RequestMapping("/rest")
public class TestRestController {
	
	@Autowired
	private UserSecurityRepo secUserRepo;
	@Autowired
	private MongoUserChatsRepo mongoUserChatsRepo;

	@GetMapping("/security_users")
	public Iterable<UserSecurity> getAllUsersSecurity() {
		return secUserRepo.findAll();
	}
	

	@GetMapping("/get_auths")
	public Iterable<UserAuthority> getMaryAuths() {
		return secUserRepo.findById("mary").get().getAuthorities();
	}
	
	@GetMapping("/create")
	public Iterable<UserSecurity> createUserSecurity() {
		secUserRepo.save(new UserSecurity("lisiyrak2","fox333",true));
		return null;
	}
	
	@GetMapping("/mongo_test")
	public Iterable<MongoUserChats> createUserChatsAndReturn() {
		MongoPersonalMessage msg1 = new MongoPersonalMessage(false, new Date(), "привет");
		MongoChat chat = new MongoChat("mary", Arrays.asList(msg1));
		MongoUserChats userChats = new MongoUserChats(Arrays.asList(chat));
		mongoUserChatsRepo.save(userChats);
		return mongoUserChatsRepo.findAll();
	}
}
