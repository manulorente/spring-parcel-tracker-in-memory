package com.dam.parcelmanagement.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class Address {

    private String email;
    private String firstName;
    private String lastName;
    private String street;
    private String city;
    private String zipCode;
    private String country;

}
