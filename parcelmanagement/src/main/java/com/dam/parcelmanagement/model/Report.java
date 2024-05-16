package com.dam.parcelmanagement.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity(name = "report")
@Data
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) 
    private Long id;

    private Long numberOfDeliveries;

    private Double averageRating;

    private Double totalIncome;
    
}
