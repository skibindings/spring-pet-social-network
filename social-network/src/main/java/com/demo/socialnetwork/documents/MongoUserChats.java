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
	
	private int newPmsCounter;
	
	public MongoUserChats() {
		chats = new ArrayList<MongoChat>();
		newPmsCounter = 0;
	}

	public MongoUserChats(List<MongoChat> chats, int newPmsCounter) {
		super();
		this.chats = chats;
		this.newPmsCounter = newPmsCounter;
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

	public int getNewPmsCounter() {
		return newPmsCounter;
	}

	public void setNewPmsCounter(int newPmsCounter) {
		this.newPmsCounter = newPmsCounter;
	}
}
