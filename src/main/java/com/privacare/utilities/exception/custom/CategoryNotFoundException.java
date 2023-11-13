package com.privacare.utilities.exception.custom;

public class CategoryNotFoundException extends NotFound {
    public CategoryNotFoundException(Integer itemId) {
        super(itemId);
    }

    @Override
    public String getMessage() {
        return super.getMessage("Category");
    }
}
