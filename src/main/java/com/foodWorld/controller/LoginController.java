package com.foodWorld.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.foodWorld.entity.LoginRequest;
import com.foodWorld.exception.InvalidCredentialsException;
import com.foodWorld.exception.UsernameNotFoundException;
import com.foodWorld.service.LoginService;

@RestController
@RequestMapping("/api")
public class LoginController {

	@Autowired
	private LoginService loginServe;
	
	@PostMapping("/login")
	public ResponseEntity<?> validateLogin(@RequestBody LoginRequest loginRequest) {
	    try {
	        LoginRequest user = loginServe.loginValidate(loginRequest);
	        return ResponseEntity.ok(user); // Return the validated LoginRequest object
	    } catch (UsernameNotFoundException e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());
	    } catch (InvalidCredentialsException e) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error: " + e.getMessage());
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: Something went wrong");
	    }
	}


}
