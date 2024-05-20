package com.dam.parcelmanagement.controller;

import java.security.Principal;
import java.util.Collection;
import java.util.List;

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

    // Inyecta una instancia de UserService para gestionar los usuarios
    @Autowired
    private UserService userService;

    // Inyecta una instancia de DeliveryService para gestionar las entregas
    @Autowired
    private DeliveryService deliveryService;

    // Verifica si el usuario autenticado tiene el rol de ADMIN
    private boolean isUserAdmin(Principal principal) {
        if (principal instanceof Authentication) {
            Authentication authentication = (Authentication) principal;
            Collection<String> roles = authentication.getAuthorities().stream()
                    .map(r -> r.getAuthority())
                    .toList();
            // Devuelve verdadero si el usuario tiene el rol de ADMIN
            return roles.contains(UserRole.ROLE_ADMIN.name());
        }
        return false;
    }

    // Muestra la página principal (index)
    @GetMapping(value = {"/", "/index"})
    public String index(Model model) {
        return "index";
    }

    // Muestra el dashboard para usuarios autenticados con rol ADMIN o CUSTOMER
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_CUSTOMER')")
    @GetMapping({"/dashboard"})
    public String viewDashboard(Principal principal, Model model) {
        // Obtiene el nombre de usuario del usuario autenticado
        String username = this.userService.getUserByUsername(principal.getName()).getUsername();
        // Obtiene las entregas asociadas al usuario
        List<Delivery> deliveries = this.deliveryService.getDeliveriesByUsername(username);
        model.addAttribute("username", username);
        model.addAttribute("deliveries", deliveries);

        // Si el usuario es administrador, añade la lista de todos los usuarios al modelo
        if (isUserAdmin(principal)) {
            List<User> users = userService.getAllUsers();
            model.addAttribute("useradmin", true);
            model.addAttribute("users", users);
            return "dashboard";
        } else {
            // Si el usuario no es administrador, añade solo la información del usuario al modelo
            User user = userService.getUserByUsername(username);
            model.addAttribute("users", user);
            return "dashboard";
        }
    }
}
