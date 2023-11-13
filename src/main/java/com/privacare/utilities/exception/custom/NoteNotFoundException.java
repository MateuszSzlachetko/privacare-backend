package com.privacare.utilities.exception.custom;


import java.util.UUID;

public class NoteNotFoundException extends NotFound {
    public NoteNotFoundException(UUID itemId) {
        super(itemId);
    }

    public String getMessage() {
        return super.getMessage("Note");
    }
}
