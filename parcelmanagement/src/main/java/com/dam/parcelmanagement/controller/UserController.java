package com.dam.parcelmanagement.controller;

import com.dam.parcelmanagement.model.Customer;
import com.dam.parcelmanagement.model.User;
import com.dam.parcelmanagement.model.UserRole;
import com.dam.parcelmanagement.service.UserService;

import java.security.Principal;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/users")
public class UserController {

    private final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    private boolean isUserAdmin(Principal principal) {
        if (principal instanceof Authentication) {
            Authentication authentication = (Authentication) principal;
            Collection<String> roles = authentication.getAuthorities().stream()
                    .map(r -> r.getAuthority())
                    .toList();
            return roles.contains(UserRole.ROLE_ADMIN.name());
        }
        return false;
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/create")
    public String CreateUserForm(Model model) {
        log.info("Showing user creation form");
        model.addAttribute("user", new Customer());
        return "user-new";
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/create")
    public String createUser(@ModelAttribute Customer userDetails, Model model) {
        log.info("Creating user");
        this.userService.createUser(userDetails);
        return "redirect:/users";
    }    

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_CUSTOMER')")
    @PostMapping("/{userName}/delete")
    public String deleteUserByUserName(@PathVariable String userName) {
        log.info("Deleting user by username");
        this.userService.deleteUserByUserName(userName);
        return "redirect:/users";
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_CUSTOMER')")
    @GetMapping("/{userName}/edit")
    public String editUserForm(@PathVariable String userName, Model model) {
        log.info("Showing user edit form");
        User user = userService.getUserByUserName(userName);
        model.addAttribute("user", user);
        return "user-edit";
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_CUSTOMER')")
    @PostMapping("/{userName}/edit")
    public String updateUser(@PathVariable String userName, Principal principal, @ModelAttribute Customer userDetails, Model model) {
        log.info("Updating user");
        if (!isUserAdmin(principal)) {
            User user = userService.getUserByUserName(userName);
            user.setAddress(userDetails.getAddress());
            user.setPassword(userDetails.getPassword());
        }
        userService.updateUser(userDetails);
        return "redirect:/users";
    }
    
}
