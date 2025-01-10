package com.test.orderprocessingsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.orderprocessingsystem.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
	
	public List<Order> getByIdOrCustomerName(String id, String customerName);

}
