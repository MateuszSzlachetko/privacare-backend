package com.privacare.utilities.exception.custom.not_found;

import java.util.UUID;

public class UserNotFoundException extends NotFound {
    public UserNotFoundException(UUID userId) {
        super(userId);
    }
    public UserNotFoundException(String userId) {
        super(userId);
    }

    @Override
    public String getMessage() {
        return super.getMessage("User");
    }
}
