package com.dam.parcelmanagement.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("CUSTOMER")
public class Customer extends User{

    public Customer() {
        super(null, null, null); 
        this.setRole(UserRole.CUSTOMER);
    }

    public Customer (String username, String password, Address address) {
        super(username, password, address);
        this.setRole(UserRole.CUSTOMER);
    }

}