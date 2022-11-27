package com.tweetapp.services;



import com.tweetapp.entities.User;
import com.tweetapp.exception.UserException;


public interface UserService {
	
	String save(User user);
	 //public User forgot(ForgotRequest forgot,String username);
	
//	public User findByUsername(String username) throws UserException;



}
