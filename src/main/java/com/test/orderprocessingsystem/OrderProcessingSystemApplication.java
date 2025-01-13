package com.test.orderprocessingsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;

import com.test.orderprocessingsystem.service.BatchService;

@EnableCaching
@SpringBootApplication
public class OrderProcessingSystemApplication {
	
	private static CacheManager cacheManager;
	
	public OrderProcessingSystemApplication(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(OrderProcessingSystemApplication.class, args);
		
		BatchService batchService = new BatchService(cacheManager);
		batchService.run();
	}

}
