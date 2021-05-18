package com.demo.socialnetwork.documents.embedded;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MongoChat {
	
	private String correspUsername; 
	
	private List<MongoPersonalMessage> pms;
	
	public MongoChat() {
		pms = new ArrayList<MongoPersonalMessage>();
	}

	public MongoChat(String correspUsername) {
		this.correspUsername = correspUsername;
		this.pms = new ArrayList<MongoPersonalMessage>();
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
	
	public Date getLastMessageTimestamp() {
		if(!pms.isEmpty()) {
			return pms.get(pms.size()-1).getTimestamp();
		}
		return new Date(1999,12,12);
	}
}
