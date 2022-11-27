package com.tweetapp.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.tweetapp.dto.LoginDTO;
import com.tweetapp.entities.Tweet;
import com.tweetapp.entities.User;
import com.tweetapp.exception.UserException;
import com.tweetapp.repositories.TweetRepository;
import com.tweetapp.repositories.UserRepository;

@Service
@Component
public abstract class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userrepository;

	@Autowired
	private User user;
	
	@Autowired
	private Tweet tweet;
	
	@Autowired
	private LoginDTO login;
	
	@Autowired
	private TweetRepository tweetrepo;
	
//	public User findByUsername(String username) throws UserException {
//		
//		 
//		Optional<User> optional = userrepository.findByUsernameContains(username);
//		if(optional.isPresent())
//		return optional.get();
//		
//		throw new UserException("User Not Found");
//	}
	

}
