package com.foodWorld.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.foodWorld.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{

	List<Order> findByOrderDate(LocalDate orderDate);
	List<Order> findByCreatedBy(String createdBy);
	
	List<Order> findByOrderDateBetween(LocalDate startDate, LocalDate endDate);
}
