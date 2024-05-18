package com.dam.parcelmanagement.model;

import java.util.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "invoices")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Double price;

    private Double tax;

    private Date date;

    private Date dueDate;

    private Double total;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_info_id", referencedColumnName = "id")
    private Address customerInfo;

    private String serviceInfo;
    
}
