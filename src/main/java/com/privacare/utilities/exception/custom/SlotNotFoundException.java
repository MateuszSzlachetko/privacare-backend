package com.privacare.utilities.exception.custom;


import java.util.UUID;

public class SlotNotFoundException extends NotFound {
    public SlotNotFoundException(UUID itemId) {
        super(itemId);
    }

    @Override
    public String getMessage() {
        return super.getMessage("Slot");
    }
}
