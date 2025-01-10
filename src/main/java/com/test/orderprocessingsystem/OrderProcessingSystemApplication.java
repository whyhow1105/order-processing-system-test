package com.test.orderprocessingsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableCaching
@EnableJpaAuditing
@SpringBootApplication
public class OrderProcessingSystemApplication {
	
//	private static ApplicationContext applicationContext;

	public static void main(String[] args) {
		SpringApplication.run(OrderProcessingSystemApplication.class, args);
//		applicationContext = SpringApplication.run(OrderProcessingSystemApplication.class, args);
//		
//		for (String bean: applicationContext.getBeanDefinitionNames()) {
//			System.out.println(bean);
//		}
	}

}
