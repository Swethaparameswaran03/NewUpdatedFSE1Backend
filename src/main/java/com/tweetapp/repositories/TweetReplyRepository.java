package com.tweetapp.repositories;



import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.tweetapp.entities.Tweet;
import com.tweetapp.entities.TweetReply;
import com.tweetapp.entities.User;

@Repository
public interface TweetReplyRepository extends MongoRepository<TweetReply,String>{
	
	


}
