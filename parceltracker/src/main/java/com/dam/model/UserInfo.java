package com.dam.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString @NoArgsConstructor @AllArgsConstructor
public class UserInfo {

    private String email;
    private String firstName;
    private String lastName;
    private String street;
    private String city;
    private String zipCode;
    private String country;

}
