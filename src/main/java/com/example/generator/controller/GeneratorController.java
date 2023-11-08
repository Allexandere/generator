package com.example.generator.controller;

import com.example.generator.model.dto.GenerateRequest;
import com.example.generator.service.GeneratorService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1")
@RequiredArgsConstructor
public class GeneratorController {

    private final GeneratorService generatorService;

    @PostMapping(path = "/generate")
    @ApiOperation("Generate records in table of departures and cities")
    @ApiResponse(code = 400, message = "Amount of records or cities value is invalid (must be positive, not zero, more than 1 for cities)")
    public void generate(@RequestBody GenerateRequest generateRequest) {
        generatorService.generateRecords(generateRequest);
    }

}
