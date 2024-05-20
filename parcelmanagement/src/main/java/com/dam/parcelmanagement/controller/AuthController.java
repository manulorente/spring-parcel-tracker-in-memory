package com.dam.parcelmanagement.controller;

import com.dam.parcelmanagement.model.Customer;
import com.dam.parcelmanagement.service.UserService;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    // Inyecta una instancia de UserService para utilizar sus métodos
    @Autowired
    private UserService userService;

    // Muestra el formulario de inicio de sesión
    // Si hay un error (por ejemplo, nombre de usuario o contraseña incorrectos), añade un mensaje de error al modelo
    @GetMapping("/login")
    public String showLoginForm(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", "Nombre de usuario o contraseña incorrectos.");
        }
        model.addAttribute("user", new Customer());
        return "login";
    }

    // Gestiona la solicitud de cierre de sesión y redirige al formulario de inicio de sesión
    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login";
    }

    // Muestra el formulario de registro
    // Redirige a la página de creación de usuarios
    @GetMapping("/register")
    public String showRegistrationForm(Principal principal, Model model) {
        model.addAttribute("user", new Customer());
        return "redirect:/users/create";
    }

    // Gestiona la solicitud de registro de un nuevo usuario
    // Verifica si el nombre de usuario ya está en uso y, si no, crea un nuevo usuario
    @PostMapping("/register")
    public String registerUser(@ModelAttribute Customer userDetails, Model model) {
        if (this.userService.getUserByUsername(userDetails.getUsername()) != null) {
            model.addAttribute("errorMessage", "El nombre de usuario ya está en uso.");
            return "user-new";
        }
        this.userService.createUser(userDetails);
        return "redirect:/dashboard";
    }

}
