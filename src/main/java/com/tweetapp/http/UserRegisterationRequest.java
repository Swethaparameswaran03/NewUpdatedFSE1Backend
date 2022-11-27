package com.tweetapp.http;


import java.sql.Date;



import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;



	@Data
	@RequiredArgsConstructor
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


