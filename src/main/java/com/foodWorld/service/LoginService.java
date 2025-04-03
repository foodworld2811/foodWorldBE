package com.foodWorld.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.foodWorld.entity.LoginRequest;
import com.foodWorld.entity.User;
import com.foodWorld.exception.InvalidCredentialsException;
import com.foodWorld.exception.UsernameNotFoundException;

@Service
public class LoginService {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean validateUser(LoginRequest loginRequest) throws UsernameNotFoundException, InvalidCredentialsException {
        Optional<User> userOptional = userService.getUserByUsername(loginRequest.getUsername());

        // If user is not found
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("User not found with username: " + loginRequest.getUsername());
        }

        User user = userOptional.get();

        // Validate password
        if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return true;  // Credentials are valid
        } else {
            throw new InvalidCredentialsException("Invalid password for username: " + loginRequest.getUsername());
        }
    }
}
