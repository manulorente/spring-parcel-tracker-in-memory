package com.dam.parcelmanagement.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "reports")
@Data
@NoArgsConstructor
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date date;

    private Long numberOfDeliveries;

    private Double averageRating;

    private Double totalIncome;

    public Report(Long numberOfDeliveries, Double averageRating, Double totalIncome) {
        this.numberOfDeliveries = numberOfDeliveries;
        this.averageRating = averageRating;
        this.totalIncome = totalIncome;
        this.date = new Date();
    }
    
}
