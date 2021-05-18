package com.demo.socialnetwork.entities;

import java.sql.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "friend_requests")
public class FriendRequest {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "message")
	private String message;
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
	@JoinColumn(name="from_user")
	private UserProfile from;
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
	@JoinColumn(name="to_user")
	private UserProfile to;
	
	public FriendRequest() {
		
	}

	public FriendRequest(String message, UserProfile from, UserProfile to) {
		super();
		this.message = message;
		this.from = from;
		this.to = to;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public UserProfile getFrom() {
		return from;
	}

	public void setFrom(UserProfile from) {
		this.from = from;
	}
	
	public String getFromUsername() {
		if(from != null) {
			return from.getUsername();
		}
		return "";
	}

	public UserProfile getTo() {
		return to;
	}

	public void setTo(UserProfile to) {
		this.to = to;
	}
	
	public String getToUsername() {
		if(to != null) {
			return to.getUsername();
		}
		return "";
	}

}
