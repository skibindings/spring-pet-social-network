package com.demo.socialnetwork.documents.embedded;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class MongoPersonalMessage {
	
	private boolean reciept;
	
	//@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date timestamp;
	
	private String text;
	
	public MongoPersonalMessage() {
		text = "";
	}

	public MongoPersonalMessage(boolean reciept, Date timestamp, String text) {
		super();
		this.reciept = reciept;
		this.timestamp = timestamp;
		this.text = text;
	}

	public boolean isReciept() {
		return reciept;
	}

	public void setReciept(boolean reciept) {
		this.reciept = reciept;
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
