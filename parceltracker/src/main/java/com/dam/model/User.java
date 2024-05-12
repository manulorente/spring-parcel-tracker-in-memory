package com.dam.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Table(name = "users")
@Getter @Setter @ToString @NoArgsConstructor
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String username;
    @Column(unique = true)
    private String password;

    @Enumerated(EnumType.STRING)
    private Roles rol;

    @Embedded
    private UserInfo userInfo;

    public User(String username, String password, Roles rol, UserInfo userInfo) {
        this.username = username;
        this.password = password;
        this.rol = rol;
        this.userInfo = userInfo;
    }
    
}
