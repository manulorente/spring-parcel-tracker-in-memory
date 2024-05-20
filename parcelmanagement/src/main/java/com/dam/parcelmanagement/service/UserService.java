package com.dam.parcelmanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dam.parcelmanagement.model.Admin;
import com.dam.parcelmanagement.model.Customer;
import com.dam.parcelmanagement.model.User;
import com.dam.parcelmanagement.model.UserRole;
import com.dam.parcelmanagement.repository.UserRepository;

import jakarta.transaction.Transactional;

import com.dam.parcelmanagement.exception.ResourceNotFoundException;

@Service
public class UserService {

    // Inyecta una instancia de UserRepository para acceder a las operaciones CRUD
    @Autowired
    private UserRepository userRepository;

    // Obtiene todos los usuarios
    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    // Verifica si un usuario existe por su nombre de usuario
    public Boolean existsUserByUsername(String username) {
        return this.userRepository.existsByUsername(username);
    }

    // Obtiene un usuario por su ID
    public User getUserById(Long id) {
        return this.userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    // Obtiene un usuario por su nombre de usuario
    public User getUserByUsername(String username) {
        return this.userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
    }

    // Actualiza un usuario existente
    @Transactional
    public User updateUser(User userDetails) {
        User newUser = this.getUserByUsername(userDetails.getUsername());
        if (userDetails.getPassword() != null) {
            newUser.setPassword(userDetails.getPassword());
        }
        if (userDetails.getRole() != null) {
            newUser.setRole(userDetails.getRole());
        }
        if (userDetails.getAddress() != null) {
            newUser.setAddress(userDetails.getAddress());
        }
        return this.userRepository.save(newUser);
    }

    // Crea un nuevo usuario
    @Transactional
    public User createUser(User user) {
        if (user.getId() != null && this.userRepository.existsById(user.getId())) {
            throw new ResourceNotFoundException("User already exists with id: " + user.getId());
        }
        if (this.userRepository.existsByUsername(user.getUsername())) {
            throw new ResourceNotFoundException("User already exists with username: " + user.getUsername());
        }
        User newUser;
        if (user.getRole().equals(UserRole.ROLE_ADMIN)){
            newUser = new Admin(user.getUsername(), user.getPassword(), user.getAddress());
        } else {
            newUser = new Customer(user.getUsername(), user.getPassword(), user.getAddress());
        }
        return this.userRepository.save(newUser);
    }

    // Elimina un usuario por su nombre de usuario
    @Transactional
    public void deleteUser(String username) {
        this.userRepository.delete(this.getUserByUsername(username));
    }
}
