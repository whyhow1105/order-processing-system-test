package com.test.orderprocessingsystem.usecase;

import com.test.orderprocessingsystem.model.Order;

public interface OrderUseCase {
	public String addOrder(Order order);
	
	public Order queryOrder(String query);
	
	public Order updateOrder(Order order);
	
	public void deleteOrder(String id);
}
