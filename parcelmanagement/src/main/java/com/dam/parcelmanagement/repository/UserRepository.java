package com.dam.parcelmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dam.parcelmanagement.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    
}
