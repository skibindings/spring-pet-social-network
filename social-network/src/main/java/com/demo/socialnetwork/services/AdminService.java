package com.demo.socialnetwork.services;

public interface AdminService {
	
	public void deleteUserProfile(String username);
	
	public void promoteUser(String username);
	
	public void demoteUser(String username);
}
