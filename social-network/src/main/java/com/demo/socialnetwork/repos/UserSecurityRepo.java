package com.demo.socialnetwork.repos;

import org.springframework.stereotype.Repository;

import com.demo.socialnetwork.entities.UserSecurity;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserSecurityRepo extends JpaRepository<UserSecurity, String>{
	
}
