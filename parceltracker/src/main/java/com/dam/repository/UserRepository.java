package com.dam.repository;

import org.springframework.data.jpa.repository.JpaRepository; // Add this import statement

import com.dam.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    
}
