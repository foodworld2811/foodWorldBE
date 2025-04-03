package com.foodWorld.controller;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class loginResponse {
	public String token;
	
	public loginResponse(String token) {
		this.token = token;
	}
}
