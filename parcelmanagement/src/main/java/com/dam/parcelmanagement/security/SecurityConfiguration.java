package com.dam.parcelmanagement.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import com.dam.parcelmanagement.model.User;
import com.dam.parcelmanagement.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableMethodSecurity
public class SecurityConfiguration {

    // Inyecta una instancia de UserService para gestionar los usuarios
    @Autowired
    UserService userService;

    // Configura la cadena de filtros de seguridad
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(requests -> requests
                    .requestMatchers("/h2-console/**", "/css/**", "/js/**", "/img/**").permitAll()
                    .requestMatchers("/", "/index", "/login", "/register").permitAll() 
                    .requestMatchers("/deliveries/**", "/users/**", "/invoices/**", "/reports/**", "/comments/**").permitAll()
                    .anyRequest().authenticated())
            .formLogin(login -> login
                    .loginPage("/login")
                    .defaultSuccessUrl("/dashboard", true)
                    .failureHandler(customAuthenticationFailureHandler())
                    .permitAll())
            .logout(logout -> logout
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/index")
                    .permitAll());
        http.headers(headers -> headers.disable()); // Deshabilita las cabeceras de seguridad
        http.csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**")); // Deshabilita CSRF para la consola H2
        return http.build();
    }

    // Configura el servicio de detalles del usuario
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            User user = this.userService.getUserByUsername(username);
            UserBuilder builder = org.springframework.security.core.userdetails.User.withUsername(user.getUsername());
            builder.password("{noop}" + user.getPassword()); // No encripta la contraseña
            builder.authorities(user.getRole().toString());
            return builder.build();
        };
    }

    // Maneja fallos de autenticación redirigiendo a la página de login con un mensaje de error
    @Bean
    public AuthenticationFailureHandler customAuthenticationFailureHandler() {
        return new SimpleUrlAuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                setDefaultFailureUrl("/login?error=true");
                super.onAuthenticationFailure(request, response, exception);
            }
        };
    }
}
