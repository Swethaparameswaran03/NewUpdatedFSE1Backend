package com.tweetapp.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
//@RequiredArgsConstructor

//@NoArgsConstructor
public class UserDTO {

	@NonNull
	private String firstname;
	@NonNull
	private String username;
	@NonNull
	private String lastname;
	@NonNull
	private String contactno;
	@NonNull
	private String gender;
	@NonNull
	private String password;

}
