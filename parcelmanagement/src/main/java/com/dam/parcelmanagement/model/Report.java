package com.dam.parcelmanagement.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;

@Entity(name = "report")
@Data
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "report_seq")
    @SequenceGenerator(name="report_seq", sequenceName="report_seq", allocationSize = 1)    
    private Long id;

    private Long numberOfDeliveries;

    private Double averageRating;

    private Double totalIncome;
    
}
