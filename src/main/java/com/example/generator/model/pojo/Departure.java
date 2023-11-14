package com.example.generator.model.pojo;

import com.example.generator.model.TransportType;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class Departure {
    private UUID id;
    private TransportType transportType;
    private LocalDateTime departureDate;
    private Integer travelTime;
    private Integer price;
    private Long departureCity;
    private Long arrivalCity;
}
