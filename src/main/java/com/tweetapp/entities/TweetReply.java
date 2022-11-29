package com.tweetapp.entities;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Document(collection = "tweetreply")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TweetReply {

	@Id
	private String replyMessage;

	private long parenttweetId;
	@Field
	private List<User> user;

	@Field
	@JsonIgnore
	private Tweet tweet;

	@CreatedDate
	private Date repliedDate;

}
