package com.example.generator.exception;

import com.example.generator.exception.throwables.GeneratorApiException;
import com.example.generator.exception.util.ApiError;
import com.example.generator.exception.util.ApiErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Slf4j
@ControllerAdvice(basePackages = {"com.example.generator.controller"})
public class ApiExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ApiError> defaultErrorHandler(HttpServletRequest request, Exception exception) {
        log.error(ExceptionUtils.getStackTrace(exception));

        ApiError apiError = buildApiError(request, exception);
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        if (isCustomException(exception)) {
            int errorCode = extractErrorCode(exception);
            httpStatus = HttpStatus.valueOf(errorCode);
        }

        return new ResponseEntity<>(apiError, httpStatus);
    }

    private ApiError buildApiError(HttpServletRequest req, Exception exception) {
        return ApiError.builder()
                .timestamp(LocalDateTime.now())
                .path(req.getContextPath() + req.getServletPath())
                .message(exception.getMessage())
                .build();
    }

    private int extractErrorCode(Exception e) {
        return e.getClass().getAnnotation(ApiErrorCode.class).value();
    }

    private boolean isCustomException(Exception e) {
        return e.getClass().getSuperclass().equals(GeneratorApiException.class);
    }

}
