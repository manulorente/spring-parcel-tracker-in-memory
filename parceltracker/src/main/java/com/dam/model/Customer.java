package com.dam.model;

import java.util.List;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter @Getter @ToString
public class Customer extends User{

    public Customer() {
        super();
        this.setRol(Roles.ROLE_CUSTOMER);
    }

    public Customer(String username, String password, UserInfo userInfo) {
        super(username, password, Roles.ROLE_CUSTOMER, userInfo);
    }

    private List<Packet> packets;
    
}
