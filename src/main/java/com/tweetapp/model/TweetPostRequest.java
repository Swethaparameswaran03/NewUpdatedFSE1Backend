package com.tweetapp.model;

import lombok.AllArgsConstructor;

import lombok.*;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TweetPostRequest {

	private String tweetText;
}
