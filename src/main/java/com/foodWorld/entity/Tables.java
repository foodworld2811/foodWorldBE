package com.foodWorld.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Tables {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long tableId;
	
	@Column(unique = true)
	private String tableName;
	
	private String userName;
}
