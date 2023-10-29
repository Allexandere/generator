package com.example.generator.model.dto;

import com.example.generator.model.TransportType;
import lombok.Builder;
import lombok.Data;

import java.time.Duration;
import java.time.LocalDateTime;

@Data
@Builder
public class Departure {
    private TransportType transportType;
    private LocalDateTime departureDate;
    private Duration travelTime;
    private String departureCityName;
}
