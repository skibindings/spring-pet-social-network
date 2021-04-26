package com.demo.socialnetwork.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "user_profiles")
public class UserProfile {
	
	@Id
	@Column(name = "username")
	private String username;
	
	@Column(name = "mongo_id")
	private String mongoId;
	
	@Column(name = "name")
	@NotBlank(message = "Обязательное поле!")
	private String name;
	
	@Column(name = "surname")
	@NotBlank(message = "Обязательное поле!")
	private String surname;
	
	@Basic
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull(message = "Обязательное поле!")
	@Past(message = "Неверная дата!")
	private Date birthdate;
	
	@Column(name = "email")
	@NotBlank(message = "Обязательное поле!")
	private String email;
	
	@Column(name = "phone_number")
	@Pattern(regexp="^[0-9]{11}",message="Неверный телефонный номер")  
	private String phoneNumber;
	
	@Column(name = "city")
	private String city;
	
	@Column(name = "about")
	private String about;
	
	@ManyToOne
	@JoinColumn(name="dep_id")
	private Department dep;

	@OneToMany(mappedBy="to", cascade = CascadeType.ALL)
	private List<FriendRequest> friendRequestsToMe;
	
	@OneToMany(mappedBy="from", cascade = CascadeType.ALL)
	private List<FriendRequest> friendRequestsFromMe;
	
	@ManyToMany
	@JoinTable(
			name = "friends",
			joinColumns = @JoinColumn(name = "user_1"),
			inverseJoinColumns = @JoinColumn(name = "user_2") 
			)
	private List<UserProfile> friends;
	
	public UserProfile() {
		phoneNumber = null;
		city = null;
		about = null;
	}

	public UserProfile(String username, String mongoId, String name, String surname, Date birthdate, String email,
			String phoneNumber, String city, String about, Department dep, List<FriendRequest> friendRequestsToMe,
			List<FriendRequest> friendRequestsFromMe, List<UserProfile> friends) {
		super();
		this.username = username;
		this.mongoId = mongoId;
		this.name = name;
		this.surname = surname;
		this.birthdate = birthdate;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.city = city;
		this.about = about;
		this.dep = dep;
		this.friendRequestsToMe = friendRequestsToMe;
		this.friendRequestsFromMe = friendRequestsFromMe;
		this.friends = friends;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMongoId() {
		return mongoId;
	}

	public void setMongoId(String mongoId) {
		this.mongoId = mongoId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public Department getDep() {
		return dep;
	}

	public void setDep(Department dep) {
		this.dep = dep;
	}

	public List<FriendRequest> getFriendRequestsToMe() {
		return friendRequestsToMe;
	}

	public void setFriendRequestsToMe(List<FriendRequest> friendRequestsToMe) {
		this.friendRequestsToMe = friendRequestsToMe;
	}

	public List<FriendRequest> getFriendRequestsFromMe() {
		return friendRequestsFromMe;
	}

	public void setFriendRequestsFromMe(List<FriendRequest> friendRequestsFromMe) {
		this.friendRequestsFromMe = friendRequestsFromMe;
	}

	public List<UserProfile> getFriends() {
		return friends;
	}

	public void setFriends(List<UserProfile> friends) {
		this.friends = friends;
	}
}
