package com.example.generator.validator;

import com.example.generator.exception.throwables.GenerateRequestAmountIsInvalidException;
import com.example.generator.model.dto.GenerateRequest;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
public class GeneratorRequestValidator {

    private final static String AMOUNT_OF_RECORDS_IS_ZERO_OR_NEGATIVE = "Amount of records is zero or negative";
    private final static String AMOUNT_OF_CITIES_IS_ZERO_OR_NEGATIVE = "Amount of cities is less than 2";

    @SneakyThrows
    public void validate(GenerateRequest generateRequest) {
        if (generateRequest.getAmountOfRecords() < 1) {
            throw new GenerateRequestAmountIsInvalidException(AMOUNT_OF_RECORDS_IS_ZERO_OR_NEGATIVE);
        }
        if (generateRequest.getAmountOfCities() < 2) {
            throw new GenerateRequestAmountIsInvalidException(AMOUNT_OF_CITIES_IS_ZERO_OR_NEGATIVE);
        }
    }
}
