package com.demo.socialnetwork.documents.embedded;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

public class MongoBlog {
	
	//@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date timestamp;
	
	private String text;
	
	private List<String> likeUsernames;
	
	private List<MongoBlogComment> comments;
	
	public MongoBlog() {
		likeUsernames = new ArrayList<String>();
		comments = new ArrayList<MongoBlogComment>();
	}

	public MongoBlog(Date timestamp, String text, List<String> likeUsernames, List<MongoBlogComment> comments) {
		super();
		this.timestamp = timestamp;
		this.text = text;
		this.likeUsernames = likeUsernames;
		this.comments = comments;
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

	public List<String> getLikeUsernames() {
		return likeUsernames;
	}

	public void setLikeUsernames(List<String> likeUsernames) {
		this.likeUsernames = likeUsernames;
	}

	public List<MongoBlogComment> getComments() {
		return comments;
	}

	public void setComments(List<MongoBlogComment> comments) {
		this.comments = comments;
	}
}
