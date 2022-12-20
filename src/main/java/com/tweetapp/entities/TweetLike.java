package com.tweetapp.entities;

import java.util.List;
import java.util.Optional;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.*;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "tweetlike")
@JsonInclude(JsonInclude.Include.NON_NULL)

public class TweetLike {
	
	@Id
	private long id;
	@Field
	private String username;
	@Field
	@JsonIgnore
	private long tweetId;
//	@Override
//	public String toString() {
//		return "\nTweetLike [user=" + user + ", tweet=" + tweet + "]";
//	}
	

}
