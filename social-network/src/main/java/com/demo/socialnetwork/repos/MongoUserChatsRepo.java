package com.demo.socialnetwork.repos;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.demo.socialnetwork.documents.MongoUserChats;

public interface MongoUserChatsRepo extends MongoRepository<MongoUserChats, String> {

}
