package com.foodWorld.entity;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
	public Collection<? extends GrantedAuthority> getAuthorities(){
		return List.of();
	}
}
