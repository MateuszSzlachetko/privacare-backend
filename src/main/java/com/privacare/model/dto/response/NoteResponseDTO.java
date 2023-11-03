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
public class NoteResponseDTO {
    private UUID id;

    private UUID creatorId;

    private UUID patientId;

    private LocalDateTime createdAt;

    private String content;
}
