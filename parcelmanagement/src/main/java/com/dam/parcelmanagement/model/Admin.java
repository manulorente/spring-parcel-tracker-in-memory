package com.dam.parcelmanagement.model;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Admin extends User{

    public Admin(String username, String password, Address address) {
        super(username, password, address);
        this.setRole(UserRole.ADMIN);
    }

}
