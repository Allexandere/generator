package com.example.generator.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema
public class GenerateRequest {
    @Schema(example = "1")
    private Integer amountOfRecords;
    @Schema(example = "2")
    private Integer amountOfCities;
}
