package com.privacare.utilities.exception.custom;


import java.util.UUID;

public class NewsNotFoundException extends NotFound {
    public NewsNotFoundException(UUID itemId) {
        super(itemId);
    }

    @Override
    public String getMessage() {
        return super.getMessage("News");
    }
}
