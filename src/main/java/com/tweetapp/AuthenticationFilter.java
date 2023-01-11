package com.tweetapp;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.tweetapp.services.JWTUserService;
import com.tweetapp.utility.JWTUtility;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JWTUtility jwtutil;

	@Autowired
	private JWTUserService jwtuserservice;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
//System.out.println("success");	
		String authorization = request.getHeader("Authorization");
		System.out.println("authorization: " + authorization);
		String token = null;
		String userName = null;

		if (null != authorization && authorization.startsWith("Bearer ")) {
			token = authorization.substring(7);
			userName = jwtutil.getUsernameFromToken(token);
		}
		if (null != userName && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = jwtuserservice.loadUserByUsername(userName);

			if (jwtutil.validateToken(token, userDetails)) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());

				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}

		}
		response.setHeader("Access-Control-Allow-Origin", request.getHeader("*"));
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, remember-me");
		filterChain.doFilter(request, response);
	}
}
