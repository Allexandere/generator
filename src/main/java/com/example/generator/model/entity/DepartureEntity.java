package com.example.generator.model.entity;

import com.example.generator.model.TransportType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "departures", schema = "routes")
@NoArgsConstructor
public class DepartureEntity extends ComparableEntity {
    @Id
    @GeneratedValue
    @Getter
    private UUID id;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "transport_type")
    private TransportType transportType;
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime departureDate;
    @Column(name = "travel_time")
    private Duration travelTime;
    @ManyToOne
    @JoinColumn(name = "city_id")
    private CityEntity city;

    public DepartureEntity(TransportType transportType,
                           LocalDateTime departureDate,
                           Duration travelTime,
                           CityEntity city) {
        this.transportType = transportType;
        this.departureDate = departureDate;
        this.travelTime = travelTime;
        this.city = city;
    }
}
