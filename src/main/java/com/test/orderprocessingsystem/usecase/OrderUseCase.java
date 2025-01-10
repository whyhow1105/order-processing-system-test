package com.test.orderprocessingsystem.usecase;

import java.util.List;

import com.test.orderprocessingsystem.entity.Order;

public interface OrderUseCase {
	public String addOrder(Order order);
	
	public List<Order> queryOrder(String query);
	
	public Order updateOrder(Order order);
	
	public void deleteOrder(String id);
}
