package com.foodWorld.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foodWorld.entity.LoginRequest;
import com.foodWorld.entity.User;
import com.foodWorld.exception.InvalidCredentialsException;
import com.foodWorld.exception.UsernameNotFoundException;

@Service
public class LoginService {
	
	@Autowired
	private UserService userService;

	public LoginRequest loginValidate(LoginRequest loginRequest) throws UsernameNotFoundException, InvalidCredentialsException {
		Optional<User> userOptional = userService.getUserByUsername(loginRequest.getUsername());
	    if (!userOptional.isPresent()) {
	        throw new UsernameNotFoundException("User not found with username: " + loginRequest.getUsername());
	    }
	    User user = userOptional.get();
	    if (loginRequest.getUsername().equals(user.getUsername()) 
	    	    && loginRequest.getPassword().equals(user.getPassword())) {
	    	    return loginRequest;
	    	} else {
	    	    throw new InvalidCredentialsException("Invalid credentials for username: " + loginRequest.getUsername());
	    	}

	    
	}
}
