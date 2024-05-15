package com.dam.parcelmanagement.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;

@Entity(name = "address")
@Data
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_seq")
    @SequenceGenerator(name="address_seq", sequenceName="address_seq", allocationSize = 1)
    private Long id;

    private String email;
    private String firstName;
    private String lastName;
    private String street;
    private String city;
    private String zipCode;
    private String country;

}
