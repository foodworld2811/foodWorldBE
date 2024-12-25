package com.foodWorld.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foodWorld.entity.CategoryItems;
import com.foodWorld.repository.CategoryItemsRepository;

@Service
public class CategoryItemsService {
	
	@Autowired
	private CategoryItemsRepository categoryItemsRepo;
	
	public CategoryItems addCategoryItems(CategoryItems categoryItems) {
		return categoryItemsRepo.save(categoryItems);
	}
	
	public List<CategoryItems> getAllCategoryItems(){
		return categoryItemsRepo.findAll();
	}
	
	public Optional<CategoryItems> findCategoryItemsById(long id) {
		return categoryItemsRepo.findById(id);
	}
	
	public CategoryItems updateCategoryItems(CategoryItems categoryItems) {
		return categoryItemsRepo.save(categoryItems);
	}

	public void deleteUser(Long id) {
		categoryItemsRepo.deleteById(id);
	}
}
