package com.test.orderprocessingsystem.service;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.test.orderprocessingsystem.entity.Product;
import com.test.orderprocessingsystem.repository.ProductRepository;
import com.test.orderprocessingsystem.usecase.ProductUseCase;

@Service
public class ProductService implements ProductUseCase {
	
	private final ProductRepository productRepository;
	
	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@Override
//	@CachePut(value = "products", key = "#product.type")
	public void addProduct(Product product) {
		
		Product existProduct = this.productRepository.getByType(product.getType());
		
		if (existProduct == null) {
			this.productRepository.save(product);
		} else {
			existProduct.setDisallowCurrency(product.getDisallowCurrency());
			existProduct.setDisallowSpecificCurrency(product.isDisallowSpecificCurrency());
			existProduct.setAllowCurrency(product.getAllowCurrency());
			existProduct.setAllowCustomerType(product.getAllowCustomerType());
			existProduct.setAllowSpecificCurrency(product.isAllowSpecificCurrency());
			existProduct.setAllowSpecificCustomerType(product.isAllowSpecificCustomerType());
			
			this.productRepository.save(existProduct);
		}
		
	}

	@Override
//	@Cacheable(value = "products", key = "#type")
	public Product getByType(String type) {
		return this.productRepository.getByType(type);
	}

}
