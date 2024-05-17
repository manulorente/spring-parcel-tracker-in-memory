package com.dam.parcelmanagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dam.parcelmanagement.model.User;

import com.dam.parcelmanagement.repository.UserRepository;
import com.dam.parcelmanagement.exception.ResourceNotFoundException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressService addressService;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    public User getUserById(Long id) {
        return this.userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    public User getUserByUserName(String userName) {
        return this.userRepository.findByUsername(userName)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + userName));
    }

    public User updateUser(User userDetails) {
        User newUser = this.getUserByUserName(userDetails.getUsername());

        this.addressService.updateAddress(userDetails.getAddress().getId(), userDetails.getAddress());

        newUser.setUsername(userDetails.getUsername());
        newUser.setPassword(userDetails.getPassword());
        newUser.setRole(userDetails.getRole());
        newUser.setAddress(userDetails.getAddress());

        return this.userRepository.save(newUser);
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
    
    public void deleteUserByUserName(String userName) {
        Optional<User> user = this.userRepository.findByUsername(userName);
        if (user == null) {
            throw new ResourceNotFoundException("User not found with username: " + userName);
        }
        this.userRepository.delete(user.get());
    }

}
