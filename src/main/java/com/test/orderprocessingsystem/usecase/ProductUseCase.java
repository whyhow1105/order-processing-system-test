package com.test.orderprocessingsystem.usecase;

import com.test.orderprocessingsystem.model.Product;

public interface ProductUseCase {
	
	public void addProduct(Product product);
	
	public Product getByType(String type);

}
