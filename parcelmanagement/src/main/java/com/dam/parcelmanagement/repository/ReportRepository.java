package com.dam.parcelmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dam.parcelmanagement.model.Report;

public interface ReportRepository extends JpaRepository<Report, Long> {
    
}
