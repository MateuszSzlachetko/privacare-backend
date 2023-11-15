package com.privacare.service;

import com.privacare.model.entity.Category;
import com.privacare.repository.CategoryRepository;
import com.privacare.utilities.exception.custom.not_found.CategoryNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Category getCategoryBy(Integer id) {
        return this.categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));
    }

    public List<Category> getCategories() {
        return this.categoryRepository.findAll();
    }
}
