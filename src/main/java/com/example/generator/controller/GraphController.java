package com.example.generator.controller;

import com.example.generator.model.pojo.City;
import com.example.generator.model.pojo.Departure;
import com.example.generator.model.pojo.DepartureGraph;
import com.example.generator.service.GraphService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1")
@RequiredArgsConstructor
public class GraphController {

    private final GraphService graphService;

    @GetMapping(path = "/cities")
    @ApiOperation("Get list of cities from the database")
    public List<City> getCities() {
        return graphService.getCities();
    }

    @GetMapping(path = "/city/id")
    @ApiOperation("Get list of cities from the database")
    public List<Long> getCityIds() {
        return graphService.getCityIds();
    }

    @GetMapping(path = "/departures")
    @ApiOperation("Get list of departures from the database")
    public List<Departure> getDepartures() {
        return graphService.getDepartures();
    }

    @GetMapping(path = "/graph")
    @ApiOperation("Get graph of cities and departures from the database")
    public DepartureGraph getGraph() {
        return graphService.getGraph();
    }

}
