package com.foodWorld.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foodWorld.entity.Tables;
import com.foodWorld.repository.TableRepository;

@Service
public class TableService {

	@Autowired
	private TableRepository tableRepo;
	
	public List<Tables> getAllTables(){
		return tableRepo.findAll();
	}
}
