package com.demo.socialnetwork.controllers.models;

import javax.persistence.Entity;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.demo.socialnetwork.entities.UserProfile;
import com.demo.socialnetwork.entities.UserSecurity;

public class RegisterModel {
	
	@Valid
	private UserSecurity userSecurity;
	
	@Valid
	private UserProfile userProfile;
	
	public RegisterModel() {
		userSecurity = new UserSecurity();
		userProfile = new UserProfile();
	}

	public RegisterModel(UserSecurity userSecurity, UserProfile userProfile) {
		this.userSecurity = userSecurity;
		this.userProfile = userProfile;
	}

	public UserSecurity getUserSecurity() {
		return userSecurity;
	}

	public void setUserSecurity(UserSecurity userSecurity) {
		this.userSecurity = userSecurity;
	}

	public UserProfile getUserProfile() {
		return userProfile;
	}
	
	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}
}
