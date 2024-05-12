package com.dam.model;

import java.util.List;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter @Getter @ToString
public class Admin extends User{

    public Admin() {
        super();
        this.setRol(Roles.ROLE_ADMIN);
    }

    public Admin(String username, String password, UserInfo userInfo) {
        super(username, password, Roles.ROLE_ADMIN, userInfo);
    }

    private List<Report> weeklyReports;
    
}
