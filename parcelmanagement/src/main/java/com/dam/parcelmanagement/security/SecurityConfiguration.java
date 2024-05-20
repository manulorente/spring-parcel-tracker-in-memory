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

    @Autowired
    UserService userService;

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
                    .permitAll());
        http.headers(headers -> headers.disable());
        http.csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"));
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            User user = this.userService.getUserByUsername(username);
            UserBuilder builder = org.springframework.security.core.userdetails.User.withUsername(user.getUsername());
            builder.password("{noop}" + user.getPassword());
            builder.authorities(user.getRole().toString());
            return builder.build();
        };
    }

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

