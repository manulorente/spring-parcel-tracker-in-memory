package com.dam.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Entity
@Table(name = "users_customer")
@Data
@EqualsAndHashCode(callSuper = true)
public class Customer extends User{

    public Customer(String username, String password, AddressInfo userInfo) {
        super(username, password, Roles.ROLE_CUSTOMER, userInfo);
    }

    @OneToMany(mappedBy = "id")
    private List<Packet> packets;
    
}
