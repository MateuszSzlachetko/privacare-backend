package com.privacare.model.dto.request;

import com.privacare.utilities.validator.ValidState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskEditRequestDTO {

    @NotNull
    private UUID id;

    @NotNull
    private String content;

    @NotNull
    @ValidState
    private String state;
}