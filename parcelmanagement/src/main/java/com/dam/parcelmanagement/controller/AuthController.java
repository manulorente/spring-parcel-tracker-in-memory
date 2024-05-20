package com.dam.parcelmanagement.controller;

import com.dam.parcelmanagement.model.Customer;
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

    @GetMapping("/logout")
    public String logout() {
        log.info("User logged out");
        return "redirect:/login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Principal principal, Model model) {
        log.info("Register page accessed");
        model.addAttribute("user", new Customer());
        return "redirect:/users/create";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute Customer userDetails, Model model) {
        log.info("Registering user");
        if (this.userService.getUserByUsername(userDetails.getUsername()) != null) {
            model.addAttribute("errorMessage", "User already exists");
            return "user-new";
        }
        this.userService.createUser(userDetails);
        return "redirect:/dashboard";
    }

}
