package com.foodWorld.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.foodWorld.entity.LoginRequest;
import com.foodWorld.exception.InvalidCredentialsException;
import com.foodWorld.service.LoginService;
import com.foodWorld.util.jwt.JwtUtil;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class LoginController {

	@Autowired
	private LoginService loginServe;

	@Autowired
	private JwtUtil jwtUtil;
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest)throws Exception {
		try {
			if(!loginServe.validateUser(loginRequest)) {
				throw new InvalidCredentialsException("Invalid username or password");
			}
			String token = jwtUtil.generateToken(loginRequest.getUsername());
			return ResponseEntity.ok(new loginResponse(token));
		}catch (BadCredentialsException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
		}
	}
	
	 @PostMapping("/validate-token")
	    public ResponseEntity<Boolean> validateToken(String token) {
	        try {	      
	            String username = jwtUtil.extractUsername(token);
	            boolean isValid = jwtUtil.validateToken(token, username);
	            return ResponseEntity.ok(isValid);
	        } catch (Exception e) {
	            return ResponseEntity.ok(false);
	        }
	    }

}
