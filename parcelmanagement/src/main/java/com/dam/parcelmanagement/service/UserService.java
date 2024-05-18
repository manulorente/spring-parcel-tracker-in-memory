package com.dam.parcelmanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dam.parcelmanagement.model.Address;
import com.dam.parcelmanagement.model.Admin;
import com.dam.parcelmanagement.model.Customer;
import com.dam.parcelmanagement.model.User;
import com.dam.parcelmanagement.model.UserRole;
import com.dam.parcelmanagement.repository.UserRepository;

import jakarta.transaction.Transactional;

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

    @Transactional
    public User updateUser(User userDetails) {
        User newUser = this.getUserByUserName(userDetails.getUsername());

        Address updatedAddress = this.addressService.updateAddress(newUser.getAddress().getId(), userDetails.getAddress());
        newUser.setAddress(updatedAddress);

        if (userDetails.getPassword() != null) {
            newUser.setPassword(userDetails.getPassword());
        }
        if (userDetails.getRole() != null) {
            newUser.setRole(userDetails.getRole());
        }
        return this.userRepository.save(newUser);
    }

    @Transactional
    public User createUser(User user) {
        if (user.getId() != null && this.userRepository.existsById(user.getId())) {
            throw new ResourceNotFoundException("User already exists with id: " + user.getId());
        }
        if (this.userRepository.existsByUsername(user.getUsername())) {
            throw new ResourceNotFoundException("User already exists with username: " + user.getUsername());
        }
        Address newAddress = this.addressService.createAddress(user.getAddress());
        User newUser;
        if (user.getRole().equals(UserRole.ROLE_ADMIN)){
            newUser = new Admin(user.getUsername(), user.getPassword(), newAddress);
        }
        else {
            newUser = new Customer(user.getUsername(), user.getPassword(), newAddress);
        }
        return this.userRepository.save(newUser);
    }
    
    @Transactional
    public void deleteUserByUserName(String userName) {
        this.userRepository.delete(this.getUserByUserName(userName));
    }

}
