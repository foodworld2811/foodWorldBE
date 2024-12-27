package com.foodWorld.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foodWorld.entity.Category;
import com.foodWorld.repository.CategoryRepository;


@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
    
    public Optional<Category> findCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    public Category updateCategory(Category category) {
        return categoryRepository.save(category);
    }
}
