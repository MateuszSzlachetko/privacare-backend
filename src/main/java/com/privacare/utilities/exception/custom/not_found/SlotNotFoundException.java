package com.privacare.utilities.exception.custom.not_found;

import java.util.UUID;

public class SlotNotFoundException extends NotFound {
    public SlotNotFoundException(UUID slotId) {
        super(slotId);
    }

    @Override
    public String getMessage() {
        return super.getMessage("Slot");
    }
}
