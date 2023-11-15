package com.privacare.utilities.exception.custom.not_found;

import java.util.UUID;

public class TaskNotFoundException extends NotFound {
    public TaskNotFoundException(UUID taskId) {
        super(taskId);
    }

    @Override
    public String getMessage() {
        return super.getMessage("Task");
    }
}
