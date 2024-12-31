package com.foodWorld.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.foodWorld.entity.User;
import com.foodWorld.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Return Optional<User> as findById does already
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);  
    }

    public User addUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
    
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
