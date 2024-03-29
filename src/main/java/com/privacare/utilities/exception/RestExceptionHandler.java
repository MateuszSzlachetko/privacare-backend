package com.privacare.utilities.exception;


import com.privacare.utilities.exception.custom.SlotAlreadyReservedException;
import com.privacare.utilities.exception.custom.SlotHasAppointmentConnectedException;
import com.privacare.utilities.exception.custom.UnauthorizedAccess;
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
            Exception e
    ) {
        log.warn("Exception caught: ", e);
        ErrorDetails errorDetails = ErrorDetails.createErrorDetails(e.getMessage());
        return ResponseEntity.internalServerError().body(errorDetails);
    }

    @ExceptionHandler(
            {MethodArgumentNotValidException.class, BindException.class}
    )
    public ResponseEntity<ErrorDetails> handleValidationErrors(MethodArgumentNotValidException exception) {
        log.warn("Validation failed: ", exception);

        ErrorDetails errorDetails = ErrorDetails.builder()
                .timestamp(LocalDateTime.now())
                .messages(exception.getBindingResult()
                        .getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .collect(Collectors.toList()))
                .build();

        return ResponseEntity.badRequest().body(errorDetails);
    }

    @ExceptionHandler({
            SlotAlreadyReservedException.class,
            SlotHasAppointmentConnectedException.class,
            ArithmeticException.class})
    public ResponseEntity<ErrorDetails> handleSlotExceptions(Exception e) {
        ErrorDetails errorDetails = ErrorDetails.createErrorDetails(e.getMessage());
        if (e instanceof ArithmeticException)
            errorDetails.setMessages(List.of("The slot interval must be a positive integer not " + e.getMessage()));

        return ResponseEntity.badRequest().body(errorDetails);
    }

    @ExceptionHandler({NoSuchElementException.class})
    public ResponseEntity<ErrorDetails> handleNotFoundExceptions(Exception e) {
        log.warn("No such element exception caught: ", e);
        ErrorDetails errorDetails = ErrorDetails.createErrorDetails(e.getMessage());
        return ResponseEntity.badRequest().body(errorDetails);
    }

    @ExceptionHandler({UnauthorizedAccess.class})
    public ResponseEntity<ErrorDetails> handleUnauthorizedAccessExceptions(Exception e) {
        log.warn(e.getMessage());
        ErrorDetails errorDetails = ErrorDetails.createErrorDetails(e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDetails);
    }
}
