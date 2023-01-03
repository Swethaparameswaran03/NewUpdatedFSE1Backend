package com.tweetapp.model;

import lombok.AllArgsConstructor;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TweetPostRequest {

	private String tweetText;
}
