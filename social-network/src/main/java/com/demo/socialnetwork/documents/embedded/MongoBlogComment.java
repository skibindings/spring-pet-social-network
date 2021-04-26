package com.demo.socialnetwork.documents.embedded;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class MongoBlogComment {
	
	private String username;
	
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private Date timestamp;
	
	private String text;

	public MongoBlogComment(String username, Date timestamp, String text) {
		super();
		this.username = username;
		this.timestamp = timestamp;
		this.text = text;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	
}
