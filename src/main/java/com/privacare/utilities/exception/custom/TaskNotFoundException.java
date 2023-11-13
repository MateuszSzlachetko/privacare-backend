package com.privacare.utilities.exception.custom;


import java.util.UUID;

public class TaskNotFoundException extends NotFound {
    public TaskNotFoundException(UUID itemId) {
        super(itemId);
    }

    public String getMessage() {
        return super.getMessage("Task");
    }
}
