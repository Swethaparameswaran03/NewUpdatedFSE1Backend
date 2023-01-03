package com.tweetapp.model;


//@Data
public class ForgotRequest {
//	public ForgotRequest(String username, String confirmPassword, String newPassword) {
//		super();
//		this.username = username;
//		this.confirmPassword = confirmPassword;
//		this.newPassword = newPassword;
//	}

	public ForgotRequest() {

	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	private String username;
	private String confirmPassword;
	private String newPassword;

}
