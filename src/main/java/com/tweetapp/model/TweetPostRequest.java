package com.tweetapp.model;



import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.data.annotation.Id;

import com.tweetapp.entities.Tweet;

import lombok.*;
import lombok.NoArgsConstructor;


	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public class TweetPostRequest {
		
        
		private String tweetText;
}
