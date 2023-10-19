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
public class NewsResponseDTO {

    private UUID id;

    private String creatorFullName;

    private LocalDateTime createdAt;

    private String title;

    private String content;
}
