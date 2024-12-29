package com.foodWorld.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.foodWorld.entity.CategoryItems;
import com.foodWorld.entity.User;
import com.foodWorld.service.CategoryItemsService;

@RestController
@RequestMapping("/api/categoryItems")
@CrossOrigin(origins = "http://localhost:4200")
public class CategoryItemsController {
	
	@Autowired
	private CategoryItemsService categoryItemsServe;
	
	@PostMapping
	public ResponseEntity<CategoryItems> addCategoryItems(@RequestParam String itemName, @RequestParam String itemPrice,@RequestParam boolean itemStatus,@RequestParam String categoryName, @RequestPart MultipartFile file) throws IOException {
			CategoryItems categoryItems = new CategoryItems();
			categoryItems.setItemName(itemName);
			categoryItems.setItemPrice(itemPrice);
			categoryItems.setItemstatus(itemStatus);
			categoryItems.setCategoryName(categoryName);
			categoryItems.setItemImage(file.getBytes());
			CategoryItems savedItem = categoryItemsServe.addCategoryItems(categoryItems);
			return ResponseEntity.status(HttpStatus.CREATED).body(savedItem);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<CategoryItems> updateCategoryItems(
	        @PathVariable Long id,
	        @RequestParam(required = false) String itemName,
	        @RequestParam(required = false) String itemPrice,
	        @RequestParam(required = false) boolean itemStatus,
	        @RequestParam(required = false) String categoryName,
	        @RequestPart(required = false) MultipartFile file) throws IOException {
	    		
	    // Fetch the existing category item by ID
	    Optional<CategoryItems> existingItemOptional = categoryItemsServe.findCategoryItemsById(id);
	    
//		System.out.println("Item Status is : "+existingItemOptional.get());
	    
	    if (!existingItemOptional.isPresent()) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	    }
	    
	    CategoryItems existingItem = existingItemOptional.get();

	    // Update fields only if new values are provided
	    if (itemName != null) {
	        existingItem.setItemName(itemName);
	    }
	    if (itemPrice != null) {
	        existingItem.setItemPrice(itemPrice);
	    }
	    
        existingItem.setItemstatus(itemStatus);

	    if (categoryName != null) {
	        existingItem.setCategoryName(categoryName);
	    }
	    if (file != null) {
	        existingItem.setItemImage(file.getBytes());
	    }

	    // Save the updated category item
	    CategoryItems updatedItem = categoryItemsServe.updateCategoryItems(existingItem);

	    return ResponseEntity.ok(updatedItem);
	}

	
	@GetMapping
	public ResponseEntity<List<CategoryItems>> getAllCategoryItems(){
		List<CategoryItems> allCategoryItems = categoryItemsServe.getAllCategoryItems();
		if(allCategoryItems == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();	
		}
		return ResponseEntity.status(HttpStatus.OK).body(allCategoryItems);
	}
	
	@DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        Optional<CategoryItems> categoryItems = categoryItemsServe.findCategoryItemsById(id);  // Returns Optional<User>

        if (categoryItems.isPresent()) {
        	categoryItemsServe.deleteUser(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();  // 204 No Content
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();  // 404 Not Found
        }
    }
}
