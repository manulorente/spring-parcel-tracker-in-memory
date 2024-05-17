package com.dam.parcelmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dam.parcelmanagement.model.Delivery;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    
}
