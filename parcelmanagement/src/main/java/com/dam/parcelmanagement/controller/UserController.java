package com.dam.parcelmanagement.controller;

import com.dam.parcelmanagement.model.Customer;
import com.dam.parcelmanagement.model.User;
import com.dam.parcelmanagement.model.UserRole;
import com.dam.parcelmanagement.service.UserService;

import java.security.Principal;
import java.util.Collection;

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

    // Inyecta una instancia de UserService para gestionar los usuarios
    @Autowired
    private UserService userService;

    // Verifica si el usuario autenticado tiene el rol de ADMIN
    private boolean isUserAdmin(Principal principal) {
        if (principal instanceof Authentication) {
            Authentication authentication = (Authentication) principal;
            Collection<String> roles = authentication.getAuthorities().stream()
                    .map(r -> r.getAuthority())
                    .toList();
            // Retorna verdadero si el usuario tiene el rol de ADMIN
            return roles.contains(UserRole.ROLE_ADMIN.name());
        }
        return false;
    }

    // Muestra el formulario para crear un nuevo usuario
    @GetMapping("/create")
    public String createUser(Principal principal, Model model) {
        model.addAttribute("user", new Customer());
        model.addAttribute("isUserAdmin", isUserAdmin(principal));
        return "user-new";
    }

    // Procesa la creación de un nuevo usuario
    @PostMapping("/create")
    public String createUser(Principal principal, @ModelAttribute Customer userDetails, Model model) {
        Boolean usernameExists = this.userService.existsUserByUsername(userDetails.getUsername());
        if (usernameExists) {
            // Si el nombre de usuario ya existe, muestra un mensaje de error y vuelve a mostrar el formulario
            model.addAttribute("errorMessage", "El nombre de usuario ya existe. Por favor, elija otro.");
            model.addAttribute("user", new Customer());
            model.addAttribute("isUserAdmin", isUserAdmin(principal));
            return "user-new";
        }
        // Si el nombre de usuario no existe, crea un nuevo usuario
        this.userService.createUser(userDetails);
        return "redirect:/dashboard";
    }

    // Permite eliminar un usuario por su nombre de usuario
    // Solo accesible para usuarios con roles 'ROLE_ADMIN' o 'ROLE_CUSTOMER'
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_CUSTOMER')")
    @PostMapping("/{username}/delete")
    public String deleteUser(@PathVariable String username) {
        this.userService.deleteUser(username);
        return "redirect:/dashboard";
    }

    // Muestra el formulario para editar un usuario por su nombre de usuario
    // Solo accesible para usuarios con roles 'ROLE_ADMIN' o 'ROLE_CUSTOMER'
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_CUSTOMER')")
    @GetMapping("/{username}/edit")
    public String updateUser(@PathVariable String username, Model model) {
        User user = userService.getUserByUsername(username);
        model.addAttribute("user", user);
        return "user-edit";
    }

    // Procesa la actualización de un usuario
    // Solo accesible para usuarios con roles 'ROLE_ADMIN' o 'ROLE_CUSTOMER'
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_CUSTOMER')")
    @PostMapping("/{username}/edit")
    public String updateUser(@PathVariable String username, Principal principal, @ModelAttribute Customer userDetails, Model model) {
        User user = this.userService.getUserByUsername(username);
        user.setAddress(userDetails.getAddress());
        user.setPassword(userDetails.getPassword());
        this.userService.updateUser(userDetails);
        return "redirect:/dashboard";
    }
}
