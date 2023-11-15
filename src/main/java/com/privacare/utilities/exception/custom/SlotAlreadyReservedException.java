package com.privacare.utilities.exception.custom;

import java.util.UUID;

public class SlotAlreadyReservedException extends RuntimeException {
    public SlotAlreadyReservedException(UUID slotId) {
        super("Slot with id: " + slotId + " is already reserved");
    }
}
