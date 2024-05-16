package com.dam.parcelmanagement.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("ADMIN")
public class Admin extends User{

    public Admin() {
        super(null, null, null); 
        this.setRole(UserRole.ADMIN);
    }

    public Admin(String username, String password, Address address) {
        super(username, password, address);
        this.setRole(UserRole.ADMIN);
    }

}
