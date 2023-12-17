package com.example.generator.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "cities", schema = "routes")
@NoArgsConstructor
@Getter
public class CityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "city_name")
    private String cityName;

    @Column(name = "timezone")
    private String timeZone;

    public CityEntity(String cityName, String timeZone) {
        this.cityName = cityName;
        this.timeZone = timeZone;
    }
}
