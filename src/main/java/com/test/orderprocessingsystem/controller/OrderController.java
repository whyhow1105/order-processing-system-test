package com.test.orderprocessingsystem.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.orderprocessingsystem.model.Order;
import com.test.orderprocessingsystem.usecase.OrderUseCase;

@RestController
@RequestMapping("/order")
public class OrderController {
	
	private final OrderUseCase orderUseCase;
	
	public OrderController(OrderUseCase orderUseCase) {
		this.orderUseCase = orderUseCase;
	}
	
	@PostMapping
	public ResponseEntity<String> addOrder(@RequestBody Order order) {
		return ResponseEntity.ok(this.orderUseCase.addOrder(order));
	}
	
	@GetMapping("/{query}")
	public ResponseEntity<Order> queryOrder(@PathVariable String query) {
		return ResponseEntity.ok(this.orderUseCase.queryOrder(query));
	}
	
	@PutMapping()
	public ResponseEntity<Order> updateOrder(@RequestBody Order order) {
		return ResponseEntity.ok(this.orderUseCase.updateOrder(order));
	}
	
	@DeleteMapping("/{id}")
	public void deleteOrder(@PathVariable String id) {
		this.orderUseCase.deleteOrder(id);
	}
}
