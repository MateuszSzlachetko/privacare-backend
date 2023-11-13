package com.privacare.utilities.exception.custom;

import lombok.Getter;
import lombok.Setter;

import java.util.NoSuchElementException;
import java.util.UUID;

@Getter
@Setter
public abstract class NotFound extends NoSuchElementException {
    private UUID itemUUID;
    private Integer itemId;

    public NotFound(UUID itemId) {
        this.itemUUID = itemId;
    }

    public NotFound(Integer itemId) {
        this.itemId = itemId;
    }

    public String getMessage(String name) {
        if (itemUUID != null)
            return String.format("%s with id: %s not found", name, this.itemUUID);
        if (itemId != null)
            return String.format("%s with id: %s not found", name, this.itemId);
        return "";
    }
}
