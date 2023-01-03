package com.tweetapp.http;

import lombok.*;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
//@RequiredArgsConstructor
//@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterationRequest {

	@NonNull
	private String username;
	@NonNull
	private String firstname;
	@NonNull
	private String lastname;
	@NonNull
	private String contactno;
	@NonNull
	private String password;
	@NonNull
	private String gender;

//	    @CreationTimestamp
//	    public Date createdDate;

}
