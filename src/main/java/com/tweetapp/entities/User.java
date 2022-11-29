package com.tweetapp.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import lombok.*;


@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection="demouser")
@JsonIgnoreProperties({ "contactno","password","tweets","tweetReplies"})
public class User {

	@Transient
    public static final String SEQUENCE_NAME = "users_sequence";
	
	@Id
	private String username;
   @Field
	private String password;
	private String firstname;
	private String lastname;
	private String gender;
	private String contactno;

}
	