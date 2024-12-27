package com.foodWorld.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.foodWorld.entity.Tables;

@Repository
public interface TableRepository extends JpaRepository<Tables, Long> {

}
