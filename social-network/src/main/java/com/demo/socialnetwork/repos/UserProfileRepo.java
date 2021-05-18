package com.demo.socialnetwork.repos;

import org.springframework.stereotype.Repository;

import com.demo.socialnetwork.entities.UserProfile;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserProfileRepo extends JpaRepository<UserProfile, String>{
	
	List<UserProfile> findByNameContainingIgnoreCase(String searchInName);
	
	List<UserProfile> findBySurnameContainingIgnoreCase(String searchInSurname);
}
