package com.example.generator.service;

import com.example.generator.model.entity.CityEntity;
import com.example.generator.model.entity.DepartureEntity;
import com.example.generator.model.pojo.City;
import com.example.generator.model.pojo.Departure;
import com.example.generator.model.pojo.DepartureGraph;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class GraphMapper extends ModelMapper {
    @PostConstruct
    public void configureMapper() {
        PropertyMap<DepartureEntity, Departure> cityEntitiesMappings = new PropertyMap<>(DepartureEntity.class, Departure.class) {
            @Override
            protected void configure() {
                map().setDepartureCity(source.getDepartureCity().getId());
                map().setArrivalCity(source.getArrivalCity().getId());
            }
        };
        this.addMappings(cityEntitiesMappings);
    }

    public DepartureGraph mapToGraph(List<CityEntity> savedCities, List<DepartureEntity> savedDepartures) {
        List<City> cities = mapCities(savedCities);
        List<Departure> departures = mapDepartures(savedDepartures);
        return new DepartureGraph(cities, departures);
    }

    private List<Departure> mapDepartures(List<DepartureEntity> savedDepartures) {
        return savedDepartures.stream().map(departureEntity -> this.map(departureEntity, Departure.class)).toList();
    }

    private List<City> mapCities(List<CityEntity> savedCities) {
        return savedCities.stream().map(cityEntity -> this.map(cityEntity, City.class)).toList();
    }
}
