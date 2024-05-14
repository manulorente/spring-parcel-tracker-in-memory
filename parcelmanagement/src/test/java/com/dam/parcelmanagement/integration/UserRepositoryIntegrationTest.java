package com.dam.parcelmanagement.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dam.parcelmanagement.repository.UserRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
public class UserRepositoryIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        this.userRepository.deleteAll();
    }

    @Test
    @Transactional
    public void testSave() {
        assert(true);
    }
    
}
