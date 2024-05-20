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

    @GetMapping("/create")
    public String createUser(Principal principal, Model model) {
        log.info("Showing user creation form");
        model.addAttribute("user", new Customer());
        model.addAttribute("isUserAdmin", isUserAdmin(principal));
        return "user-new";
    }

    @PostMapping("/create")
    public String createUser(Principal principal, @ModelAttribute Customer userDetails, Model model) {
        log.info("Creating user");
        Boolean usernameExists = this.userService.existsUserByUsername(userDetails.getUsername());
        if (usernameExists) {
            model.addAttribute("errorMessage", "El nombre de usuario ya existe. Por favor, elija otro.");
            model.addAttribute("user", new Customer());
            model.addAttribute("isUserAdmin", isUserAdmin(principal));
            return "user-new";
        }
        this.userService.createUser(userDetails);
        return "redirect:/dashboard";
    }    

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_CUSTOMER')")
    @PostMapping("/{username}/delete")
    public String deleteUser(@PathVariable String username) {
        log.info("Deleting user by username");
        this.userService.deleteUser(username);
        return "redirect:/dashboard";
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_CUSTOMER')")
    @GetMapping("/{username}/edit")
    public String updateUser(@PathVariable String username, Model model) {
        log.info("Showing user edit form");
        User user = userService.getUserByUsername(username);
        model.addAttribute("user", user);
        return "user-edit";
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_CUSTOMER')")
    @PostMapping("/{username}/edit")
    public String updateUser(@PathVariable String username, Principal principal, @ModelAttribute Customer userDetails, Model model) {
        log.info("Updating user");
        User user = this.userService.getUserByUsername(username);
        user.setAddress(userDetails.getAddress());
        user.setPassword(userDetails.getPassword());
        this.userService.updateUser(userDetails);
        return "redirect:/dashboard";
    }
    
}
