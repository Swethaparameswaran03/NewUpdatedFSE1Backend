package com.tweetapp.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.tweetapp.entities.Tweet;
import com.tweetapp.entities.User;

@Repository
public interface TweetRepository extends MongoRepository<Tweet,Long>{

////	public List<Tweet> findByUsername(String username);
//	
	public Tweet findByTweetId(long tweetId);
//	
////	public Tweet findByUsernameAndTweetId(User user,String id);
//
////	public Tweet deleteByUsernameAndTweetId(String username,String id);
//
//	public List<Tweet> findByUser(Optional<List<User>> user);
//
	public Tweet deleteByTweetId(long tweetId);
//
////	public Optional<List<User>> findByUsername(String userName);
//
//
//}
public List<Tweet> findByUser(List<User> user);
	
	
//	public Tweet findByUsernameAndTweetId(String username,long tweetId);

//	public Tweet deleteByUsernameAndTweetId(String username,long tweetId);
//	public void save(TweetPostRequest tw);



}
