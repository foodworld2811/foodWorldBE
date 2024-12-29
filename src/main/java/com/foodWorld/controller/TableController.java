package com.foodWorld.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.foodWorld.entity.Tables;
import com.foodWorld.service.TableService;

@RestController
@RequestMapping("/api/tables")
@CrossOrigin(origins = "http://localhost:4200")
public class TableController {
	
	@Autowired
	private TableService tableServe;

	@GetMapping
	public List<Tables> getAllTables() {
		return tableServe.getAllTables();
	}
}
