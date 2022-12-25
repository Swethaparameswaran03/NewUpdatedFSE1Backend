package com.tweetapp.services;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.tweetapp.dto.UserDTO;
import com.tweetapp.entities.Tweet;
import com.tweetapp.entities.TweetLike;
import com.tweetapp.entities.TweetReply;
import com.tweetapp.entities.User;
import com.tweetapp.exception.TweetException;
import com.tweetapp.exception.UserException;
import com.tweetapp.model.ForgotRequest;
import com.tweetapp.model.TweetPostRequest;
import com.tweetapp.model.TweetReplyRequest;
import com.tweetapp.repositories.TweetLikeRepository;
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

	@Autowired
	private TweetLikeRepository like;

	public UserDTO saveUser(User user) throws UserException {
		user.setUsername(user.getUsername());
		user.setPassword(passwordencoder.encode(user.getPassword()));

		User Saveduser = userrepository.save(user);
		UserDTO dto = convertUsertoUserDto(Saveduser);

		return dto;
	}

	private UserDTO convertUsertoUserDto(User user) {
		return new UserDTO(user.getUsername(), user.getFirstname(), user.getLastname(), user.getContactno(),
				user.getGender(), user.getPassword());
	}

	public User forgot(ForgotRequest forgot, String username) throws UserException {
		if (forgot.getConfirmPassword().equals(forgot.getNewPassword())) {
			Optional<List<User>> optionalUser = userrepository.findByUsername(username);
			User user = null;
			if (optionalUser.isPresent()) {
				user = optionalUser.get().get(0);
				user.setPassword(passwordencoder.encode(forgot.getConfirmPassword()));
				userrepository.save(user);
			} else {
				throw new UserException("User Not Found");
			}

			return user;
		}
		throw new UserException("Password and Confirm password should be same");
	}

	public Tweet postNewTweet(TweetPostRequest tweetRequest, String username)
			throws IllegalAccessException, InvocationTargetException, UserException {
		List<User> a = userrepository.findAll();
		for (User g : a) {
			if (username.equals(g.getUsername())) {
				List<User> user = userrepository.findByUsername(username).get();
				Tweet tweet = new Tweet();
				tweet.setTweetReplies(new ArrayList<TweetReply>());
				tweet.setTweetText(tweetRequest.getTweetText());
				tweet.setTweetId(gen.generateSequence(User.SEQUENCE_NAME));
				tweet.setTweetDate(new Date());
				tweet.setUser(user);
				if (username.equals(userrepository.findByUsername(username))) {
					tweet = tweetrepository.save(tweet);
				}
				return tweetrepository.save(tweet);
			}
		}
		throw new UserException("There are no user available!!");
	}

//
	public TweetReply replyTweet(TweetReplyRequest request, long tweetId, String username)
			throws UserException, TweetException {
		List<Tweet> tweetList = tweetrepository.findAll();
		Tweet tweet = tweetrepository.findByTweetId(tweetId);
		if (!tweet.equals(null)) {
			List<User> user = findByUsername(username);
			TweetReply tweetReply = new TweetReply();
			tweetReply.setRepliedDate(new Date());
			tweetReply.setTweet(tweet);
			tweetReply.setUser(user);
			if (user.get(0).getUsername().equals(username)) {
				tweetReply.setReplyMessage(request.getReplyMessage());
				tweetReply.setParenttweetId(tweetId);
				repo.save(tweetReply);
				return tweetReply;
			}
		} else {
//			return null;
			throw new TweetException("There are no tweets available for this user to reply!!");
		}
		return null;
	}

	public List<Tweet> displayAllTweets() throws TweetException {
		List<Tweet> totalTweets = tweetrepository.findAll();
		if (!totalTweets.isEmpty()) {
			List<TweetReply> replies = repo.findAll();
			List<Tweet> total = new ArrayList<>();
			for (Tweet u : totalTweets) {
				Tweet i = u;
				i.setTweetReplies(repo.findByParenttweetId(i.getTweetId()));
//				List<TweetLike> findByTweet = like.findByTweet(u);
//				System.out.println("TWEET LIKES:"+findByTweet);
//				i.setLikes(findByTweet.size());

				System.out.println("Tid: " + i.getTweetId() + "Likes:" + i.getLikes());
				System.out.println("Tid: " + i.getTweetId() + "Likes:" + i.getTweetReplies());

				total.add(i);
			}

			// totalTweets.stream().forEach(System.out::println);
			return total;
		} else {
			throw new TweetException("There are no tweets available!!");
		}
	}

	public Iterable<User> displayAllUsers() throws TweetException {
		List<User> total = userrepository.findAll();
		total.forEach((p) -> System.out.println(p.getUsername()));
		return total;

	}

	public List<Tweet> displayAllTweetsOfUser(String username) throws UserException, TweetException {
		List<User> user = findByUsername(username);

		List<Tweet> a = tweetrepository.findTweetByUserId(user.get(0).getUsername());
		if (!a.isEmpty()) {
			List<Tweet> total = new ArrayList<>();
			for (Tweet u : a) {
				Tweet i = u;
				i.setTweetReplies(repo.findByParenttweetId(i.getTweetId()));
				total.add(i);
			}

			a.stream().forEach(System.out::println);
			return total;
		} else {
			throw new TweetException("There are no tweets available for this user!!");

		}
	}

	public List<User> findByUsername(String username) throws UserException {

		Optional<List<User>> optional = userrepository.findByUsername(username);
		if (optional.isPresent())
			return optional.get();

		throw new UserException("User Not Found");
	}

	public List<User> searchbyUsername(String username) throws UserException {
		return userrepository.findByUsernameContains(username);
	}

	public Tweet updateTweetofUser(String username, long tweetId, TweetPostRequest tweetResponse)
			throws TweetException {
		List<User> total = userrepository.findAll();
		for (User t : total) {
			if (username.equals(t.getUsername())) {
				Tweet tweet = tweetrepository.findByTweetId(tweetId);
				if (t.getUsername().equals(tweet.getUser().get(0).getUsername())) {
					tweet.setTweetText(tweetResponse.getTweetText());
					Tweet a = tweetrepository.save(tweet);
					a.setTweetReplies(repo.findByParenttweetId(tweetId));
					return a;
				} else {
					throw new TweetException("user and id doesnt match");
				}
			}
		}
		throw new TweetException("user and id doesnt match");

	}

	public Tweet deleteTweetofUser(String username, long tweetId) throws TweetException {
		List<User> total = userrepository.findAll();
		for (User t : total) {
			if (username.equals(t.getUsername())) {
				Tweet tweet = tweetrepository.findByTweetId(tweetId);
				if (t.getUsername().equals(tweet.getUser().get(0).getUsername())) {
					Tweet ti= tweetrepository.deleteByTweetId(tweetId);
					like.deleteByTweetId(tweetId);
					return ti;
				}
			}
		}
		throw new TweetException("There are no tweets available for this user!!");
	}

//	
	public void likeTweetofUser(String username, long tweetId) {
//		List<User> total = userrepository.findAll();
//		for (User t : total) {
//			if (username.equals(t.getUsername())) {
//				Tweet tweet = tweetrepository.findByTweetId(tweetId);
////				if (t.getUsername().equals(tweet.getUser().get(0).getUsername())) {
//				if(username.equals(tweet.getUser().get(0).getUsername())) {
		Tweet tweet = tweetrepository.findByTweetId(tweetId);
		User user = userrepository.findByUsername(username).get().get(0);
		System.out.println(user);
		System.out.println(tweet);

		if (checkIflikeIsPresent(username, tweetId)) {
			TweetLike likes = like.findByUsernameAndTweetId(username, tweetId);
			System.out.println(likes.getUsername() + " " + likes.getTweetId());
			int deleteByUserAndTweet = like.deleteByUsernameAndTweetId(username, tweetId);
			tweet.setLikes(tweet.getLikes() - 1);
			tweetrepository.save(tweet);
			System.out.println(tweetrepository.save(tweet));
			System.out.println(deleteByUserAndTweet);
			System.out.println("Tid: " + tweetId + " User: " + username + " DisLIKED");
		}

		else {
			long one=like.findAll().size();
			TweetLike likes = new TweetLike();
			likes.setId(one);
			likes.setTweetId(tweetId);
			likes.setUsername(username);
			tweet.setLikes(tweet.getLikes() + 1);
			tweetrepository.save(tweet);
			like.save(likes);
			System.out.println("Tid: " + tweetId + " User: " + username + " LIKED");
			return;
		}
//					if(tweet.getLikes()<1)
//					{	
//					tweet.setLikes(tweet.getLikes() + 1);
//					}
//					else
//					{
//						tweet.setLikes(tweet.getLikes()-1);
//					}
//				
//					Tweet a = tweetrepository.save(tweet);			
//					a.setTweetReplies(repo.findByParenttweetId(tweetId));
//					System.out.println(a);
//					return a;

//  throw new TweetException("There are no tweets available for this user!!");
	}

	private boolean checkIflikeIsPresent(String username, long tweetId) {
		Tweet tweet = tweetrepository.findByTweetId(tweetId);
		User user = userrepository.findByUsername(username).get().get(0);
		System.out.println("checkIflikeIsPresent");
		TweetLike l = like.findByUsernameAndTweetId(username, tweetId);
		System.out.println("checkIflikeIsPresent Completed");
		System.out.println(l);
		return (l != null);
	}

//	public TweetLike li(User user,Tweet tweet)
//	{
//		TweetLike re=like.findByUserAndTweet(user, tweet);
//		return null;
//		
//	}
//	
	public String findUsernameOfLikesPresent(String username, long tweetId) {
		Tweet tweet = tweetrepository.findByTweetId(tweetId);
		User user = userrepository.findByUsername(username).get().get(0);
		System.out.println(user);
		System.out.println(tweet);
List<TweetLike> findByTweet = like.findAll();

List<TweetLike> filteredTweets = findByTweet.stream().filter(t->t.getTweetId()==tweetId).collect(Collectors.toList());

System.out.println("findByTweet: "+findByTweet);
		if (filteredTweets.size()>0) {
			TweetLike likes = like.findByUsernameAndTweetId(username, tweetId);
			List<TweetLike> tweetlist = like.findByTweetId(tweetId);
			int count = 0;
			StringBuilder names = new StringBuilder("");
//						tweetlist.forEach(System.out::println);
			System.out.println("tweetLikeList: "+filteredTweets);
			
			boolean moreThanTwoUser = true;
			for (int i = 0; i < 2; i++) {
				try {
					System.out.println("TWEET LOOP: "+filteredTweets.get(i).getUsername());
					names.append(filteredTweets.get(i).getUsername() + ",");
				} catch (Exception e) {
					System.out.println(e);
					names.subSequence(0, names.length() - 2);
					moreThanTwoUser = false;
				}

			}
			System.out.println("Before Names: "+names );
			if (moreThanTwoUser) {
				System.out.println(names.lastIndexOf(","));
				names.replace(names.lastIndexOf(","),names.lastIndexOf(",")+1,"");
				 //names.subSequence(0, names.lastIndexOf(",")).toString();
				//System.out.println(subSequence);
				System.out.println("After Sub: "+names);
				if (filteredTweets.size() > 2) {
					names.append(" + " + ((filteredTweets.size()) - 2) + " more");
				} else {
					int indexOfComma = names.indexOf(",");
					names.replace(indexOfComma, indexOfComma + 1, " and ");

				}
				System.out.println("more than two user:" +names.toString());
			} else {
				System.out.println(names.lastIndexOf(","));
				names.replace(names.lastIndexOf(","),names.lastIndexOf(",")+1,"");
				System.out.println("one user:" +names.toString());
			}
//			System.out.println(likes.getUser().getUsername());
//			System.out.println(likes.getTweet().getTweetId());
			System.out.println("FINAL Anmes: "+names.toString());
			return names.toString();
//					return;
		}
//					else
//					{
//						return null;
//	
		return "You're are first person to like";

	}
}
