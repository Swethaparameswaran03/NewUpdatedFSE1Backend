package com.tweetapp.test;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tweetapp.model.JwtRequest;
import com.tweetapp.repositories.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
//@WebMvcTest(UserController.class)
@TestPropertySource(properties = "spring.mongodb.embedded.version=3.5.5")
public class LogTests {
	
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private UserRepository repo;

	@Test
	@Order(1)
	public void mockWhenLogin() throws JsonProcessingException, Exception 

	{

		JwtRequest u=new JwtRequest();

		u.setUsername("swetha");

		u.setPassword("password");

//	List<com.tweetapp.entities.User> user= new ArrayList<>();
//		  user.add(new com.tweetapp.entities.User("swetha","swetha","swetha","p","female","9566644979"));
//			when(repo.findByUsername(Mockito.any(String.class))).thenReturn(Optional.of(user));

		mockMvc.perform(post("/api/v1.0/tweets/login")

		.contentType("application/json")

		.content(objectMapper.writeValueAsString(u)))

		.andExpect(status().isOk());
	}

	@Test
	@Order(2)
	public void mockWhenNotLogin() throws JsonProcessingException, Exception 

	{

		JwtRequest u=new JwtRequest();

		u.setUsername("test");

		u.setPassword("test");

//		List<com.tweetapp.entities.User> user= new ArrayList<>();
//		  user.add(new com.tweetapp.entities.User("swetha","swetha","swetha","p","female","9566644979"));
//			when(repo.findByUsername(Mockito.any(String.class))).thenReturn(Optional.of(user));

		mockMvc.perform(post("/api/v1.0/tweets/login")

		.contentType("application/json")

		.content(objectMapper.writeValueAsString(u)))

		.andExpect(status().is4xxClientError());
	}


}
