package com.example.generator.exception.throwables;

import com.example.generator.exception.util.ApiErrorCode;

@ApiErrorCode(400)
public class GenerateRequestAmountIsInvalidException extends GeneratorApiException {
    public GenerateRequestAmountIsInvalidException(String message) {
        super(message);
    }
}
