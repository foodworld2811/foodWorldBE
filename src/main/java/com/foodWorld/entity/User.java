package com.foodWorld.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.Size;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	long Id;
	String firstname;
	String lastname;
	@Size(min = 10, max = 10, message = "Phone number must be exactly 10 characters long")
	String contact;
	String address;
	@Size(min = 8, max = 8, message = "Username must be exactly 8 characters long")
	@Column(unique = true)
	String username;
	@Size(min = 8, message = "Password must be grater then 8 characters long")
	String password;
}
