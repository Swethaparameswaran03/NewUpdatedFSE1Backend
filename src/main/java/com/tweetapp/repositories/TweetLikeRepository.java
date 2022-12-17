package com.tweetapp.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.tweetapp.entities.Tweet;
import com.tweetapp.entities.TweetLike;
import com.tweetapp.entities.User;

public interface TweetLikeRepository extends MongoRepository<TweetLike, String>{
	

	//@Query("{$and:[{user.username: ?0},{tweet.tweetId: ?1}] }")
	public TweetLike findByUserAndTweet(User user, Tweet tweetId);

	public List<TweetLike> deleteByUser(List<User> user);

	public TweetLike deleteByUser(String username);

	public int deleteByUserAndTweet(User user, Tweet tweet);

	public List<TweetLike> findByTweet(Tweet tweet);

}
