package com.test.orderprocessingsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class OrderProcessingSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderProcessingSystemApplication.class, args);
	}

}
