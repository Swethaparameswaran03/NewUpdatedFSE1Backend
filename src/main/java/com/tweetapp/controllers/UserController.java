package com.tweetapp.controllers;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
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
	
	//method to register new user
    @PostMapping("api/v1.0/tweets/register")
    @CrossOrigin(origins="http://localhost:4200")
	public ResponseEntity<?> registerUser(@RequestBody UserRegisterationRequest request) throws IllegalAccessException, InvocationTargetException, UserException 
	{
    	List<User> users = userrepository.findAll();
         for(User TotalUsers:users)
       {
      if (TotalUsers.getUsername().equals(request.getUsername())) 
		{
   		return new ResponseEntity<>("Username already exists!",HttpStatus.CONFLICT);
		}
       }
    	User user=new User();
		BeanUtils.copyProperties(user, request);
		UserDTO savedUser = usermodelservice.saveUser(user);
		return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
	}
//      List<User> users = userrepository.findAll();
//       for(User TotalUsers:users)
//        {
//       if (TotalUsers.getUsername().equals(user.getUsername())) 
//		{
//    		return new ResponseEntity<>("Username already exists!",HttpStatus.CONFLICT);
//		}
//     }
//		User user1=new User();
//		user1.setUsername(user.getUsername());
//		user1.setFirstname(user.getFirstname());
//		user1.setLastname(user.getLastname());
//		user1.setGender(user.getGender());
//		user1.setContactno(user.getContactno());
//		user1.setPassword(passwordEncoder.encode(user.getPassword()));
//		userrepository.save(user1);
//		return new ResponseEntity<>(userrepository.save(user1),HttpStatus.OK);
//	}
/* @PostMapping("api/v1.0/tweets/login")
	public ResponseEntity<?> login(@RequestBody LoginDTO login)
	{
		List<User> users = userrepository.findAll();
	       for(User TotalUsers:users)
	        {
	           if (TotalUsers.getUsername().equals(login.getUsername())) 
	           {
	        	   String username=login.getUsername();
	        	   String pass=login.getPassword();
	       			//Authentication filter=
	       	        //SecurityContextHolder.getContext().setAuthentication(authentication);
		       		return new ResponseEntity<>("login succedded",HttpStatus.OK);
	       		}

	           }
	        
	       		return new ResponseEntity<>("Username does not exists",HttpStatus.NOT_FOUND);      
		
	}*/
    //method for login
    @PostMapping("api/v1.0/tweets/login")
    @CrossOrigin(origins="http://localhost:4200")
    public ResponseEntity<?> authenticate(@RequestBody JwtRequest jwtrequest) throws Exception
    {
    	try
    	{
    	authenticationmanager.authenticate(
    			new UsernamePasswordAuthenticationToken(
    					jwtrequest.getUsername(),
    					jwtrequest.getPassword()
    					)
        );
    	}catch(Exception e)
    	{
    	      return  new ResponseEntity<>("Not authorized",HttpStatus.BAD_REQUEST);
    	}
      final UserDetails userDetails=jwtuserService.loadUserByUsername(jwtrequest.getUsername());
      final String token=jwtutil.generateToken(userDetails);
      //return new JwtResponse(token);
      return  new ResponseEntity<>(new JwtResponse (token),HttpStatus.OK);
    }
    //method for forgot password
    @PostMapping("api/v1.0/tweets/{username}/forgot")
    @CrossOrigin(origins="http://localhost:4200")
   public ResponseEntity<?> forgot(@PathVariable String username,@RequestBody ForgotRequest forgot) throws UserException
   {
    	try {
	User a=usermodelservice.forgot(forgot, username);
	return new ResponseEntity<>(a,HttpStatus.OK); 
   }
    catch(Exception e)
    {
	      return  new ResponseEntity<>("Password and Confirm password should be same",HttpStatus.BAD_REQUEST);

    }
   }
  
    


}
