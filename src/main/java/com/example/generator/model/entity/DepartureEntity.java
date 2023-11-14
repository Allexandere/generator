package com.example.generator.model.entity;

import com.example.generator.model.TransportType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "departures", schema = "routes")
@NoArgsConstructor
@Getter
public class DepartureEntity extends ComparableEntity {
    @Id
    @GeneratedValue
    private UUID id;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "transport_type")
    private TransportType transportType;
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime departureDate;
    @Column(name = "travel_time")
    private Integer travelTime;
    @Column(name = "price")
    private Integer price;
    @ManyToOne
    @JoinColumn(name = "departure_city_id")
    private CityEntity departureCity;
    @ManyToOne
    @JoinColumn(name = "arrival_city_id")
    private CityEntity arrivalCity;

    public DepartureEntity(TransportType transportType,
                           LocalDateTime departureDate,
                           Integer travelTime,
                           CityEntity departureCity,
                           CityEntity arrivalCity,
                           Integer price) {
        this.transportType = transportType;
        this.departureDate = departureDate;
        this.travelTime = travelTime;
        this.departureCity = departureCity;
        this.arrivalCity = arrivalCity;
        this.price = price;
    }
}
