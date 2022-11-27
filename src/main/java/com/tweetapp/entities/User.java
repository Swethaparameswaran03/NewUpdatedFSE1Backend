package com.tweetapp.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.github.kaiso.relmongo.annotation.CascadeType;
import io.github.kaiso.relmongo.annotation.FetchType;
import io.github.kaiso.relmongo.annotation.JoinProperty;
import io.github.kaiso.relmongo.annotation.OneToMany;
import lombok.*;


@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection="demouser")
@JsonIgnoreProperties({ "contactno", "password","tweets" })
public class User {

	@Transient
    public static final String SEQUENCE_NAME = "users_sequence";
	
	@Id
	private String username;
   @Field
	private String password;
//	@Field
	private String firstname;
//	@Field
	private String lastname;
//	@Field
	private String gender;
//	@Field
	private String contactno;
@Field
private List<Tweet> tweets;
////	@DBRef
////	@CascadeSave
//    @OneToMany(fetch=FetchType.EAGER, cascade = CascadeType.PERSIST)
//    @JoinProperty(name="tweets")
//	private List<Tweet> tweets=new ArrayList<Tweet>();
//	
////	public Date createdDate;
////	@DBRef(lazy = true)
//    @OneToMany(fetch=FetchType.EAGER, cascade = CascadeType.PERSIST)
//    @JoinProperty(name="tweetreplies")
//    @JsonIgnore
//	private List<TweetReply> tweetReplies = new ArrayList<TweetReply>();

}
	