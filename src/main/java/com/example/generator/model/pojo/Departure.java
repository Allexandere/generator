package com.example.generator.model.pojo;

import com.example.generator.model.TransportType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class Departure {
    private UUID id;
    private TransportType transportType;
    @JsonFormat(pattern="yyyy-MM-dd hh:mm")
    private LocalDateTime departureDate;
    private Integer travelTime;
    private Integer price;
    private Long departureCity;
    private Long arrivalCity;
}
