package com.tweetapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import com.tweetapp.services.JWTUserService;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter  {
	
	@Autowired
	private AuthenticationFilter filter;
	
	@Autowired
	private JWTUserService jwtuserService;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	public void configure(HttpSecurity http) throws Exception {
		http.csrf()
		.disable().cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues()).and()
         // ... here goes your custom security configuration
         .authorizeRequests()
         .antMatchers(AUTH_WHITELIST)
         .permitAll() 
         // whitelist Swagger UI resources
         .anyRequest()
         // ... here goes your custom security configuration
        .authenticated()
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
         http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

        // require authentication for any endpoint that's not whitelisted
 }
		
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(jwtuserService);
    }
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
   }
	
	private static final String[] AUTH_WHITELIST = {
            // -- Swagger UI v2
            "/api/v1.0/tweets/register",
            "/api/v1.0/tweets/login",
            "api/v1.0/tweets/{username}/forgot"
			};


}
