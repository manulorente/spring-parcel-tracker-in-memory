package com.dam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dam.model.Customer;
import com.dam.model.User;
import com.dam.model.AddressInfo;
import com.dam.repository.UserRepository;

import jakarta.annotation.PostConstruct;

@Component
public class SampleDataLoader {

    private static final Logger log = LoggerFactory.getLogger(SampleDataLoader.class);

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void loadSampleData() {
        log.info("Loading sample data...");

        // Create a new user
        Customer customer = new Customer("customer", "password", new AddressInfo());

        // Save the user
        User savedUser = this.userRepository.save(customer);

        log.info("Sample data loaded successfully!");
    }
    
}
