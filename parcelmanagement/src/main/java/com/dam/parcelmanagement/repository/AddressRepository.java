package com.dam.parcelmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dam.parcelmanagement.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {    
}
