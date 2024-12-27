package com.foodWorld.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.foodWorld.entity.Category;
import com.foodWorld.service.CategoryService;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "http://localhost:4200")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<Category> addCategory(
            @RequestParam String categoryTitle,@RequestParam boolean categoryStatus,
            @RequestPart("img") MultipartFile file) throws IOException {
        Category category = new Category();
        category.setCategoryTitle(categoryTitle);
        category.setCategoryStatus(categoryStatus);
        category.setCategoryimg(file.getBytes());
        Category savedCategory = categoryService.addCategory(category);
        return ResponseEntity.ok(savedCategory);
    }
    
    @GetMapping
    public  ResponseEntity<List<Category>> getAllItems(){
        List<Category> userList = categoryService.getAllCategories();
        return  ResponseEntity.ok(userList);
    }
    
    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    public ResponseEntity<Category> updateCategory(
            @PathVariable Long id,
            @RequestParam(required = false) String categoryTitle,
            @RequestParam(required = false) Boolean categoryStatus,
            @RequestPart(required = false) MultipartFile img) throws IOException {

        // Fetch the existing category by ID
        Optional<Category> existingCategoryOptional = categoryService.findCategoryById(id);

        if (!existingCategoryOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Category existingCategory = existingCategoryOptional.get();

        // Update fields only if new values are provided
        if (categoryTitle != null) {
            existingCategory.setCategoryTitle(categoryTitle);
        }
        if (categoryStatus != null) {
            existingCategory.setCategoryStatus(categoryStatus);
        }
        if (img != null) {
            existingCategory.setCategoryimg(img.getBytes());
        }

        // Save the updated category
        Category updatedCategory = categoryService.updateCategory(existingCategory);

        return ResponseEntity.ok(updatedCategory);
    }

    
}
