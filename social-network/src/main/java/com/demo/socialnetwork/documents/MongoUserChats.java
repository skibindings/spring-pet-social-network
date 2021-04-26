package com.demo.socialnetwork.documents;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

import com.demo.socialnetwork.documents.embedded.MongoChat;

@Document(collection = "users_chats")
public class MongoUserChats {
	
	@Id
	private String id;
	
	private List<MongoChat> chats;
	
	public MongoUserChats() {
		chats = new ArrayList<MongoChat>();
	}

	public MongoUserChats(List<MongoChat> chats) {
		super();
		this.chats = chats;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<MongoChat> getChats() {
		return chats;
	}

	public void setChats(List<MongoChat> chats) {
		this.chats = chats;
	}
}
