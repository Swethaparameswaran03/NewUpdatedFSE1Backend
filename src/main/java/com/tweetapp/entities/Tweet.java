package com.tweetapp.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonInclude;

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
@Document(collection = "tweet")
@JsonInclude(JsonInclude.Include.NON_NULL)

public class Tweet {

	@Id
	private long tweetId;
	private List<User> user;
	@Field
	private String tweetText;
	@CreatedDate
	private Date tweetDate;
	@Field
	private int likes;
	@Field
	public List<TweetReply> tweetReplies = new ArrayList<>();

}
