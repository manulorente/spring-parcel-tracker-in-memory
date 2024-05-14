package com.dam.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dam.model.Packet;

public interface PacketRepository extends JpaRepository<Packet, Long> {
    
}
