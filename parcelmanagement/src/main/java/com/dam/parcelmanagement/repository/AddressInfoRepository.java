package com.dam.parcelmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dam.parcelmanagement.model.AddressInfo;

@Repository
public interface AddressInfoRepository extends JpaRepository<AddressInfo, Long>{
    
}
