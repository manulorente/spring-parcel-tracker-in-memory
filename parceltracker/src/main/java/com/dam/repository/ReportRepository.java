package com.dam.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dam.model.Report;

public interface ReportRepository extends JpaRepository<Report, Long> {

}
