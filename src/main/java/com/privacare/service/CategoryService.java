package com.privacare.service;

import com.privacare.model.entity.Category;
import com.privacare.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Category getCategoryBy(Integer id) throws NoSuchElementException {
        return this.categoryRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Category with id: " + id + " not found"));
    }

    public List<Category> getCategories() {
        return this.categoryRepository.findAll();
    }
}
