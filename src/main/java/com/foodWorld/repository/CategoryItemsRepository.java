package com.foodWorld.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.foodWorld.entity.CategoryItems;

@Repository
public interface CategoryItemsRepository extends JpaRepository<CategoryItems, Long>{

}
