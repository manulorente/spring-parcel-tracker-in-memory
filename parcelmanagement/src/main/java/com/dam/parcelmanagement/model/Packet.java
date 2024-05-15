package com.dam.parcelmanagement.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;

@Entity(name = "packet")
@Data
public class Packet {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "packet_seq")
    @SequenceGenerator(name="packet_seq", sequenceName="packet_seq", allocationSize = 1)
    private Long id;

    @Enumerated(EnumType.STRING)
    private PacketType type;

    private Double weight;

    private Double height;

    private Double width;

    private Double length;

}
