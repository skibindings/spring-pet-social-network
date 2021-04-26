package com.demo.socialnetwork.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.socialnetwork.controllers.models.RegisterModel;
import com.demo.socialnetwork.entities.UserAuthority;
import com.demo.socialnetwork.repos.MongoUserChatsRepo;
import com.demo.socialnetwork.repos.UserSecurityRepo;

@Service
public class RegisterServiceImpl implements RegisterService {

	@Autowired
	private UserSecurityRepo secUserRepo;
	@Autowired
	private MongoUserChatsRepo mongoUserChatsRepo;
	
	@Override
	public int registerUser(RegisterModel newUser) {
		if(secUserRepo.existsById(newUser.getUserSecurity().getUsername())) {
			return 1; // user with such a login exists
		}
		newUser.getUserProfile().setUsername(newUser.getUserSecurity().getUsername());
		UserAuthority newUserAuthority = new UserAuthority(newUser.getUserSecurity().getUsername(),"ROLE_EMPLOYEE");
		
		return 0;
	}
}
