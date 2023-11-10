package com.privacare.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MultipleSlotsRequestDTO {

    @NotNull
    private UUID doctorId;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @NotNull
    private LocalTime startTime;

    @NotNull
    private LocalTime endTime;

    private Integer slotsInterval = 60;

    private Set<DayOfWeek> selectedDays;
}
