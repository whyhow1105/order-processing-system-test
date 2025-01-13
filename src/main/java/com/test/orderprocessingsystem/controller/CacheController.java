package com.test.orderprocessingsystem.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.orderprocessingsystem.service.CacheService;

@RestController
@RequestMapping("/cache")
public class CacheController {
	
	private final CacheService cs;
	
	public CacheController(CacheService cs) {
		this.cs = cs;
	}
	
	@GetMapping() 
	public void getAllCache() {
		this.cs.getCacheDetail();
	}

}
