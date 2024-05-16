package com.dam.parcelmanagement.model;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class Packet {
    
    @Enumerated(EnumType.STRING)
    private PacketType type;

    private Double weight;

    private Double height;

    private Double width;

    private Double length;

}
