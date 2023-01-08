package com.tweetapp.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.tweetapp.entities.TweetReply;

@Repository
public interface TweetReplyRepository extends MongoRepository<TweetReply, Long> {

	List<TweetReply> findByParenttweetId(long parenttweetId);

	public int deleteByparenttweetId(long parenttweetId);

}
