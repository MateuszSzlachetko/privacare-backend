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
public class TaskResponseDTO {

    private UUID id;

    private UUID creatorId;

    private LocalDateTime createdAt;

    private String content;

    private Integer categoryId;

    private String state;
}
