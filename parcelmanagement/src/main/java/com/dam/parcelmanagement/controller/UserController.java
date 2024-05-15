package com.dam.parcelmanagement.controller;

import org.springframework.web.bind.annotation.RestController;

import com.dam.parcelmanagement.model.User;
import com.dam.parcelmanagement.service.UserService;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/api")
public class UserController {

    private final Logger log = LoggerFactory.getLogger(LandingController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<User> getUsers() {
        log.info("Getting all users");
        return this.userService.getAllUsers();
    }
    
    
}
