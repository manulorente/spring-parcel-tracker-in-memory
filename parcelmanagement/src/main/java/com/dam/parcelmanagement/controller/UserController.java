package com.dam.parcelmanagement.controller;

import org.springframework.web.bind.annotation.RestController;

import com.dam.parcelmanagement.model.User;
import com.dam.parcelmanagement.service.UserService;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @GetMapping("")
    public List<User> getUsers() {
        log.info("Getting all users");
        return this.userService.getAllUsers();
    }

    @GetMapping("/{userName}")
    public User getUserByUserName(@PathVariable String userName) {
        log.info("Getting user by username");
        return this.userService.getUserByUserName(userName);
    }

    @PutMapping("/")
    public User updateUser(@RequestBody User userDetails) {
        log.info("Updating user");
        return this.userService.updateUser(userDetails);
    }

    @PostMapping("/")
    public User createUser(@RequestBody User userDetails) {
        log.info("Creating user");
        return this.userService.createUser(userDetails);
    }

    @DeleteMapping("/")
    public void deleteUserByUserName(@RequestBody  String userName) {
        log.info("Deleting user by username");
        this.userService.deleteUserByUserName(userName);
    }
    
}
