package com.example.generator.model.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class DepartureGraph {
    List<City> cities;
    List<Departure> departures;
}
