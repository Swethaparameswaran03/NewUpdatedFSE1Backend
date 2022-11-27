package com.tweetapp.entities;



import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.github.kaiso.relmongo.annotation.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TweetReply {
	
    @Id
	private String replyMessage;


//	@DBRef(lazy = true)
//	 @ManyToOne
	@Field
	 private List<User> user;
    
//	@DBRef(lazy = true)
//	 @ManyToOne
//	 @JsonIgnore
	@Field
	 private Tweet tweet;
	
	private Date repliedDate;
	
	

	
	

}
