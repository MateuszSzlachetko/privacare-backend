package com.privacare.utilities.exception.custom;

public class CategoryNotFoundException extends NotFound {
    public CategoryNotFoundException(Integer itemId) {
        super(itemId);
    }

    public String getMessage() {
        return super.getMessage("Category");
    }
}
