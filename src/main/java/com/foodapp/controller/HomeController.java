package com.foodapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

	
	@GetMapping("/")
	public String HomeControllers()
	{
		return "Welcome to food delivery App";
	}
}
