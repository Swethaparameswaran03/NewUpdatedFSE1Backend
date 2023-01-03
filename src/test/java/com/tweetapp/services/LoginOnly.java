package com.tweetapp.services;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.tweetapp.entities.Tweet;
import com.tweetapp.entities.User;
import com.tweetapp.exception.TweetException;
import com.tweetapp.model.TweetPostRequest;
import com.tweetapp.model.TweetReplyRequest;
import com.tweetapp.repositories.TweetLikeRepository;
import com.tweetapp.repositories.TweetReplyRepository;
import com.tweetapp.repositories.TweetRepository;
import com.tweetapp.repositories.UserRepository;


@SpringBootTest
@AutoConfigureMockMvc
class LoginOnly {

	
	@InjectMocks
	private UserModelService service;
	
	@MockBean
	private UserRepository userrepository;
	
	@MockBean
	private TweetRepository tweetrepo;
	
	@MockBean
	private TweetReplyRepository replyrepo;
	
	@MockBean
	private TweetLikeRepository like;
	
	
	@BeforeEach

	public void setup() {

	MockitoAnnotations.openMocks(this);
	}
	
	@Test
	@Order(1)
	void testdisplayalltweets() throws TweetException {
		Mockito.doReturn(new ArrayList<>()).when(tweetrepo).findAll();

try {
	service.displayAllTweets();
}
catch(Exception e)
{
	assertEquals(e.getMessage(),"There are no tweets available!!");

}
		
//		assertEquals(service.displayAllTweets(),"There are no tweets available!!");
	}
	
	@Test
	@Order(2)
	void findusernameexception() throws TweetException {
		Mockito.doReturn(Optional.ofNullable(null)).when(userrepository).findByUsername("swext");
		
//		List<Tweet> tweetList = tweetrepository.findAll();
//		Tweet tweet = tweetrepository.findByTweetId(tweetId);


try {
	service.findByUsername("swext");
}
catch(Exception e)
{
	assertEquals(e.getMessage(),"User Not Found");

}
	}
	
//	@Test
//	@Order(3)
//	void updatetweetofuserexception() throws TweetException {
//		List<com.tweetapp.entities.User> user= new ArrayList<>();
//		  user.add(new com.tweetapp.entities.User("swetha","test1","test1","p","female","9566644979"));
//		Mockito.doReturn(user).when(userrepository).findAll();
//		Mockito.doReturn(new Tweet()).when(tweetrepo).findByTweetId(200);
//
////		List<Tweet> tweetList = tweetrepository.findAll();
////		Tweet tweet = tweetrepository.findByTweetId(tweetId);
//		TweetPostRequest t=new TweetPostRequest();
//		t.setTweetText("swe");
//
//
//try {
//	service.updateTweetofUser("swetha",200,t);
//}
//catch(Exception e)
//{
//	assertEquals(e.getMessage(),"user and id doesnt match");
//
//}
//	}
}
//		
////		assertEquals(service.displayAllTweets(),"There are no tweets available!!");
//	}


