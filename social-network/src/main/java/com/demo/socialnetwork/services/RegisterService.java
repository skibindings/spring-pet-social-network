package com.demo.socialnetwork.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.socialnetwork.controllers.models.RegisterModel;
import com.demo.socialnetwork.repos.MongoUserChatsRepo;
import com.demo.socialnetwork.repos.UserSecurityRepo;

public interface RegisterService {
	
	public int registerUser(RegisterModel newUser);
}
