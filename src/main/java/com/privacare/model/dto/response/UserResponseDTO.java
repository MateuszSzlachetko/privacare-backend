package com.privacare.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {
    private UUID id;

    private String authId;

    private LocalDateTime createdAt;

    private String name;

    private String surname;

    private String pesel;

    private String phoneNumber;
}
