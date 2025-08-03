package com.dam.proyectodam.login.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "userCredentials")
public class UserCredentialsEntity {
	
	@Id
	private String id;
	
	@Indexed(unique = true)
	private String username;
	
	private String password;
	private String token;
	private Boolean rememberMe;
	
	public UserCredentialsEntity() {
		
	}
	
	public UserCredentialsEntity(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getId() {
		return id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Boolean getRememberMe() {
		return rememberMe;
	}

	public void setRememberMe(Boolean rememberMe) {
		this.rememberMe = rememberMe;
	}

	@Override
	public String toString() {
		return "UserCredentialsEntity [username=" + username + ", password=" + password + ", token=" + token
				+ ", rememberMe=" + rememberMe + "]";
	}

}
