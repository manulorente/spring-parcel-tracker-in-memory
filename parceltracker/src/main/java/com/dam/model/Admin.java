package com.dam.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "users_admin")
@Data
@EqualsAndHashCode(callSuper = true)
public class Admin extends User{
    
    public Admin(String username, String password, AddressInfo userInfo) {
        super(username, password, Roles.ROLE_ADMIN, userInfo);
    }

    @OneToMany(mappedBy = "id")
    private List<Report> weeklyReports;
    
}
