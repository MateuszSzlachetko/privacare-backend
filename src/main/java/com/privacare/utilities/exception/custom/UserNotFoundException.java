package com.privacare.utilities.exception.custom;


import java.util.UUID;

public class UserNotFoundException extends NotFound {
    public UserNotFoundException(UUID itemId) {
        super(itemId);
    }

    public String getMessage() {
        return super.getMessage("User");
    }
}
