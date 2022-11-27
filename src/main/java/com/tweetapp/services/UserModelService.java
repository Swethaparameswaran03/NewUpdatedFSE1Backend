package com.tweetapp.services;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tweetapp.dto.UserDTO;
import com.tweetapp.entities.Tweet;
import com.tweetapp.entities.TweetReply;
import com.tweetapp.entities.User;
import com.tweetapp.exception.TweetException;
import com.tweetapp.exception.UserException;
import com.tweetapp.model.ForgotRequest;
import com.tweetapp.model.TweetPostRequest;
import com.tweetapp.model.TweetReplyRequest;
import com.tweetapp.repositories.TweetReplyRepository;
import com.tweetapp.repositories.TweetRepository;
import com.tweetapp.repositories.UserRepository;

@Service
public class UserModelService {
	
@Autowired
private UserRepository userrepository;


@Autowired
private TweetRepository tweetrepository;

@Autowired
private TweetReplyRepository repo;

@Autowired
private SequenceGeneratorService gen;


@Autowired
private PasswordEncoder passwordencoder;

public UserDTO saveUser(User user) throws UserException {
//System.out.println("Saving user:"+user);
//		Optional<List<User>> isUserPresent = userrepository.findByUsername(user.getUsername());
//		if(isUserPresent.isPresent())
//			throw new UserException("Username: "+user.getUsername()+"already exist");
		user.setUsername(user.getUsername());
		user.setPassword(passwordencoder.encode(user.getPassword()));

		User Saveduser = userrepository.save(user);
		UserDTO dto = convertUsertoUserDto(Saveduser);

		return dto;
	}

	private UserDTO convertUsertoUserDto(User user) {
		return new UserDTO(user.getUsername(),user.getFirstname(),user.getLastname(),user.getContactno(),user.getGender(),user.getPassword());
}

	public User forgot(ForgotRequest forgot,String username)throws UserException {
		if(forgot.getConfirmPassword().equals(forgot.getNewPassword()))
		{
		Optional<List<User>> optionalUser = userrepository.findByUsername(username);
		User user=null;
		if(optionalUser.isPresent()) {
			user=optionalUser.get().get(0);
			user.setPassword(passwordencoder.encode(forgot.getConfirmPassword()));
			userrepository.save(user);
		}else {
			throw new UserException("User Not Found");
		}
		
		return user;
		}
		throw new UserException("Password and Confirm password should be same");
	}
	

//	imp ...public Tweet postNewTweet(TweetPostRequest tweetRequest, String username)
//			throws IllegalAccessException, InvocationTargetException, UserException 
//	{
//		
//		Tweet tweet = new Tweet();
//		tweet.setTweetText(tweetRequest.getTweetText());
//		if(username.equals(userrepository.findByUsername(username)))
//		{
//		 tweet =tweetrepository.save(tweet);
//	    
////		return tweet;
//		}
//		return tweet;
//		}
	public Tweet postNewTweet(TweetPostRequest tweetRequest, String username)
			throws IllegalAccessException, InvocationTargetException, UserException {
		List<User> a = userrepository.findAll();
		for (User g : a) {
			if (username.equals(g.getUsername())) {
				List<User> user = userrepository.findByUsername(username).get();
				Tweet tweet = new Tweet();
				tweet.setTweetText(tweetRequest.getTweetText());
                tweet.setTweetId(gen.generateSequence(User.SEQUENCE_NAME));
                tweet.setUser(user);
				if (username.equals(userrepository.findByUsername(username))) {
					tweet = tweetrepository.save(tweet);
				}
				return tweetrepository.save(tweet);
			}
		}
		throw new UserException("There are no user available!!");
	}
	
////	public Iterable<Tweet> displayAllTweets() throws TweetException
////	{
////		List<Tweet> totalTweets=tweetrepository.findAll();
////		System.out.println(totalTweets);
//////		if(!totalTweets.isEmpty())
//////		{
//////		totalTweets.stream().forEach(System.out::println);
////		return totalTweets;
//////		}
//////		else
//////		{
//////			throw new TweetException("There are no tweets available!!");
//////		}	
////	}
//	public List<Tweet> getAllTweet(){
//		return tweetrepository.findAll();
//	}
//	public Iterable<User> displayAllUsers() throws TweetException
//	{
//		List<User> total=userrepository.findAll();
//		total.forEach((p)-> System.out.println(p.getUsername()));	
//		return total;
//		
//     }
//	public List<Tweet> displayAllTweetsOfUser(String username) throws TweetException
//	{
//		Optional<List<User>> user=userrepository.findByUsername(username);
//
////		List <Tweet> total=tweetrepository.findAll();
////		for(Tweet t:total)
////		{
////		if(username.equals(t.getUser().getUsername()))
//		{
////		List<Tweet> tweets=tweetrepository.findByUser(user);
////		return tweets;
//		List<Tweet> tweets= tweetrepository.findByUser(user);
//		return tweets;
//		}
//		
////		throw new TweetException("There are no tweets available for this user!!");
//	}
//	
////	public List<Tweet> getAllTweetByUser(long userId) throws UserException{
////		User user = userService.findByUserId(userId);
////		
////		return tweetDao.findByUser(user);
////	}
//	
//
//	public Optional<List<User>> searchbyUsername(String username) throws UserException
//	{
//		List<User> total=userrepository.findAll();
//		for(User t:total)
//		{
//		if(username.equals(t.getUsername()))
//		{
//		Optional<List<User>> tweets=userrepository.findByUsername(username);
//		return tweets;
//		}
//		}
//		throw new UserException("The above user is not found!!");
//	}
//	
//	public Tweet updateTweetofUser(String username,long tweetId,TweetPostRequest tweetResponse) throws TweetException
//	{
//		List <Tweet> total=tweetrepository.findAll();
//		for(Tweet t:total)
//		{
//		if(username.equals(t.getUser().getUsername()))
//		{	
//    	Tweet tweet=tweetrepository.findByTweetId(tweetId).get();
//		tweet.setTweetText(tweetResponse.getTweetText());
//        return tweetrepository.save(tweet);
//		}
//		}
//        throw new TweetException("cant update");		
//	}
//
//		
//////		throw new TweetException("There are no tweets available for this user!!");
////		if (checkTweetIsValidUser(tweetId, username)) {
////			Tweet tweet = tweetrepository.findByTweetId(tweetId).get();
////			tweet.setTweetText(tweetResponse.getTweetText());
////			return tweetrepository.save(tweet);
////		}
////
////		return null;
////	}
//
//	public void deleteTweetofUser(String username,long tweetId) throws TweetException
//	{
//		Optional<List<User>> total=userrepository.findByUsername(username);
//		if(username.equals(total)) {
//			tweetrepository.deleteByTweetId(tweetId);
//	    	 System.out.println("deleted");
//
//		}
//		}
////		throw new TweetException("There are no tweets available for this user!!");
////		if (checkTweetIsValidUser(tweetId, username))
////		{
////		tweetrepository.deleteByTweetId(tweetId);	
////		}
////		return null;
//
//
//	
//	public Tweet likeTweetofUser(String username,long tweetId) throws TweetException
//	{
////		List <Tweet> total=tweetrepository.findAll();
////		for(Tweet t:total)
////		{
////		if(username.equals(t.getUser()))
////		{
//			Tweet tweets=tweetrepository.findByTweetId(tweetId).get();
//            tweets.setLikes(tweets.getLikes()+1);
//			return tweetrepository.save(tweets);
//			
////		}
////		}
////		throw new TweetException("There are no tweets available for this user!!");
//	}
//
	public TweetReply replyTweet(TweetReplyRequest request,long tweetId,String username) throws TweetException, UserException {
	Tweet tweet = tweetrepository.findByTweetId(tweetId);
	List<User> user=findByUsername(username);
//     List <Tweet> total=tweetrepository.findAll();
//	for(Tweet t:total)
//	{
//	if(username.equals(t.getUser().getClass()))
//    {
		TweetReply tweetReply=new TweetReply();
		tweetReply.setTweet(tweet);
		tweetReply.setUser(user);
		if(user.get(0).getUsername().equals(username))
		{
		tweetReply.setReplyMessage(request.getReplyMessage());
		return repo.save(tweetReply);
		}
		else
		{
   throw new TweetException("There are no tweets available for this user to reply!!");
		}
//	}
//	}
	}

	public List<Tweet> displayAllTweets() throws TweetException
	{
		List<Tweet> totalTweets=tweetrepository.findAll();
		if(!totalTweets.isEmpty())
		{
		totalTweets.stream().forEach(System.out::println);
		return totalTweets;
		}
		else
		{
			throw new TweetException("There are no tweets available!!");
		}	
	}
	public Iterable<User> displayAllUsers() throws TweetException
	{
		List<User> total=userrepository.findAll();
		total.forEach((p)-> System.out.println(p.getUsername()));	
		return total;
		
     }
	public List<Tweet> displayAllTweetsOfUser(String username) throws UserException, TweetException 
	{
List<User> user = findByUsername(username);
		
		List<Tweet> a=tweetrepository.findByUser(user);
		if(!a.isEmpty())
		{
		return a;
		}
		else
		{
	throw new TweetException("There are no tweets available for this user!!");
	
		}
	}
	public List<User> findByUsername(String username) throws UserException {
		
		 
		Optional<List<User>> optional = userrepository.findByUsername(username);
		if(optional.isPresent())
		return optional.get();
		
		throw new UserException("User Not Found");
	}
	public List<User> searchbyUsername(String username) throws UserException 
	{
		return userrepository.findByUsernameContains(username);
//		throw new UserException("The above user is not found!!");
	}
	
	public Tweet updateTweetofUser(String username,long tweetId,TweetPostRequest tweetResponse) throws TweetException 
//	{
	{
	List<User> total=userrepository.findAll();
for(User t:total)
	{
	if(username.equals(t.getUsername()))
	{
		Tweet tweet = tweetrepository.findByTweetId(tweetId);
		if(t.getUsername().equals(tweet.getUser().get(0).getUsername()))
		{
	    tweet.setTweetText(tweetResponse.getTweetText());
	    return tweetrepository.save(tweet);
		}
		else
		{
			throw new TweetException("user and id doesnt match");
		}
	}
	}
throw new TweetException("user and id doesnt match");

}

	public Tweet deleteTweetofUser(String username,long tweetId) throws TweetException
	{
		List<User> total=userrepository.findAll();
		for(User t:total)
		{
			if(username.equals(t.getUsername()))
			{
				Tweet tweet = tweetrepository.findByTweetId(tweetId);
				if(t.getUsername().equals(tweet.getUser().get(0).getUsername()))
		{
			return tweetrepository.deleteByTweetId(tweetId);
		}
		}
		}
		throw new TweetException("There are no tweets available for this user!!");
	}
//	
	public Tweet likeTweetofUser(String username,long tweetId) throws TweetException
	{
		List<User> total=userrepository.findAll();
		for(User t:total)
		{
			if(username.equals(t.getUsername()))
			{
				Tweet tweet = tweetrepository.findByTweetId(tweetId);
				if(t.getUsername().equals(tweet.getUser().get(0).getUsername()))
		{
            tweet.setLikes(tweet.getLikes()+1);
			return tweetrepository.save(tweet);
		}
		}
		}
		throw new TweetException("There are no tweets available for this user!!");
		}


}