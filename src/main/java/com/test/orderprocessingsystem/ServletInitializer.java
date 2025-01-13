package com.test.orderprocessingsystem;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.test.orderprocessingsystem.servlet.OrderServlet;
import com.test.orderprocessingsystem.usecase.OrderUseCase;

@Configuration
public class ServletInitializer extends SpringBootServletInitializer {

	private final OrderUseCase orderUseCase;
	
	public ServletInitializer(OrderUseCase orderUseCase) {
		this.orderUseCase = orderUseCase;
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(OrderProcessingSystemApplication.class);
	}
	
	@Bean
	public ServletRegistrationBean<OrderServlet> orderServlet() {
		return new ServletRegistrationBean<>(new OrderServlet(this.orderUseCase), "/orderServlet");
	}

}
