package com.foodWorld.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.foodWorld.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

}
