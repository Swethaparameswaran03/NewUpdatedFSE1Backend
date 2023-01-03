package com.tweetapp.test;

import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.mockito.Mockito;

import com.tweetapp.entities.Tweet;
import com.tweetapp.entities.TweetLike;
import com.tweetapp.entities.TweetReply;
import com.tweetapp.entities.User;
import com.tweetapp.exception.TweetException;
import com.tweetapp.repositories.TweetLikeRepository;
import com.tweetapp.repositories.TweetReplyRepository;
import com.tweetapp.repositories.TweetRepository;
import com.tweetapp.repositories.UserRepository;
import com.tweetapp.services.JWTUserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tweetapp.model.JwtRequest;
import com.tweetapp.model.TweetPostRequest;
import com.tweetapp.model.TweetReplyRequest;
import com.tweetapp.repositories.UserRepository;
import com.tweetapp.utility.JWTUtility;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.mockito.MockitoAnnotations;
import org.objenesis.instantiator.basic.NewInstanceInstantiator;
import org.springframework.security.core.userdetails.UserDetails;

@SpringBootTest
@AutoConfigureMockMvc
//@WebMvcTest(UserController.class)
@TestPropertySource(properties = "spring.mongodb.embedded.version=3.5.5")
@TestMethodOrder(OrderAnnotation.class)
public class TweetsTest {
	
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	public JWTUtility jwtUtil1;
	@InjectMocks
	public JWTUtility jwtUtil;

	@MockBean
	private UserRepository userrepository;
	
	@MockBean
	private TweetRepository tweetrepo;
	
	@MockBean
	private TweetReplyRepository replyrepo;
	
	@MockBean
	private TweetLikeRepository like;

	@Autowired
	private JWTUserService serv;
	
	private List<Tweet> tweet;
	private List<TweetReply> replies;
	private Tweet tweett;

	
	String jwtToken;
	
	@BeforeEach

	public void setup() {

	MockitoAnnotations.openMocks(this);
	
//	List<User> userList=new ArrayList<>();
//	userList.add(new User("swetha", "swetha", "TEST", "USER2", "female", "9655774780"));
//	Mockito.doReturn(Optional.of(userList)).when(userrepository).findByUsername("test2");
	
	List<com.tweetapp.entities.User> user= new ArrayList<>();
	  user.add(new com.tweetapp.entities.User("test1","test1","tes1","y","female","567890124"));
		java.util.Optional<List<com.tweetapp.entities.User>> ab = Optional.of(user);
	//////	
	when(userrepository.findByUsername(Mockito.any(String.class))).thenReturn(ab);



//	UserDetails u = new User("test", "test", "TE", "ST", "test@test.com", "Female", "user", "green");
	UserDetails u=serv.loadUserByUsername("test1");
	System.out.println(u.getUsername());
	
	jwtToken = jwtUtil1.generateToken(u);
System.out.println(jwtToken);
//	uList = new ArrayList<User>();
//
//	uList.add(u);
//	
	}
	
//	@Test
//	public void login() throws JsonProcessingException, Exception
//	{
//		JwtRequest u=new JwtRequest();
//
//		u.setUsername("swetha");
//
//		u.setPassword("swetha");
//
//		mockMvc.perform(post("/api/v1.0/tweets/login")
//
//		.contentType("application/json")
//
//		.content(objectMapper.writeValueAsString(u)))
//
//		.andExpect(status().isOk());
//	}
	
	@Test
	@Order(1)
	public void posttweet() throws JsonProcessingException, Exception
	
	{
		List<com.tweetapp.entities.User> user= new ArrayList<>();
		  user.add(new com.tweetapp.entities.User("test1","test1","test1","p","female","9566644979"));
			when(userrepository.findByUsername(Mockito.any(String.class))).thenReturn(Optional.of(user));
			when(userrepository.findAll()).thenReturn(user);
			when(tweetrepo.save(Mockito.any(Tweet.class))).thenReturn(new Tweet());
			TweetPostRequest t = new TweetPostRequest("hi");

				mockMvc.perform(post("/api/v1.0/tweets/test1/add")
						.header("Authorization", "Bearer " + jwtToken)

				.contentType("application/json")
				.content(objectMapper.writeValueAsString(t)))

				.andExpect(status().isCreated());
	}
	
	@Test
	@Order(2)
	public void posttweetNot() throws JsonProcessingException, Exception
	
	{
		List<com.tweetapp.entities.User> user= new ArrayList<>();
		  user.add(new com.tweetapp.entities.User("test12","test12","test12","p","female","9566644979"));
			when(userrepository.findAll()).thenReturn(null);
//			when(userrepository.findByUsername(Mockito.any(String.class))).thenReturn((Optional.ofNullable(null)));
//			when(tweetrepo.save(Mockito.any(Tweet.class))).thenReturn(new Tweet());
			TweetPostRequest t = new TweetPostRequest("hi");

				mockMvc.perform(post("/api/v1.0/tweets/test12/add")
						.header("Authorization", "Bearer " + jwtToken)

				.contentType("application/json")
				.content(objectMapper.writeValueAsString(t)))

				.andExpect(status().is4xxClientError());
	}
	
	@Test
	@Order(3)
	public void posttweetNotUsername() throws JsonProcessingException, Exception
	
	{
		List<com.tweetapp.entities.User> user= new ArrayList<>();
		  user.add(new com.tweetapp.entities.User("test12","test12","test12","p","female","9566644979"));
			when(tweetrepo.findAll()).thenReturn(null);
//			when(userrepository.findByUsername(Mockito.any(String.class))).thenReturn((Optional.ofNullable(null)));
//			when(tweetrepo.save(Mockito.any(Tweet.class))).thenReturn(new Tweet());
			TweetPostRequest t = new TweetPostRequest("hi");

				mockMvc.perform(post("/api/v1.0/tweets/test12/add")
						.header("Authorization", "Bearer " + jwtToken)

				.contentType("application/json")
				.content(objectMapper.writeValueAsString(t)))

				.andExpect(status().is4xxClientError());
	}

//	
	@Test
	@Order(4)
	public void findAllTweets() throws JsonProcessingException, Exception
	
	{
		List<com.tweetapp.entities.User> user= new ArrayList<>();
		  user.add(new com.tweetapp.entities.User("test1","test1","test1","p","female","9566644979"));
			 replies= new ArrayList<>();
			  replies.add(new com.tweetapp.entities.TweetReply("myreply",123,user,tweett,new Date()));

		  List<com.tweetapp.entities.Tweet> tweet= new ArrayList<>();
		  tweet.add(new com.tweetapp.entities.Tweet(123,user,"text",new Date(),2,replies));
		  
			when(tweetrepo.findAll()).thenReturn(tweet);
			when(replyrepo.findByParenttweetId(Mockito.any(Long.class))).thenReturn(replies);

			mockMvc.perform(
					MockMvcRequestBuilders.get("/api/v1.0/tweets/all")
					.header("Authorization", "Bearer " + jwtToken)
					.accept(MediaType.APPLICATION_JSON))
					.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}

	@Test
	@Order(5)
	public void findAllTweetsException() throws JsonProcessingException, Exception
	
	{
		List<com.tweetapp.entities.User> user= new ArrayList<>();
		  user.add(new com.tweetapp.entities.User("test1","test1","test1","p","female","9566644979"));
		  user.add(new com.tweetapp.entities.User("test2","test1","test1","p","female","9566644979"));
//		  user.add(new com.tweetapp.entities.User("test3","test1","test1","p","female","9566644979"));

			 replies= new ArrayList<>();
			  replies.add(new com.tweetapp.entities.TweetReply("myreply",123,user,tweett,new Date()));

		  List<com.tweetapp.entities.Tweet> tweet= new ArrayList<>();
		  tweet.add(new com.tweetapp.entities.Tweet(123,user,"text",new Date(),2,replies));
		  
		  List<com.tweetapp.entities.TweetLike> tweets= new ArrayList<>();
		  tweets.add(new com.tweetapp.entities.TweetLike(1,"test1",123));
		  tweets.add(new com.tweetapp.entities.TweetLike(2,"test2",123));
//		  tweets.add(new com.tweetapp.entities.Tweet(123,user,"text",new Date(),3,replies));

		  
			when(tweetrepo.findAll()).thenReturn(null);
			when(like.findByTweetId(Mockito.any(Long.class))).thenReturn(tweets);

			when(replyrepo.findByParenttweetId(Mockito.any(Long.class))).thenReturn(null);

			mockMvc.perform(
					MockMvcRequestBuilders.get("/api/v1.0/tweets/all")
					.header("Authorization", "Bearer " + jwtToken)
					.accept(MediaType.APPLICATION_JSON))
					.andExpect(MockMvcResultMatchers.status().is4xxClientError()).andReturn();
	}
	
	@Test
	@Order(6)
	public void findAllTweetsOfUser() throws JsonProcessingException, Exception
	
	{
		List<com.tweetapp.entities.User> user= new ArrayList<>();
		  user.add(new com.tweetapp.entities.User("test1","test1","test1","p","female","9566644979"));
			 replies= new ArrayList<>();
			  replies.add(new com.tweetapp.entities.TweetReply("myreply",123,user,tweett,new Date()));

		  List<com.tweetapp.entities.Tweet> tweet= new ArrayList<>();
		  tweet.add(new com.tweetapp.entities.Tweet(123,user,"text",new Date(),2,replies));
		  
			when(tweetrepo.findTweetByUserId(Mockito.any(String.class))).thenReturn(tweet);
			when(replyrepo.findByParenttweetId(Mockito.any(Long.class))).thenReturn(replies);

			mockMvc.perform(
					MockMvcRequestBuilders.get("/api/v1.0/tweets/test1")
					.header("Authorization", "Bearer " + jwtToken)
					.accept(MediaType.APPLICATION_JSON))
					.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}
	
	@Test
	@Order(7)
	public void findAllTweetsOfUserException() throws JsonProcessingException, Exception
	
	{
		List<com.tweetapp.entities.User> user= new ArrayList<>();
		  user.add(new com.tweetapp.entities.User("test1","test1","test1","p","female","9566644979"));
			 replies= new ArrayList<>();
			  replies.add(new com.tweetapp.entities.TweetReply("myreply",123,user,tweett,new Date()));

		  List<com.tweetapp.entities.Tweet> tweet= new ArrayList<>();
		  tweet.add(new com.tweetapp.entities.Tweet(123,user,"text",new Date(),2,replies));
		  
			when(tweetrepo.findTweetByUserId(Mockito.any(String.class))).thenReturn(new ArrayList<>());
			when(replyrepo.findByParenttweetId(Mockito.any(Long.class))).thenReturn(null);

			mockMvc.perform(
					MockMvcRequestBuilders.get("/api/v1.0/tweets/test1")
					.header("Authorization", "Bearer " + jwtToken)
					.accept(MediaType.APPLICATION_JSON))
					.andExpect(MockMvcResultMatchers.status().is4xxClientError()).andReturn();
	}

	@Test
	@Order(8)
	public void findAllTweetsOfUserException1() throws JsonProcessingException, Exception
	
	{
		List<com.tweetapp.entities.User> user= new ArrayList<>();
		  user.add(new com.tweetapp.entities.User("test1","test1","test1","p","female","9566644979"));
			 replies= new ArrayList<>();
			  replies.add(new com.tweetapp.entities.TweetReply("myreply",123,user,tweett,new Date()));

		  List<com.tweetapp.entities.Tweet> tweet= new ArrayList<>();
		  tweet.add(new com.tweetapp.entities.Tweet(123,user,"text",new Date(),2,replies));
		  
			when(tweetrepo.findTweetByUserId(Mockito.any(String.class))).thenReturn((null));
//			when(replyrepo.findByParenttweetId(Mockito.any(Long.class))).thenReturn(replies);

			mockMvc.perform(
					MockMvcRequestBuilders.get("/api/v1.0/tweets/test1")
					.header("Authorization", "Bearer " + jwtToken)
					.accept(MediaType.APPLICATION_JSON))
					.andExpect(MockMvcResultMatchers.status().is4xxClientError()).andReturn();
	}

	@Test
	@Order(9)
	public void findAllUsers() throws JsonProcessingException, Exception
	
	{
		List<com.tweetapp.entities.User> user= new ArrayList<>();
		  user.add(new com.tweetapp.entities.User("test1","test1","test1","p","female","9566644979"));
			 replies= new ArrayList<>();
			  replies.add(new com.tweetapp.entities.TweetReply("myreply",123,user,tweett,new Date()));

		  List<com.tweetapp.entities.Tweet> tweet= new ArrayList<>();
		  tweet.add(new com.tweetapp.entities.Tweet(123,user,"text",new Date(),2,replies));
		  
			when(userrepository.findAll()).thenReturn((user));
//			when(replyrepo.findByParenttweetId(Mockito.any(Long.class))).thenReturn(replies);

			mockMvc.perform(
					MockMvcRequestBuilders.get("/api/v1.0/tweets/users/all")
					.header("Authorization", "Bearer " + jwtToken)
					.accept(MediaType.APPLICATION_JSON))
					.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}

	@Test
	@Order(10)
	public void searchByUsername() throws JsonProcessingException, Exception
	
	{
		List<com.tweetapp.entities.User> user= new ArrayList<>();
		  user.add(new com.tweetapp.entities.User("test1","test1","test1","p","female","9566644979"));
			 replies= new ArrayList<>();
			  replies.add(new com.tweetapp.entities.TweetReply("myreply",123,user,tweett,new Date()));

		  List<com.tweetapp.entities.Tweet> tweet= new ArrayList<>();
		  tweet.add(new com.tweetapp.entities.Tweet(123,user,"text",new Date(),2,replies));
		  
			when(userrepository.findByUsernameContains(Mockito.any(String.class))).thenReturn((user));
//			when(replyrepo.findByParenttweetId(Mockito.any(Long.class))).thenReturn(replies);

			mockMvc.perform(
					MockMvcRequestBuilders.get("/api/v1.0/tweets/user/search/test1")
					.header("Authorization", "Bearer " + jwtToken)
					.accept(MediaType.APPLICATION_JSON))
					.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	}
	
	@Test
	@Order(11)
	public void searchByUsernameException() throws JsonProcessingException, Exception
	
	{
		List<com.tweetapp.entities.User> user= new ArrayList<>();
		  user.add(new com.tweetapp.entities.User("test1","test1","test1","p","female","9566644979"));
			 replies= new ArrayList<>();
			  replies.add(new com.tweetapp.entities.TweetReply("myreply",123,user,tweett,new Date()));

		  List<com.tweetapp.entities.Tweet> tweet= new ArrayList<>();
		  tweet.add(new com.tweetapp.entities.Tweet(123,user,"text",new Date(),2,replies));
		  
			doThrow(new RuntimeException("abc")).
			when(userrepository).findByUsernameContains(Mockito.any(String.class));
//			when(replyrepo.findByParenttweetId(Mockito.any(Long.class))).thenReturn(replies);

			mockMvc.perform(
					MockMvcRequestBuilders.get("/api/v1.0/tweets/user/search/hello")
					.header("Authorization", "Bearer " + jwtToken)
					.accept(MediaType.APPLICATION_JSON))
					.andExpect(MockMvcResultMatchers.status().is4xxClientError()).andReturn();
	}

	@Test
	@Order(12)
	public void updateTweetOfUser() throws JsonProcessingException, Exception
	
	{
		Tweet tweett=new  Tweet();
		tweett.setTweetId(123);
//		tweett.setUser("test1");
		List<com.tweetapp.entities.User> user= new ArrayList<>();
		  user.add(new com.tweetapp.entities.User("test1","test1","test1","p","female","9566644979"));
			 replies= new ArrayList<>();
			  replies.add(new com.tweetapp.entities.TweetReply("myreply",123,user,tweett,new Date()));

		  List<com.tweetapp.entities.Tweet> tweet= new ArrayList<>();
		  tweet.add(new com.tweetapp.entities.Tweet(123,user,"text",new Date(),2,replies));
		  
			when(userrepository.findAll()).thenReturn((user));

			when(tweetrepo.findByTweetId(Mockito.any(Long.class))).thenReturn((tweet.get(0)));
    		when(tweetrepo.save(Mockito.any(Tweet.class))).thenReturn(new Tweet());

			when(replyrepo.findByParenttweetId(Mockito.any(Long.class))).thenReturn(replies);
			TweetPostRequest t = new TweetPostRequest("text edited");


			mockMvc.perform(
					MockMvcRequestBuilders.put("/api/v1.0/tweets/test1/update/123")
					.header("Authorization", "Bearer " + jwtToken)
					.contentType("application/json")
					.content(objectMapper.writeValueAsString(t)))
					.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
			
	}
	
	@Test
	@Order(13)
	public void updateTweetOfUserException() throws JsonProcessingException, Exception
	
	{
		Tweet tweett=new  Tweet();
		tweett.setTweetId(123);
//		tweett.setUser("test1");
		List<com.tweetapp.entities.User> user= new ArrayList<>();
		  user.add(new com.tweetapp.entities.User("test1","test1","test1","p","female","9566644979"));
		  
			List<com.tweetapp.entities.User> diffuser= new ArrayList<>();
			diffuser.add(new com.tweetapp.entities.User("test2","test1","test1","p","female","9566644979"));
			  

			 replies= new ArrayList<>();
			  replies.add(new com.tweetapp.entities.TweetReply("myreply",123,user,tweett,new Date()));

		  List<com.tweetapp.entities.Tweet> tweet= new ArrayList<>();
		  tweet.add(new com.tweetapp.entities.Tweet(123,user,"text",new Date(),2,replies));
		  
		 
		  
			when(userrepository.findAll()).thenReturn((new ArrayList<>()));

			when(tweetrepo.findByTweetId(Mockito.any(Long.class))).thenReturn(new Tweet(123,diffuser,"text",new Date(),2,replies));
    		when(tweetrepo.save(Mockito.any(Tweet.class))).thenReturn(new Tweet());

			when(replyrepo.findByParenttweetId(Mockito.any(Long.class))).thenReturn(replies);
			TweetPostRequest t = new TweetPostRequest("text edited");


			mockMvc.perform(
					MockMvcRequestBuilders.put("/api/v1.0/tweets/test1/update/123")
					.header("Authorization", "Bearer " + jwtToken)
					.contentType("application/json")
					.content(objectMapper.writeValueAsString(t)))
					.andExpect(MockMvcResultMatchers.status().is4xxClientError()).andReturn();
			
	}
	
	@Test
	@Order(14)
	public void deleteTweetOfUser() throws JsonProcessingException, Exception
	
	{
		Tweet tweett=new  Tweet();
		tweett.setTweetId(123);
//		tweett.setUser("test1");
		List<com.tweetapp.entities.User> user= new ArrayList<>();
		  user.add(new com.tweetapp.entities.User("test1","test1","test1","p","female","9566644979"));
			 replies= new ArrayList<>();
			  replies.add(new com.tweetapp.entities.TweetReply("myreply",123,user,tweett,new Date()));

		  List<com.tweetapp.entities.Tweet> tweet= new ArrayList<>();
		  tweet.add(new com.tweetapp.entities.Tweet(123,user,"text",new Date(),2,replies));
		  
			when(userrepository.findAll()).thenReturn((user));

			when(tweetrepo.findByTweetId(Mockito.any(Long.class))).thenReturn((tweet.get(0)));
			when(tweetrepo.deleteByTweetId(Mockito.any(Long.class))).thenReturn((tweet.get(0)));
			when(like.deleteByTweetId(Mockito.any(Long.class))).thenReturn((123));


     		when(tweetrepo.save(Mockito.any(Tweet.class))).thenReturn(new Tweet());

			when(replyrepo.findByParenttweetId(Mockito.any(Long.class))).thenReturn(replies);


			mockMvc.perform(
					MockMvcRequestBuilders.delete("/api/v1.0/tweets/test1/delete/123")
					.header("Authorization", "Bearer " + jwtToken)
					.accept(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

			
	}
	
	@Test
	@Order(15)
	public void deleteTweetOfUserException() throws JsonProcessingException, Exception
	
	{
		
		List<com.tweetapp.entities.User> user= new ArrayList<>();
		  user.add(new com.tweetapp.entities.User("test1","test1","test1","p","female","9566644979"));
			 replies= new ArrayList<>();
			  replies.add(new com.tweetapp.entities.TweetReply("myreply",123,user,tweett,new Date()));

		  List<com.tweetapp.entities.Tweet> tweet= new ArrayList<>();
		  tweet.add(new com.tweetapp.entities.Tweet(123,user,"text",new Date(),2,replies));
		  
			when(userrepository.findAll()).thenReturn((new ArrayList<>()));

			when(tweetrepo.findByTweetId(Mockito.any(Long.class))).thenReturn((null));
			when(tweetrepo.deleteByTweetId(Mockito.any(Long.class))).thenReturn((null));
			when(like.deleteByTweetId(Mockito.any(Long.class))).thenReturn((123));


     		when(tweetrepo.save(Mockito.any(Tweet.class))).thenReturn(new Tweet());

			when(replyrepo.findByParenttweetId(Mockito.any(Long.class))).thenReturn(replies);


			mockMvc.perform(
					MockMvcRequestBuilders.delete("/api/v1.0/tweets/test1/delete/123")
					.header("Authorization", "Bearer " + jwtToken)
					.accept(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().is4xxClientError()).andReturn();

			
	}
	
	@Test
	@Order(16)
	public void likeTweetOfUser() throws JsonProcessingException, Exception
	
	{
		Tweet tweett=new  Tweet();
		tweett.setTweetId(123);
//		tweett.setUser("test1");
		List<com.tweetapp.entities.User> user= new ArrayList<>();
		  user.add(new com.tweetapp.entities.User("test1","test1","test1","p","female","9566644979"));
			java.util.Optional<List<com.tweetapp.entities.User>> ab = Optional.of(user);

			 replies= new ArrayList<>();
			  replies.add(new com.tweetapp.entities.TweetReply("myreply",123,user,tweett,new Date()));

		  List<com.tweetapp.entities.Tweet> tweet= new ArrayList<>();
		  tweet.add(new com.tweetapp.entities.Tweet(123,user,"text",new Date(),2,replies));
		  
//			when(userrepository.findAll()).thenReturn((user));

			when(tweetrepo.findByTweetId(Mockito.any(Long.class))).thenReturn((tweet.get(0)));
			when(userrepository.findByUsername(Mockito.any(String.class))).thenReturn((ab));
			TweetLike l=new TweetLike();
			

			when(like.findByUsernameAndTweetId(Mockito.anyString(),Mockito.anyLong())).thenReturn(l);
			when(like.deleteByUsernameAndTweetId(Mockito.anyString(),Mockito.anyLong())).thenReturn(123);
			

//			when(tweetrepo.deleteByTweetId(Mockito.any(Long.class))).thenReturn((tweet.get(0)));
//			when(like.deleteByTweetId(Mockito.any(Long.class))).thenReturn((123));


     		when(tweetrepo.save(Mockito.any(Tweet.class))).thenReturn(new Tweet());

//			when(replyrepo.findByParenttweetId(Mockito.any(Long.class))).thenReturn(replies);


			mockMvc.perform(
					MockMvcRequestBuilders.put("/api/v1.0/tweets/test1/like/123")
					.header("Authorization", "Bearer " + jwtToken)
					.accept(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

			
	}
	
	@Test
	@Order(17)
	public void likeTweetOfUserException() throws JsonProcessingException, Exception
	
	{
		Tweet tweett=new  Tweet();
		tweett.setTweetId(123);
		TweetLike l=new TweetLike();

//		tweett.setUser("test1");
		List<com.tweetapp.entities.User> user= new ArrayList<>();
		  user.add(new com.tweetapp.entities.User("test1","test1","test1","p","female","9566644979"));
			java.util.Optional<List<com.tweetapp.entities.User>> ab = Optional.of(user);

			 replies= new ArrayList<>();
			  replies.add(new com.tweetapp.entities.TweetReply("myreply",123,user,tweett,new Date()));

		  List<com.tweetapp.entities.Tweet> tweet= new ArrayList<>();
		  tweet.add(new com.tweetapp.entities.Tweet(123,user,"text",new Date(),2,replies));
		  
		  List<com.tweetapp.entities.TweetLike> liking = new ArrayList<>();
		  liking.add(new com.tweetapp.entities.TweetLike(1,"test1",123));
		  
			when(like.findAll()).thenReturn(liking);

			when(tweetrepo.findByTweetId(Mockito.any(Long.class))).thenReturn((tweet.get(0)));
			when(userrepository.findByUsername(Mockito.any(String.class))).thenReturn((ab));
			

			when(like.findByUsernameAndTweetId(Mockito.anyString(),Mockito.anyLong())).thenReturn(null);
			when(like.deleteByUsernameAndTweetId(Mockito.anyString(),Mockito.anyLong())).thenReturn(123);
			

//			when(tweetrepo.deleteByTweetId(Mockito.any(Long.class))).thenReturn((tweet.get(0)));
//			when(like.deleteByTweetId(Mockito.any(Long.class))).thenReturn((123));


     		when(tweetrepo.save(Mockito.any(Tweet.class))).thenReturn(new Tweet());

//			when(replyrepo.findByParenttweetId(Mockito.any(Long.class))).thenReturn(replies);


			mockMvc.perform(
					MockMvcRequestBuilders.put("/api/v1.0/tweets/test1/like/123")
					.header("Authorization", "Bearer " + jwtToken)
					.accept(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

			
	}

	@Test
	@Order(18)
	public void replyTweetOfUser() throws JsonProcessingException, Exception
	
	{
		Tweet tweett=new  Tweet();
		tweett.setTweetId(123);
		TweetLike l=new TweetLike();

//		tweett.setUser("test1");
		List<com.tweetapp.entities.User> user= new ArrayList<>();
		  user.add(new com.tweetapp.entities.User("test1","test1","test1","p","female","9566644979"));
			java.util.Optional<List<com.tweetapp.entities.User>> ab = Optional.of(user);

			 replies= new ArrayList<>();
			  replies.add(new com.tweetapp.entities.TweetReply("myreply",123,user,tweett,new Date()));

		  List<com.tweetapp.entities.Tweet> tweet= new ArrayList<>();
		  tweet.add(new com.tweetapp.entities.Tweet(123,user,"text",new Date(),2,replies));
		  
		  List<com.tweetapp.entities.TweetLike> liking = new ArrayList<>();
		  liking.add(new com.tweetapp.entities.TweetLike(1,"test1",123));
		  
			when(tweetrepo.findAll()).thenReturn(tweet);

			when(tweetrepo.findByTweetId(Mockito.any(Long.class))).thenReturn((tweet.get(0)));
			
     		when(replyrepo.save(Mockito.any(TweetReply.class))).thenReturn(new TweetReply());
     		TweetReplyRequest y=new TweetReplyRequest();
     		y.setReplyMessage("hello");


			mockMvc.perform(
					MockMvcRequestBuilders.post("/api/v1.0/tweets/test1/reply/123")
					.header("Authorization", "Bearer " + jwtToken)
					.contentType("application/json")
					.content(objectMapper.writeValueAsString(y)))
			.andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();

			
	}

	@Test
	@Order(19)
	public void replyTweetOfUserException() throws JsonProcessingException, Exception
	
	{
		Tweet tweett=new  Tweet();
		tweett.setTweetId(123);
		TweetLike l=new TweetLike();

//		tweett.setUser("test1");
		List<com.tweetapp.entities.User> user= new ArrayList<>();
		  user.add(new com.tweetapp.entities.User("test1","test1","test1","p","female","9566644979"));
			java.util.Optional<List<com.tweetapp.entities.User>> ab = Optional.of(user);

			 replies= new ArrayList<>();
			  replies.add(new com.tweetapp.entities.TweetReply("myreply",123,user,tweett,new Date()));

		  List<com.tweetapp.entities.Tweet> tweet= new ArrayList<>();
		  tweet.add(new com.tweetapp.entities.Tweet(123,user,"text",new Date(),2,replies));
		  
		  List<com.tweetapp.entities.TweetLike> liking = new ArrayList<>();
		  liking.add(new com.tweetapp.entities.TweetLike(1,"test1",123));
		  
			when(tweetrepo.findAll()).thenReturn(tweet);

			doReturn(null).when(tweetrepo).findByTweetId(1000);
//			when(tweetrepo.findByTweetId(Mockito.any(Long.class))).thenReturn((null));

			
     		when(replyrepo.save(Mockito.any(TweetReply.class))).thenReturn(new TweetReply());
     		TweetReplyRequest y=new TweetReplyRequest();
     		y.setReplyMessage("hello");


			mockMvc.perform(
					MockMvcRequestBuilders.post("/api/v1.0/tweets/test1/reply/1000")
					.header("Authorization", "Bearer " + jwtToken)
					.contentType("application/json")
					.content(objectMapper.writeValueAsString(y)))
			.andExpect(MockMvcResultMatchers.status().is4xxClientError()).andReturn();

			
	}
	
	@Test
	@Order(20)
	public void findWhoLiked() throws JsonProcessingException, Exception
	
	{
		Tweet tweett=new  Tweet();
		tweett.setTweetId(123);
		TweetLike l=new TweetLike();

//		tweett.setUser("test1");
		List<com.tweetapp.entities.User> user= new ArrayList<>();
		  user.add(new com.tweetapp.entities.User("test1","test1","test1","p","female","9566644979"));
			java.util.Optional<List<com.tweetapp.entities.User>> ab = Optional.of(user);

			 replies= new ArrayList<>();
			  replies.add(new com.tweetapp.entities.TweetReply("myreply",123,user,tweett,new Date()));

		  List<com.tweetapp.entities.Tweet> tweet= new ArrayList<>();
		  tweet.add(new com.tweetapp.entities.Tweet(123,user,"text",new Date(),2,replies));
		  
		  List<com.tweetapp.entities.TweetLike> liking = new ArrayList<>();
		  liking.add(new com.tweetapp.entities.TweetLike(1,"test1",123));
		  
//			when(tweetrepo.findAll()).thenReturn(tweet);

			when(tweetrepo.findByTweetId(Mockito.any(Long.class))).thenReturn((tweet.get(0)));
			when(userrepository.findByUsername(Mockito.any(String.class))).thenReturn((ab));
			when(like.findAll()).thenReturn((liking));
			when(like.findByUsernameAndTweetId(Mockito.anyString(),Mockito.anyLong())).thenReturn(l);
			when(like.findByTweetId(Mockito.any(Long.class))).thenReturn(liking);


//     		when(replyrepo.save(Mockito.any(TweetReply.class))).thenReturn(new TweetReply());
//     		y.setReplyMessage("hello");


			mockMvc.perform(
					MockMvcRequestBuilders.get("/api/v1.0/tweets/test1/findWhoLiked/123")
					.header("Authorization", "Bearer " + jwtToken)
					.accept(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

			
	}
	@Test
	@Order(21)
	public void findWhoLikedException() throws JsonProcessingException, Exception
	
	{
		Tweet tweett=new  Tweet();
		tweett.setTweetId(123);
		TweetLike l=new TweetLike();

//		tweett.setUser("test1");
		List<com.tweetapp.entities.User> user= new ArrayList<>();
		  user.add(new com.tweetapp.entities.User("test1","test1","test1","p","female","9566644979"));
			java.util.Optional<List<com.tweetapp.entities.User>> ab = Optional.of(user);

			 replies= new ArrayList<>();
			  replies.add(new com.tweetapp.entities.TweetReply("myreply",123,user,tweett,new Date()));

		  List<com.tweetapp.entities.Tweet> tweet= new ArrayList<>();
		  tweet.add(new com.tweetapp.entities.Tweet(123,user,"text",new Date(),2,replies));
		  
		  List<com.tweetapp.entities.TweetLike> liking = new ArrayList<>();
		  liking.add(new com.tweetapp.entities.TweetLike(1,"test1",123));
		  liking.add(new com.tweetapp.entities.TweetLike(2,"test2",123));
		  liking.add(new com.tweetapp.entities.TweetLike(3,"test3",123));

		  
//			when(tweetrepo.findAll()).thenReturn(tweet);

			when(tweetrepo.findByTweetId(Mockito.any(Long.class))).thenReturn((tweet.get(0)));
			when(userrepository.findByUsername(Mockito.any(String.class))).thenReturn((ab));
			when(like.findAll()).thenReturn((liking));
			when(like.findByUsernameAndTweetId(Mockito.anyString(),Mockito.anyLong())).thenReturn(l);
			when(like.findByTweetId(Mockito.any(Long.class))).thenReturn(liking);


//     		when(replyrepo.save(Mockito.any(TweetReply.class))).thenReturn(new TweetReply());
//     		y.setReplyMessage("hello");


			mockMvc.perform(
					MockMvcRequestBuilders.get("/api/v1.0/tweets/test1/findWhoLiked/123")
					.header("Authorization", "Bearer " + jwtToken)
					.accept(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

			
	}

	@Test
	@Order(22)
	public void findWhoLikedException1() throws JsonProcessingException, Exception
	
	{
		Tweet tweett=new  Tweet();
		tweett.setTweetId(123);
		TweetLike l=new TweetLike();

//		tweett.setUser("test1");
		List<com.tweetapp.entities.User> user= new ArrayList<>();
		  user.add(new com.tweetapp.entities.User("test1","test1","test1","p","female","9566644979"));
			java.util.Optional<List<com.tweetapp.entities.User>> ab = Optional.of(user);

			 replies= new ArrayList<>();
			  replies.add(new com.tweetapp.entities.TweetReply("myreply",123,user,tweett,new Date()));

		  List<com.tweetapp.entities.Tweet> tweet= new ArrayList<>();
		  tweet.add(new com.tweetapp.entities.Tweet(123,user,"text",new Date(),2,replies));
		  
		  List<com.tweetapp.entities.TweetLike> liking = new ArrayList<>();
		  liking.add(new com.tweetapp.entities.TweetLike(1,"test1",123));
		  liking.add(new com.tweetapp.entities.TweetLike(2,"test2",123));

		  
//			when(tweetrepo.findAll()).thenReturn(tweet);

			when(tweetrepo.findByTweetId(Mockito.any(Long.class))).thenReturn((tweet.get(0)));
			when(userrepository.findByUsername(Mockito.any(String.class))).thenReturn((ab));
			when(like.findAll()).thenReturn((liking));
			when(like.findByUsernameAndTweetId(Mockito.anyString(),Mockito.anyLong())).thenReturn(l);
			when(like.findByTweetId(Mockito.any(Long.class))).thenReturn(liking);


//     		when(replyrepo.save(Mockito.any(TweetReply.class))).thenReturn(new TweetReply());
//     		y.setReplyMessage("hello");


			mockMvc.perform(
					MockMvcRequestBuilders.get("/api/v1.0/tweets/test1/findWhoLiked/123")
					.header("Authorization", "Bearer " + jwtToken)
					.accept(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

			
	}

	@Test
	@Order(23)
	public void findWhoLikedException2() throws JsonProcessingException, Exception
	
	{
		Tweet tweett=new  Tweet();
		tweett.setTweetId(123);
		TweetLike l=new TweetLike();

//		tweett.setUser("test1");
		List<com.tweetapp.entities.User> user= new ArrayList<>();
		  user.add(new com.tweetapp.entities.User("test1","test1","test1","p","female","9566644979"));
			java.util.Optional<List<com.tweetapp.entities.User>> ab = Optional.of(user);

			 replies= new ArrayList<>();
			  replies.add(new com.tweetapp.entities.TweetReply("myreply",123,user,tweett,new Date()));

		  List<com.tweetapp.entities.Tweet> tweet= new ArrayList<>();
		  tweet.add(new com.tweetapp.entities.Tweet(123,user,"text",new Date(),2,replies));
		  
		  List<com.tweetapp.entities.TweetLike> liking = new ArrayList<>();
		  liking.add(new com.tweetapp.entities.TweetLike(1,"test1",123));
		  liking.add(new com.tweetapp.entities.TweetLike(2,"test2",123));

		  
//			when(tweetrepo.findAll()).thenReturn(tweet);

			when(tweetrepo.findByTweetId(Mockito.any(Long.class))).thenReturn((tweet.get(0)));
			when(userrepository.findByUsername(Mockito.any(String.class))).thenReturn((ab));
			
			when(like.findAll()).thenReturn((new ArrayList<>()));
			when(like.findByUsernameAndTweetId(Mockito.anyString(),Mockito.anyLong())).thenReturn(l);
			when(like.findByTweetId(Mockito.any(Long.class))).thenReturn(liking);


//     		when(replyrepo.save(Mockito.any(TweetReply.class))).thenReturn(new TweetReply());
//     		y.setReplyMessage("hello");


			mockMvc.perform(
					MockMvcRequestBuilders.get("/api/v1.0/tweets/test1/findWhoLiked/123")
					.header("Authorization", "Bearer " + jwtToken)
					.accept(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
//			when(userrepository.findByUsername(Mockito.any(String.class))).thenReturn((Optional.ofNullable(null)));


//			mockMvc.perform(
//					MockMvcRequestBuilders.get("/api/v1.0/tweets/test1/findWhoLiked/123")
//					.header("Authorization", "Bearer " + jwtToken)
//					.accept(MediaType.APPLICATION_JSON))
//			.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

			
	}

	@Test
	@Order(24)
	public void findWhoLikedException3() throws JsonProcessingException, Exception
	
	{
		Tweet tweett=new  Tweet();
		tweett.setTweetId(123);
		TweetLike l=new TweetLike();

//		tweett.setUser("test1");
		List<com.tweetapp.entities.User> user= new ArrayList<>();
		  user.add(new com.tweetapp.entities.User("test1","test1","test1","p","female","9566644979"));
			java.util.Optional<List<com.tweetapp.entities.User>> ab = Optional.of(user);

			 replies= new ArrayList<>();
			  replies.add(new com.tweetapp.entities.TweetReply("myreply",123,user,tweett,new Date()));

		  List<com.tweetapp.entities.Tweet> tweet= new ArrayList<>();
		  tweet.add(new com.tweetapp.entities.Tweet(123,user,"text",new Date(),2,replies));
		  
		  List<com.tweetapp.entities.TweetLike> liking = new ArrayList<>();
		  liking.add(new com.tweetapp.entities.TweetLike(1,"test1",123));
		  liking.add(new com.tweetapp.entities.TweetLike(2,"test2",123));

		  
//			when(tweetrepo.findAll()).thenReturn(tweet);

			when(tweetrepo.findByTweetId(Mockito.any(Long.class))).thenReturn((tweet.get(0)));
			when(userrepository.findByUsername(Mockito.any(String.class))).thenReturn((ab));
			
			when(like.findAll()).thenReturn((null));
			when(like.findByUsernameAndTweetId(Mockito.anyString(),Mockito.anyLong())).thenReturn(l);
			when(like.findByTweetId(Mockito.any(Long.class))).thenReturn(liking);


//     		when(replyrepo.save(Mockito.any(TweetReply.class))).thenReturn(new TweetReply());
//     		y.setReplyMessage("hello");


			mockMvc.perform(
					MockMvcRequestBuilders.get("/api/v1.0/tweets/test1/findWhoLiked/123")
					.header("Authorization", "Bearer " + jwtToken)
					.accept(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
//			when(userrepository.findByUsername(Mockito.any(String.class))).thenReturn((Optional.ofNullable(null)));


//			mockMvc.perform(
//					MockMvcRequestBuilders.get("/api/v1.0/tweets/test1/findWhoLiked/123")
//					.header("Authorization", "Bearer " + jwtToken)
//					.accept(MediaType.APPLICATION_JSON))
//			.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

			
	}


	
}
