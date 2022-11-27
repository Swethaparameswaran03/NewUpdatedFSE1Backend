package com.tweetapp.model;

import lombok.Data;

@Data
public class JwtRequest {

	public JwtRequest(String username, String password) {
		super();
		this.username = username;
		this.password = password;
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
	public JwtRequest() {
		
	}
	public void setPassword(String password) {
		this.password = password;
	}
	private String username;
	private String password;
}
