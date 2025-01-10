package com.test.orderprocessingsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.orderprocessingsystem.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
	
	public Product getByType(String type);

}
