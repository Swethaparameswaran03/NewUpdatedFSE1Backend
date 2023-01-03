package com.tweetapp.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Field;

import com.tweetapp.entities.Tweet;
import com.tweetapp.entities.TweetLike;
import com.tweetapp.entities.TweetReply;
import com.tweetapp.entities.User;

import lombok.*;

@Data
//@Getter
//@Setter
public class TweetModel {

	private long tweetId;
	public TweetModel(
			Tweet t) {
		super();
		this.tweetId = t.getTweetId();
		this.user = t.getUser();
		this.tweetText = t.getTweetText();
		this.tweetDate = t.getTweetDate();
		this.likes = t.getLikes();
		this.tweetReplies = t.getTweetReplies();
	}
	private List<User> user;
	private String tweetText;
	private Date tweetDate;
	private int likes;
	public List<TweetReply> tweetReplies = new ArrayList<>();
public String username;	
}
