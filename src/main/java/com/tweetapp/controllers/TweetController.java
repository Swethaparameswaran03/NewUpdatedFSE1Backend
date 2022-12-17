package com.tweetapp.controllers;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tweetapp.entities.Tweet;
import com.tweetapp.entities.TweetLike;
import com.tweetapp.entities.TweetReply;
import com.tweetapp.entities.User;
import com.tweetapp.exception.TweetException;
import com.tweetapp.exception.UserException;
import com.tweetapp.model.TweetPostRequest;
import com.tweetapp.model.TweetReplyRequest;
import com.tweetapp.services.JWTUserService;
import com.tweetapp.services.UserModelService;
import com.tweetapp.utility.JWTUtility;

@RestController
public class TweetController {

	@Autowired
	private UserModelService usermodelService;

	@Autowired
	private JWTUtility jwtutil;

	@Autowired
	private AuthenticationManager authenticationmanager;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JWTUserService jwtuserService;

	// method to post a new tweet
	@PostMapping("api/v1.0/tweets/{username}/add")
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<?> postingNewTweet(@PathVariable String username, @RequestBody TweetPostRequest tweetreq,
			@RequestHeader("Authorization") String token)
			throws UserException, IllegalAccessException, InvocationTargetException {
		try {
			Tweet a = usermodelService.postNewTweet(tweetreq, username);
			return new ResponseEntity<>(a, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);

		}
	}

	// method to get all tweets
	@GetMapping("api/v1.0/tweets/all")
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<?> findAllTweets(@RequestHeader("Authorization") String token) throws TweetException {
		try {
			List<Tweet> tot = usermodelService.displayAllTweets();
			return new ResponseEntity<>(tot, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("There are no tweets Available!", HttpStatus.NOT_FOUND);

		}
	}

	// method to get all users
	@GetMapping("api/v1.0/tweets/users/all")
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<?> findAllUsers(@RequestHeader("Authorization") String token) throws TweetException {

		Iterable<User> a = usermodelService.displayAllUsers();
		return new ResponseEntity<>(a, HttpStatus.OK);

	}

	// method to get all tweets of particular user
	@GetMapping("api/v1.0/tweets/{username}")
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<?> findAllTweetsOfUser(@PathVariable String username,
			@RequestHeader("Authorization") String token) throws TweetException {
		try {
			List<Tweet> a = usermodelService.displayAllTweetsOfUser(username);
			return new ResponseEntity<>(a, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("There are no tweets Available for this user!", HttpStatus.NOT_FOUND);

		}

	}

	// method to search user by username
	@GetMapping("api/v1.0/tweets/user/search/{username}")
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<?> searchByUsername(@PathVariable String username,
			@RequestHeader("Authorization") String token) throws TweetException {
		try {
			List<User> a = usermodelService.searchbyUsername(username);
			return new ResponseEntity<>(a, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("The above user is not found!!", HttpStatus.NOT_FOUND);

		}

	}

	// method to update tweet of a user
	@PutMapping("api/v1.0/tweets/{username}/update/{tweetId}")
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<?> UpdateTweet(@PathVariable String username, @PathVariable long tweetId,
			@RequestHeader("Authorization") String token, @RequestBody TweetPostRequest tweetreponse)
			throws TweetException {
		try {
			Tweet a = usermodelService.updateTweetofUser(username, tweetId, tweetreponse);
			return new ResponseEntity<>(a, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("The above username and id is not found!!", HttpStatus.NOT_FOUND);
//    	}

		}

	}

	// method to delete tweet of a user
	@DeleteMapping("api/v1.0/tweets/{username}/delete/{tweetId}")
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<?> DeleteTweet(@PathVariable String username, @PathVariable long tweetId,
			@RequestHeader("Authorization") String token) throws TweetException {
		try {
			Tweet a = usermodelService.deleteTweetofUser(username, tweetId);
			return new ResponseEntity<>(a, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("The above username and id is not found!!", HttpStatus.NOT_FOUND);
		}

	}

//    
	// method to like tweet of a user
	@PutMapping("api/v1.0/tweets/{username}/like/{tweetId}")
	@CrossOrigin(origins = "http://localhost:4200")
	public void likeTweet(@PathVariable String username, @PathVariable long tweetId,
			@RequestHeader("Authorization") String token) throws TweetException {
//		try {
		System.out.println("enter");
		usermodelService.likeTweetofUser(username, tweetId);
//			return new ResponseEntity<>(a, HttpStatus.OK);
//		} catch (Exception e) {
//			return new ResponseEntity<>("The above username and id is not found!!", HttpStatus.NOT_FOUND);
//		}

	}

	// method to reply to tweet of a user
	@PostMapping("api/v1.0/tweets/{username}/reply/{tweetId}")
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<?> replyTweet(@RequestBody TweetReplyRequest replyRequest, @PathVariable long tweetId,
			@PathVariable String username, @RequestHeader("Authorization") String token)
			throws TweetException, UserException {
		try {

			TweetReply a = usermodelService.replyTweet(replyRequest, tweetId, username);
			return new ResponseEntity<>(a, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>("The above username and id is not available!!", HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("api/v1.0/tweets/{username}/findWhoLiked/{tweetId}")
	@CrossOrigin(origins = "http://localhost:4200")
	@ResponseBody
	public String whoLiked(@PathVariable String username, @PathVariable long tweetId,
			@RequestHeader("Authorization") String token)
			throws TweetException, UserException {
		try {

			String a = usermodelService.findUsernameOfLikesPresent(username,tweetId);
			//return new ResponseEntity<>(a, HttpStatus.OK);
			return a;
		} catch (Exception e) {
			//return new ResponseEntity<>("The LIKE is not available for above username and tweetid !!", HttpStatus.NOT_FOUND);
			return ("The LIKE is not available for above username and tweetid !!");
		}
	}
}