package com.demo.socialnetwork.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "users")
public class UserSecurity {
	
	@Id
	@Column(name = "username")
	@NotBlank(message = "Обязательное поле!")
	private String username;
	
	@Column(name = "password")
	@NotBlank(message = "Обязательное поле!")
	private String password;
	
	@Column(name = "enabled", nullable = false, columnDefinition = "TINYINT(1)")
	private boolean enabled;
	
	@OneToMany
	@JoinColumn(name="username")
	private List<UserAuthority> authorities;
	
	public UserSecurity() {
		this.authorities = new ArrayList<>();
	}

	public UserSecurity(String username, String password, boolean enabled) {
		this();
		this.username = username;
		this.password = password;
		this.enabled = enabled;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public List<UserAuthority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<UserAuthority> authorities) {
		this.authorities = authorities;
	}
	
	public void removeAuthority(String role_name) {
		authorities.removeIf(s -> s.getAuthority().equals(role_name));
		authorities.stream().forEach(s -> System.out.println(s.getAuthority()));
		
	}
}
