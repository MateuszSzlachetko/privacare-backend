package com.privacare.model.dto.request;

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
public class AppointmentRequestDTO {

    @NotNull
    private UUID creatorId;

    @NotNull
    private UUID patientId;

    @NotNull
    private UUID slotId;
}