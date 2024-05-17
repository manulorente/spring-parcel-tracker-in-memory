package com.dam.parcelmanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dam.parcelmanagement.model.User;

import com.dam.parcelmanagement.repository.UserRepository;
import com.dam.parcelmanagement.exception.ResourceNotFoundException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    public User updateUser(Long userId, User userDetails) {
        User user = this.getUserById(userId);
                
        user.setUsername(userDetails.getUsername());
        user.setPassword(userDetails.getPassword());
        user.setRole(userDetails.getRole());
        user.setAddress(userDetails.getAddress());

        return this.userRepository.save(user);
    }

    public User createUser(User user) {
            if (user.getId() != null && this.userRepository.existsById(user.getId())) {
                throw new ResourceNotFoundException("User already exists with id: " + user.getId());
            }
            if (this.userRepository.findByUsername(user.getUsername()) != null) {
                throw new ResourceNotFoundException("User already exists with username: " + user.getUsername());
            }
        return this.userRepository.save(user);
    }
    
    public void deleteUser(User user) {
        User existingUser = getUserById(user.getId());
        userRepository.delete(existingUser);
    }

}
