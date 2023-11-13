package com.privacare.utilities.exception.custom;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class SlotHasAppointmentConnectedException extends RuntimeException {
    private UUID id;
    private List<LocalDateTime> dateRange;

    public SlotHasAppointmentConnectedException(UUID id) {
        this.id = id;
    }

    public SlotHasAppointmentConnectedException(List<LocalDateTime> dateRange) {
        this.dateRange = dateRange;
    }

    @Override
    public String getMessage() {
        if (id != null)
            return "Slot with id: " + this.id + " has an appointment connected," +
                    " consider deleting the appointment first";
        else
            return "Slots within the date range: " + this.dateRange.get(0) + " - " + this.dateRange.get(1)
                    + " have some appointments connected, consider deleting the appointments first";
    }
}
