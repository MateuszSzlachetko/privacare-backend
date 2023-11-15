package com.privacare.utilities.exception.custom.not_found;

import java.util.UUID;

public class AppointmentNotFoundException extends NotFound {
    public AppointmentNotFoundException(UUID appointmentId) {
        super(appointmentId);
    }

    @Override
    public String getMessage() {
        return super.getMessage("Appointment");
    }
}