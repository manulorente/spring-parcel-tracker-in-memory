package com.dam.parcelmanagement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dam.parcelmanagement.repository.UserRepository;

import com.dam.parcelmanagement.service.UserService;

import com.dam.parcelmanagement.model.Address;
import com.dam.parcelmanagement.model.Admin;
import com.dam.parcelmanagement.model.Customer;
import com.dam.parcelmanagement.model.User;
import com.dam.parcelmanagement.model.UserRole;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
public class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testCreateUser() {

        Address address= new Address();
        address.setEmail("test@test.com");
        address.setFirstName("John");
        address.setLastName("Doe");
        address.setStreet("123 Main St");
        address.setCity("Springfield");
        address.setZipCode("12345");
        address.setCountry("USA");

        User admin = new Admin("admin", "admin", address);

        User createdAdmin = this.userService.createUser(admin);

        User retrievedAdmin = userRepository.findById(admin.getId()).orElse(null);

        assertNotNull(retrievedAdmin);
        assertEquals(createdAdmin.getUsername(), retrievedAdmin.getUsername());
        assertEquals(createdAdmin.getPassword(), retrievedAdmin.getPassword());
        assertEquals(UserRole.ADMIN, retrievedAdmin.getRole());
        assertEquals(createdAdmin.getAddress().getEmail(), retrievedAdmin.getAddress().getEmail());
        assertEquals(createdAdmin.getAddress().getFirstName(), retrievedAdmin.getAddress().getFirstName());
        assertEquals(createdAdmin.getAddress().getLastName(), retrievedAdmin.getAddress().getLastName());
        assertEquals(createdAdmin.getAddress().getStreet(), retrievedAdmin.getAddress().getStreet());
        assertEquals(createdAdmin.getAddress().getCity(), retrievedAdmin.getAddress().getCity());
        assertEquals(createdAdmin.getAddress().getZipCode(), retrievedAdmin.getAddress().getZipCode());
       
    }

    @Test
    public void testUpdateUser() {
        Address address= new Address();
        address.setEmail("test2@test.com");
        address.setFirstName("Jane");
        address.setLastName("Doe");
        address.setStreet("123 Main St");
        address.setCity("Springfield");
        address.setZipCode("12345");
        address.setCountry("USA");

        User user = new Customer("user", "user", address);

        User createdUser = this.userService.createUser(user);

        User retrievedUser = userRepository.findById(createdUser.getId()).orElse(null);

        assertNotNull(retrievedUser);
        assertEquals(createdUser, retrievedUser);

        retrievedUser.setUsername("newUser");
        retrievedUser.setPassword("newPassword");
        retrievedUser.setRole(UserRole.ADMIN);
        retrievedUser.setAddress(new Address());

        User updatedUser = this.userService.updateUser(retrievedUser.getId(), retrievedUser);

        assertEquals(retrievedUser.getUsername(), updatedUser.getUsername());
        assertEquals(retrievedUser.getPassword(), updatedUser.getPassword());
        assertEquals(retrievedUser.getRole(), updatedUser.getRole());
        assertEquals(retrievedUser.getAddress(), updatedUser.getAddress());
    }

    @Test
    public void testDeleteUser() {
        Address address= new Address();
        address.setEmail("test2@test.com");
        address.setFirstName("Jane");
        address.setLastName("Doe");
        address.setStreet("123 Main St");
        address.setCity("Springfield");
        address.setZipCode("12345");
        address.setCountry("USA");

        User user = new Customer("user", "user", address);

        User createdUser = this.userService.createUser(user);

        User retrievedUser = userRepository.findById(createdUser
                .getId()).orElse(null);

        assertNotNull(retrievedUser);
        assertEquals(createdUser, retrievedUser);

        this.userService.deleteUser(retrievedUser);

        User deletedUser = userRepository.findById(retrievedUser.getId()).orElse(null);
        assertEquals(null, deletedUser);
    }

    @Test
    public void testGetUserById() {
        Address address= new Address();
        address.setEmail("test2@test.com");
        address.setFirstName("Jane");
        address.setLastName("Doe");
        address.setStreet("123 Main St");
        address.setCity("Springfield");
        address.setZipCode("12345");
        address.setCountry("USA");

        User user = new Customer("user", "user", address);

        User createdUser = this.userService.createUser(user);

        User retrievedUser = this.userService.getUserById(createdUser.getId());

        assertNotNull(retrievedUser);
        assertEquals(createdUser, retrievedUser);

    }
}
