package com.tweetapp.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import com.tweetapp.entities.Tweet;
import com.tweetapp.entities.User;

@Repository
public interface TweetRepository extends MongoRepository<Tweet, Long> {

	public Tweet findByTweetId(long tweetId);

	public Tweet deleteByTweetId(long tweetId);

	
	public List<Tweet> findByUser(List<User> user);
	
	@Query("{ user: { $elemMatch: { _id:?0 } } }")
	public List<Tweet> findTweetByUserId(String user);

}
