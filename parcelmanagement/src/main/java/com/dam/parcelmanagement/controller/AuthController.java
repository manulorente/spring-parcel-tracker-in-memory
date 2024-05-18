package com.dam.parcelmanagement.controller;

import com.dam.parcelmanagement.model.Customer;
import com.dam.parcelmanagement.model.User;
import com.dam.parcelmanagement.service.UserService;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class AuthController {

    private final Logger log = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String showLoginForm(@RequestParam(value = "error", required = false) String error, Model model) {
        log.info("Login page accessed");
        if (error != null) {
            model.addAttribute("errorMessage", "Invalid username or password");
        }
        model.addAttribute("user", new Customer());
        return "login";
    }

    @PostMapping("/login")
    public String loginSuccess (Principal principal, Model model) {
        log.info("User logged in");
        User user = userService.getUserByUserName(principal.getName());
        String redirectUrl = String.format("/users/%s/view", user.getUsername());
        return "redirect:" + redirectUrl;
    }
    
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        log.info("Register page accessed");
        model.addAttribute("user", new Customer());
        return "register";
    }

    @PostMapping("/register")
    public String registerSuccess(@ModelAttribute Customer userDetails, Principal principal, Model model) {
        log.info("Registering user");
        this.userService.createUser(userDetails);
        User user = userService.getUserByUserName(principal.getName());
        String redirectUrl = String.format("/users/%s/view", user.getUsername());
        return "redirect:" + redirectUrl;
    }

}
