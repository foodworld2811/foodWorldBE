package com.foodWorld.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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


}
