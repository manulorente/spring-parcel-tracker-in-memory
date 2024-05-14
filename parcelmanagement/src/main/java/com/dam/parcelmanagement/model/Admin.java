package com.dam.parcelmanagement.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Admin extends User{
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_info_id", referencedColumnName = "id")
    private AddressInfo addressInfo;

    public Admin(String username, String password, AddressInfo addressInfo) {
        super(username, password);
        this.setRole(UserRole.ADMIN);
        this.addressInfo = addressInfo;
    }

}
