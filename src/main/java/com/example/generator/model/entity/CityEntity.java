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

    public CityEntity(String cityName) {
        this.cityName = cityName;
    }
}
