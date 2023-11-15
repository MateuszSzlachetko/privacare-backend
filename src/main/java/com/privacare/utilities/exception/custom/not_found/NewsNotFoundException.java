package com.privacare.utilities.exception.custom.not_found;

import java.util.UUID;

public class NewsNotFoundException extends NotFound {
    public NewsNotFoundException(UUID newsId) {
        super(newsId);
    }

    @Override
    public String getMessage() {
        return super.getMessage("News");
    }
}
