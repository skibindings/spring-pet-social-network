package com.demo.socialnetwork.entities;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "departments")
public class Department {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "mongo_id")
	private String mongoId;
	
	@Column(name = "name")
	@NotBlank(message = "Обязательное поле!")
	private String name;
	
	@Column(name = "description")
	@NotBlank(message = "Обязательное поле!")
	private String description;
	
	@OneToMany(mappedBy = "dep", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
	private List<UserProfile> users;
	
	@OneToOne
	@JoinColumn(name = "leader_username")
	private UserProfile leader;
	
	public Department() {
		users = new ArrayList<UserProfile>();
	}
	
	public Department(String name, String description) {
		this();
		
		this.mongoId = mongoId;
		this.name = name;
		this.description = description;
	}

	public Department(String name, String description, List<UserProfile> users) {
		this.mongoId = mongoId;
		this.name = name;
		this.description = description;
		this.users = users;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<UserProfile> getUsers() {
		return users;
	}

	public void setUsers(List<UserProfile> users) {
		this.users = users;
	}

	public UserProfile getLeader() {
		return leader;
	}

	public void setLeader(UserProfile leader) {
		this.leader = leader;
	}
	
	public void addUser(UserProfile newUser) {
		if(users == null) {
			users = new ArrayList<UserProfile>();
		}
		
		users.add(newUser);
		newUser.setDep(this);
	}
	
	public void rempoveUser(String username) {
		users.removeIf(x -> {
		    if (!x.getUsername().equals(username))
		        return false;
		    x.setDep(null);
		    return true;
		});
	}
	
	
	public void removeAllUsers() {
		users.forEach(s -> s.setDep(null));
		users.clear();
	}
}
