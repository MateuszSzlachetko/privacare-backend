package com.privacare.utilities.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class ErrorDetails {

    private LocalDateTime timestamp;

    private List<String> messages;

    public static ErrorDetails createErrorDetails(String message){
        return ErrorDetails.builder()
                .timestamp(LocalDateTime.now())
                .messages(List.of(message))
                .build();
    }
}

