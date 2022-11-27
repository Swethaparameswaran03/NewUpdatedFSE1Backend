package com.tweetapp.entities;



import java.util.ArrayList;
import java.util.List;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.github.kaiso.relmongo.annotation.CascadeType;
import io.github.kaiso.relmongo.annotation.FetchType;
import io.github.kaiso.relmongo.annotation.JoinProperty;
import io.github.kaiso.relmongo.annotation.ManyToOne;
import io.github.kaiso.relmongo.annotation.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection="tweet")
@JsonInclude(JsonInclude.Include.NON_NULL)
//public class Tweet {
//	
//	@Id
//	private long tweetId;
//	@Field
//	private String tweetText;
//	@Field
//	private String tweetDate;
//	@Field
//	private int likes;
////	@Field
////	private String comments;
////	@DBRef(lazy = true)
//	@ManyToOne(fetch = FetchType.EAGER)
//	@JoinProperty(name="tweet")
//	 private User user;
////	@DBRef(lazy = true)
//	@OneToMany(fetch=FetchType.EAGER, cascade = CascadeType.PERSIST)
//    @JoinProperty(name="tweetreplies")
//	private List<TweetReply> tweetReplies=new ArrayList<>();
//
//	
//
//	
//	
//
//}
public class Tweet {
	
	@Id
	private long tweetId;
	@Field
	private List<User> user;
	@Field
	private String tweetText;
	@Field
	private String tweetDate;
	@Field
	private int likes;
	
	

	
	

}
