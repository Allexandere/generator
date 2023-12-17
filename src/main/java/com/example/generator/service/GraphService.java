package com.example.generator.service;

import com.example.generator.model.entity.CityEntity;
import com.example.generator.model.pojo.City;
import com.example.generator.model.pojo.Departure;
import com.example.generator.model.pojo.DepartureGraph;
import com.example.generator.repository.CitiesRepository;
import com.example.generator.repository.DeparturesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GraphService {

    private final CitiesRepository citiesRepository;
    private final DeparturesRepository departuresRepository;
    private final GraphMapper graphMapper;

    public DepartureGraph getGraph() {
        return graphMapper.mapToGraph(citiesRepository.findAll(), departuresRepository.findAll());
    }

    public List<City> getCities() {
        return graphMapper.mapCities(citiesRepository.findAll());
    }

    public List<Long> getCityIds() {
        return citiesRepository.findAll().stream()
                .map(CityEntity::getId)
                .toList();
    }

    public List<Departure> getDepartures() {
        return graphMapper.mapDepartures(departuresRepository.findAll());
    }
}
