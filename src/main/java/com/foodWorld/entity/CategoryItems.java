package com.foodWorld.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryItems {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long itemId;
	@Column(nullable = false, unique = true)
	String itemName;
	@Column(nullable = false)
	String itemPrice;
	@Column(nullable = false, length = 1000000)
	byte[] itemImage;
	@Column(nullable = false)
	boolean itemstatus;
	@Column(nullable = false)
	String categoryName;
}
