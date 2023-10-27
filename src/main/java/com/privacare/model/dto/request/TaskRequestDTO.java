package com.privacare.model.dto.request;

import com.privacare.utilities.validator.ValidState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskRequestDTO {

    @NotNull
    private UUID creatorId;

    @NotBlank(message = "Content can not be empty")
    private String content;

    @NotNull
    private Integer categoryId;

    @NotNull
    @ValidState
    private String state;
}