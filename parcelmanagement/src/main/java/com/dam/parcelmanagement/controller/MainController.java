package com.dam.parcelmanagement.controller;

import java.security.Principal;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.dam.parcelmanagement.model.Delivery;
import com.dam.parcelmanagement.model.User;
import com.dam.parcelmanagement.model.UserRole;
import com.dam.parcelmanagement.service.DeliveryService;
import com.dam.parcelmanagement.service.UserService;


@Controller
public class MainController {

    private final Logger log = LoggerFactory.getLogger(MainController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private DeliveryService deliveryService;

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

    @GetMapping(value = {"/", "/index"})
    public String index(Model model) {
        log.info("Landing page accessed");
        model.addAttribute("message", "Welcome to the Parcel Management System!");
        return "index";
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_CUSTOMER')")
    @GetMapping({"/dashboard"})
    public String getUsers(Principal principal, Model model) {
        log.info("Accessing dashboard");
        String username = this.userService.getUserByUsername(principal.getName()).getUsername();
        List<Delivery> deliveries = this.deliveryService.getDeliveriesByUsername(username);
        model.addAttribute("username", username);
        model.addAttribute("deliveries", deliveries);
        if (isUserAdmin(principal)) {
            List<User> users = userService.getAllUsers();
            model.addAttribute("useradmin", true);
            model.addAttribute("users", users);
            return "dashboard";
        } else {
            User user = userService.getUserByUsername(username);
            model.addAttribute("users", user);            
            return "dashboard";
        }
    }

}
