package com.tweetapp.model;

import lombok.Data;

@Data
public class JwtResponse {

	public JwtResponse(String jwtToken) {
		super();
		this.jwtToken = jwtToken;
	}

	public JwtResponse() {

	}

	public String getJwtToken() {
		return jwtToken;
	}

	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}

	private String jwtToken;
}
