package com.dam.parcelmanagement.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Packet {
    
    @Enumerated(EnumType.STRING)
    private PacketType PacketType;

    private Double packetWeight;

    private Double packetHeight;

    private Double packetWidth;

    private Double packetLength;

}
