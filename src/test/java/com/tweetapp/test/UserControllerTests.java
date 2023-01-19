package com.tweetapp.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tweetapp.controllers.TweetController;
import com.tweetapp.controllers.UserController;
import com.tweetapp.dto.UserDTO;
import com.tweetapp.entities.Tweet;
import com.tweetapp.entities.User;
import com.tweetapp.exception.UserException;
import com.tweetapp.http.UserRegisterationRequest;
import com.tweetapp.model.ForgotRequest;
import com.tweetapp.model.JwtRequest;
import com.tweetapp.model.JwtResponse;
import com.tweetapp.model.Response;
import com.tweetapp.repositories.UserRepository;
import com.tweetapp.services.JWTUserService;
import com.tweetapp.services.UserModelService;
import com.tweetapp.utility.JWTUtility;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;




//@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
//@WebMvcTest(UserController.class)
@TestPropertySource(properties = "spring.mongodb.embedded.version=3.5.5")
@TestMethodOrder(OrderAnnotation.class)
public class UserControllerTests {
	
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private PasswordEncoder passwordencoder;

	
	@Autowired
	private UserController controller;
	
	@Autowired
	private UserModelService service;
	
	@Autowired
	private JWTUserService jwtservice;
	
	@MockBean
	private JWTUtility util;
	
	@Autowired
	private JWTUtility UTI;
	
	@MockBean
	private UserRepository repo;
	
	private UserDetails userDetails;
	private String token;
	
	List<User> uList;
	
	private JwtRequest jwt;
	
	private ForgotRequest forgot;
	
	
//	@BeforeEach
//	public void setup1() throws UserException
//	{
//		MockitoAnnotations.openMocks(this);
//	List<com.tweetapp.entities.User> b= new ArrayList<>();
//		b.add(new com.tweetapp.entities.User("test","test","test","y","male","9566644979"));
//			Optional<List<com.tweetapp.entities.User>> a = Optional.of(b);
////			
//			Mockito.doReturn(a).when(repo).findByUsername("test");
//			userDetails = jwtservice.loadUserByUsername("test");
//			 token = util.generateToken(userDetails);
//			System.out.println(token);
////			User user=new User();
////			user.setUsername("abc");
////			user.setFirstname("abc");
////			user.setLastname("abc");
////			user.setPassword("abc");
////			user.setGender("female");
////			user.setContactno("1566644979");
////		 UserDTO dto=new UserDTO(user.getUsername(), user.getFirstname(), user.getLastname(), user.getContactno(),
////					user.getGender(), user.getPassword());
////		 
////
////		Mockito.doReturn(dto).when(userservice).saveUser(user);
//	}
	
	@BeforeEach
	public void setup() throws Exception {
	MockitoAnnotations.openMocks(this);
////	User u=new User("swetha","swetha","swetha","y","male","9566644979");
////	java.util.Optional<List<com.tweetapp.entities.User>> a = repo.findByUsername("swetha");
//
//	List<com.tweetapp.entities.User> user= new ArrayList<>();
//  user.add(new com.tweetapp.entities.User("swetha","swetha","swetha","swetha","female","9566644979"));
//	java.util.Optional<List<com.tweetapp.entities.User>> ab = Optional.of(user);
////////	
////	final UserDetails userDetails = jwtservice.loadUserByUsername("swetha");
//	
//	when(repo.findByUsername(Mockito.any(String.class))).thenReturn(ab);
//	userDetails = jwtservice.loadUserByUsername("swetha");
//
////		
////final UserDetails userDetails = jwtservice.loadUserByUsername("swetha");
////		 System.out.println(userDetails);
////
////			System.out.println(token);
//	JwtRequest jwt=new JwtRequest();
//	jwt.setUsername("swetha");
//	jwt.setPassword("swetha");
//	System.out.println(controller.authenticate(jwt));
//			
////		Mockito.doReturn(a).when(repo).findByUsername("test");
////
//////	User u = new User("test", "test", "test", "test", "female", "9566449690");
////userDetails = jwtservice.loadUserByUsername("test");
////	token = util.generateToken(userDetails);
//////	uList = new ArrayList<User>();
//////	uList.add(u);
//////	Mockito.doReturn(uList).when(repo).findByUsername("test");
////////	Mockito.doReturn(new Tweets()).when(tweetService).addTweet("test1", new Tweets());
////	}
	}
	@Test
	@Order(1)
	public void mockForRegister() throws JsonProcessingException, Exception

	{
		User user1=new User();
		user1.setUsername("abc");
		user1.setFirstname("abc");
		user1.setLastname("abc");
		user1.setPassword("abc");
		user1.setGender("female");
		user1.setContactno("1566644979");
		

		User user2=new User();
		user2.setUsername("mac");
		user2.setFirstname("mac");
		user2.setLastname("abc");
		user2.setPassword("abc");
		user2.setGender("female");
		user2.setContactno("1566644979");
		List<User> a=new ArrayList<>();
		a.add(user2);
//		a.add(new User());
		when(repo.findByUsername(Mockito.any(String.class))).thenReturn(null);
	     when(repo.save(Mockito.any(User.class))).thenReturn(user1);

//		Mockito.when(service.saveUser(any(User.class))).thenReturn(user1);
//		Mockito.doReturn(new User()).when(repo).save(user1);
//		Mockito.doReturn(new UserDTO()).when(service).saveUser(user1);
//		UserDTO savedUser = usermodelservice.saveUser(user);

//				User Saveduser = userrepository.save(user);
	     
	 	when(repo.findAll()).thenReturn(a);

		UserRegisterationRequest user=new UserRegisterationRequest();
		user.setUsername("abc");
		user.setFirstname("abc");
		user.setLastname("abc");
		user.setPassword("abc");
		user.setGender("female");
		user.setContactno("1566644979");
		System.out.println(user);
	mockMvc.perform
	(post("/api/v1.0/tweets/register")
	.contentType("application/json")
	.content(objectMapper.writeValueAsString(user)))
 .andExpect(status().isCreated());
	a.add(user1);
	when(repo.findAll()).thenReturn(a);
	
	
	mockMvc.perform
	(post("/api/v1.0/tweets/register")
	.contentType("application/json")
	.content(objectMapper.writeValueAsString(user)))
 .andExpect(status().is4xxClientError());

	}

	
	@Test
	@Order(2)
	public void mockWhenForgot() throws JsonProcessingException, Exception 
//
	{
//
			ForgotRequest u=new ForgotRequest();
			u.setUsername("swetha");
			u.setNewPassword("swe");
			u.setConfirmPassword("swe");
			List<com.tweetapp.entities.User> user= new ArrayList<>();
			  user.add(new com.tweetapp.entities.User("swetha","swe","swetha","p","female","9566644979"));
				when(repo.findByUsername(Mockito.any(String.class))).thenReturn(Optional.of(user));
//			     when(repo.save(Mockito.any(User.class)));
					when(repo.save(Mockito.any(User.class))).thenReturn(new User());
			mockMvc.perform(post("/api/v1.0/tweets/swetha/forgot")
			.contentType("application/json")
			.content(objectMapper.writeValueAsString(u)))
			.andExpect(status().isOk());			
		}
	
	@Test
	@Order(3)
	public void mockWhenForgotWithError() throws JsonProcessingException, Exception 
//
	{
//
			ForgotRequest u=new ForgotRequest();
			u.setUsername("swathi");
			u.setNewPassword("swa");
			u.setConfirmPassword("swat");
			List<com.tweetapp.entities.User> user= new ArrayList<>();
			  user.add(new com.tweetapp.entities.User("swathi","swat","swathi","p","female","9566644979"));
				
			  when(repo.findByUsername(Mockito.any(String.class))).thenReturn(Optional.of(user));
//			     when(repo.save(Mockito.any(User.class)));
					when(repo.save(Mockito.any(User.class))).thenReturn(new User());
			mockMvc.perform(post("/api/v1.0/tweets/swathi/forgot")
			.contentType("application/json")
			.content(objectMapper.writeValueAsString(u)))
			.andExpect(status().is4xxClientError());			
		}
	
//
@Test
@Order(4)
public void mockWhenForgotWithNoUserFound() throws JsonProcessingException, Exception 
//
{
//
		ForgotRequest u=new ForgotRequest();
		u.setUsername("swathi");
		u.setNewPassword("swat");
		u.setConfirmPassword("swat");
		List<com.tweetapp.entities.User> user= new ArrayList<>();
		  user.add(new com.tweetapp.entities.User("swathi","swathi","swathi","p","female","9566644979"));
			when(repo.findByUsername(Mockito.any(String.class))).thenReturn(Optional.ofNullable(new ArrayList<>()));
//		     when(repo.save(Mockito.any(User.class)));
//				when(repo.save(Mockito.any(User.class))).thenReturn(new User());
		mockMvc.perform(post("/api/v1.0/tweets/swathi/forgot")
				
		.contentType("application/json")
		.content(objectMapper.writeValueAsString(u)))
		.andExpect(status().isBadRequest());			
	}
}
//
//	
//}