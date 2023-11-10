package com.privacare.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SlotRequestDTO {

    @NotNull
    private UUID doctorId;

    @NotNull
    private LocalDateTime startsAt;
}