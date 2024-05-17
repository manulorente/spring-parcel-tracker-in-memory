package com.dam.parcelmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dam.parcelmanagement.model.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    
}
