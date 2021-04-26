package com.demo.socialnetwork.documents.embedded;

import java.util.ArrayList;
import java.util.List;

public class MongoChat {
	
	private String correspUsername; 
	
	private List<MongoPersonalMessage> pms;
	
	public MongoChat() {
		pms = new ArrayList<MongoPersonalMessage>();
	}

	public MongoChat(String correspUsername, List<MongoPersonalMessage> pms) {
		this.correspUsername = correspUsername;
		this.pms = pms;
	}

	public String getCorrespUsername() {
		return correspUsername;
	}

	public void setCorrespUsername(String correspUsername) {
		this.correspUsername = correspUsername;
	}

	public List<MongoPersonalMessage> getPms() {
		return pms;
	}

	public void setPms(List<MongoPersonalMessage> pms) {
		this.pms = pms;
	}
}
