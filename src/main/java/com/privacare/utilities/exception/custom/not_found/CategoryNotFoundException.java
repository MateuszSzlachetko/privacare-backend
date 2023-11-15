package com.privacare.utilities.exception.custom.not_found;

public class CategoryNotFoundException extends NotFound {
    public CategoryNotFoundException(Integer categoryId) {
        super(categoryId);
    }

    @Override
    public String getMessage() {
        return super.getMessage("Category");
    }
}
