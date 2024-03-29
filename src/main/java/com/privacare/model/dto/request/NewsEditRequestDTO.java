package com.privacare.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewsEditRequestDTO {

    @NotNull
    private UUID id;

    @Size(max = 64)
    @NotBlank(message = "Title can not be empty")
    private String title;

    @NotBlank(message = "Content can not be empty")
    private String content;
}
