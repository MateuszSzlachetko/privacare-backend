package com.privacare.utilities.exception.custom.not_found;

import java.util.UUID;

public class NoteNotFoundException extends NotFound {
    public NoteNotFoundException(UUID noteId) {
        super(noteId);
    }

    @Override
    public String getMessage() {
        return super.getMessage("Note");
    }
}
