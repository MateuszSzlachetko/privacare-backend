package com.privacare.utilities.exception.custom.not_found;

import lombok.Getter;
import lombok.Setter;

import java.util.NoSuchElementException;
import java.util.UUID;

@Getter
@Setter
public abstract class NotFound extends NoSuchElementException {
    private UUID itemUUID;
    private String itemUID;
    private Integer itemId;

    public NotFound(UUID itemId) {
        this.itemUUID = itemId;
    }

    public NotFound(Integer itemId) {
        this.itemId = itemId;
    }

    public NotFound(String itemId) {
        this.itemUID=itemId;
    }

    public String getMessage(String name) {
        if (itemUUID != null)
            return String.format("%s with id: %s not found", name, this.itemUUID);
        if (itemId != null)
            return String.format("%s with id: %s not found", name, this.itemId);
        if (itemUID != null)
            return String.format("%s with id: %s not found", name, this.itemUID);
        return "";
    }
}
