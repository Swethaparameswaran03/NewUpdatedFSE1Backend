package com.tweetapp.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.tweetapp.entities.Tweet;
import com.tweetapp.entities.TweetLike;
import com.tweetapp.entities.User;

public interface TweetLikeRepository extends MongoRepository<TweetLike, String>{
	

	public TweetLike findByUserAndTweet(User user, Tweet tweet);

	public List<TweetLike> deleteByUser(List<User> user);

	public TweetLike deleteByUser(String username);

	public int deleteByUserAndTweet(User user, Tweet tweet);

	public List<TweetLike> findByTweet(Tweet tweet);

}
