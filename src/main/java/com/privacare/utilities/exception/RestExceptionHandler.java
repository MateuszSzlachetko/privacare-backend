package com.privacare.utilities.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.BindException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGenericException(
            Exception exception
    ) {
        log.warn("Exception caught: ", exception);
        ErrorDetails errorDetails = ErrorDetails.builder()
                .timestamp(LocalDateTime.now())
                .messages(List.of(exception.getMessage()))
                .build();

        return new ResponseEntity<>(
                errorDetails,
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorDetails> handleNoSuchElementException(
            NoSuchElementException exception
    ) {
        log.warn("No such element exception caught: ", exception);
        ErrorDetails errorDetails = ErrorDetails.builder()
                .timestamp(LocalDateTime.now())
                .messages(List.of(exception.getMessage()))
                .build();

        return new ResponseEntity<>(
                errorDetails,
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(
            {MethodArgumentNotValidException.class, BindException.class}
    )
    public ResponseEntity<ErrorDetails> handleValidationErrors(
            MethodArgumentNotValidException exception
    ) {
        log.warn("Validation failed: ", exception);

        ErrorDetails errorDetails = ErrorDetails.builder()
                .timestamp(LocalDateTime.now())
                .messages(exception.getBindingResult()
                        .getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .collect(Collectors.toList()))
                .build();

        return new ResponseEntity<>(
                errorDetails,
                HttpStatus.BAD_REQUEST
        );
    }
}
