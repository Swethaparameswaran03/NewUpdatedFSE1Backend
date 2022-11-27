package com.tweetapp.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tweetapp.entities.User;
import com.tweetapp.repositories.UserRepository;


@Service
public class JWTUserService implements UserDetailsService {

	@Autowired
	private UserRepository userrepository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       // return new User("admin","$2a$12$UlCv2M1ypGh88MGkwXWPd.5EChKeEVFEkpS93igexIIPFwXzDqYDO",new ArrayList<>());
		java.util.Optional<List<com.tweetapp.entities.User>> a= userrepository.findByUsername(username);
		if(a.isPresent())
		{
			User b=a.get().get(0);
		org.springframework.security.core.userdetails.User f=
				new org.springframework.security.core.userdetails.User(b.getUsername(),
				b.getPassword(), Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"))); 
		
		return f;

		}
		return null;
	}

}
