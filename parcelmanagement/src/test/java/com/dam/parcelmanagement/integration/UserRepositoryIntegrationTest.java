package com.dam.parcelmanagement.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dam.parcelmanagement.model.Address;
import com.dam.parcelmanagement.model.Admin;
import com.dam.parcelmanagement.model.Customer;
import com.dam.parcelmanagement.model.User;

import com.dam.parcelmanagement.repository.UserRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
public class UserRepositoryIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSaveAdmin() {
        Address address= new Address();
        address.setEmail("test@test.com");
        address.setFirstName("John");
        address.setLastName("Doe");
        address.setStreet("123 Main St");
        address.setCity("Springfield");
        address.setZipCode("12345");
        address.setCountry("USA");

        User admin = new Admin("admin", "admin", address);

        User savedAdmin = this.userRepository.save(admin);

        User retrievedAdmin = userRepository.findById(savedAdmin.getId()).orElse(null);
        assertNotNull(retrievedAdmin);

        assertEquals(savedAdmin.getUsername(), retrievedAdmin.getUsername());
        assertEquals(savedAdmin.getPassword(), retrievedAdmin.getPassword());
        assertEquals(savedAdmin.getAddress().getEmail(), retrievedAdmin.getAddress().getEmail());
        assertEquals(savedAdmin.getAddress().getFirstName(), retrievedAdmin.getAddress().getFirstName());
        assertEquals(savedAdmin.getAddress().getLastName(), retrievedAdmin.getAddress().getLastName());
        assertEquals(savedAdmin.getAddress().getStreet(), retrievedAdmin.getAddress().getStreet());
        assertEquals(savedAdmin.getAddress().getCity(), retrievedAdmin.getAddress().getCity());
        assertEquals(savedAdmin.getAddress().getZipCode(), retrievedAdmin.getAddress().getZipCode());

    }

    @Test
    public void testSaveCustomer() {
        User customer = new Customer("customer", "customer", new Address());

        User savedCustomer = this.userRepository.save(customer);

        User retrievedCustomer = userRepository.findById(savedCustomer.getId()).orElse(null);
        
        assertNotNull(retrievedCustomer);
        assertEquals(retrievedCustomer, savedCustomer);
    }

    @Test
    public void testFindById() {
        User user = new Admin("admin", "admin", new Address());

        User savedUser = this.userRepository.save(user);
        User retrievedUser = this.userRepository.findById(savedUser.getId()).orElse(null);

        assertNotNull(retrievedUser);
        assertEquals(savedUser, retrievedUser);
    }//

    @Test
    public void testDelete() {
        User user = new Admin("admin", "admin", new Address());

        User savedUser = this.userRepository.save(user);
        this.userRepository.delete(savedUser);
        User retrievedUser = this.userRepository.findById(savedUser.getId()).orElse(null);
        
        assertEquals(null, retrievedUser);

    }

    
}
