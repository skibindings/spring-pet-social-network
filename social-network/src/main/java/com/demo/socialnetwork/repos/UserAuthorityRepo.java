package com.demo.socialnetwork.repos;

import org.springframework.stereotype.Repository;

import com.demo.socialnetwork.entities.UserAuthority;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserAuthorityRepo extends JpaRepository<UserAuthority, String>{
	
	public long deleteByUsernameAndAuthority(String username, String authority);
	
	public long deleteByUsername(String username);
}
