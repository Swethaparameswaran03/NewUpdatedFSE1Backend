package com.tweetapp.controllers;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tweetapp.dto.UserDTO;
import com.tweetapp.entities.User;
import com.tweetapp.exception.UserException;
import com.tweetapp.http.UserRegisterationRequest;
import com.tweetapp.model.ForgotRequest;
import com.tweetapp.model.JwtRequest;
import com.tweetapp.model.JwtResponse;
import com.tweetapp.repositories.UserRepository;
import com.tweetapp.services.JWTUserService;
import com.tweetapp.services.UserModelService;
import com.tweetapp.utility.JWTUtility;

@RestController
public class UserController {

	@Autowired
	private UserRepository userrepository;

	@Autowired
	private UserModelService usermodelservice;

	@Autowired
	private JWTUtility jwtutil;

	@Autowired
	private AuthenticationManager authenticationmanager;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JWTUserService jwtuserService;

	// method to register new user
	@PostMapping("api/v1.0/tweets/register")
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<?> registerUser(@RequestBody UserRegisterationRequest request)
			throws IllegalAccessException, InvocationTargetException, UserException {
		List<User> users = userrepository.findAll();
		for (User TotalUsers : users) {
			if (TotalUsers.getUsername().equals(request.getUsername())) {
				return new ResponseEntity<>("Username already exists!", HttpStatus.CONFLICT);
			}
		}
		User user = new User();
		System.out.println(request.getContactno());
//		BeanUtils.copyProperties(user, request);
		user.setUsername(request.getUsername());
		user.setPassword(request.getPassword());
		user.setFirstname(request.getFirstname());
		user.setLastname(request.getLastname());
		user.setGender(request.getGender());
		user.setContactno(request.getContactno());

		UserDTO savedUser = usermodelservice.saveUser(user);
		return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
	}

	// method for login
	@PostMapping("api/v1.0/tweets/login")
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<?> authenticate(@RequestBody JwtRequest jwtrequest) throws Exception {
		try {
			authenticationmanager.authenticate(
					new UsernamePasswordAuthenticationToken(jwtrequest.getUsername(), jwtrequest.getPassword()));
		} catch (Exception e) {
			return new ResponseEntity<>("Not authorized", HttpStatus.BAD_REQUEST);
		}
		final UserDetails userDetails = jwtuserService.loadUserByUsername(jwtrequest.getUsername());
		System.out.println(userDetails);
		final String token = jwtutil.generateToken(userDetails);
		return new ResponseEntity<>(new JwtResponse(token), HttpStatus.OK);
	}

	// method for forgot password
	@PostMapping("api/v1.0/tweets/{username}/forgot")
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<?> forgot(@PathVariable String username, @RequestBody ForgotRequest forgot)
			throws UserException

	{
		List<User> user = userrepository.findByUsername(username).get();
		for (User j : user) {
			if (j.getUsername().equals(username)) {
				try {
					User a = usermodelservice.forgot(forgot, username);
					return new ResponseEntity<>(a, HttpStatus.OK);
				} catch (Exception e) {
					return new ResponseEntity<>("Password and Confirm password should be same", HttpStatus.BAD_REQUEST);

				}
			}
		}
		return new ResponseEntity<>("User doesn't exists", HttpStatus.BAD_REQUEST);

	}

//	@DeleteMapping("api/v1.0/tweets/{username}/delete")
//	public ResponseEntity<?> delete(@PathVariable String username)
//			throws UserException {
//		try {
//	 usermodelservice.deleteUser(username);
//			return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
//		} catch (Exception e) {
//			return new ResponseEntity<>("Delete failed", HttpStatus.BAD_REQUEST);
//
//		}
//	}

}
