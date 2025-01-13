package com.test.orderprocessingsystem.service;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import com.test.orderprocessingsystem.model.Product;
//import com.test.orderprocessingsystem.repository.ProductRepository;
import com.test.orderprocessingsystem.usecase.ProductUseCase;

@Service
public class ProductService implements ProductUseCase {
	
	private final CacheManager cacheManager;
	
	private final String cacheName = "products";
	
	public ProductService(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	@Override
	public void addProduct(Product product) {
		Cache cache = this.cacheManager.getCache(cacheName);
		Product existProduct = this.getByType(product.getType());
		
		if (existProduct == null) {
			product.saveRecord();
			cache.put(product.getType(), product);
		} else {
			existProduct.setDisallowCurrency(product.getDisallowCurrency());
			existProduct.setDisallowSpecificCurrency(product.isDisallowSpecificCurrency());
			existProduct.setAllowCurrency(product.getAllowCurrency());
			existProduct.setAllowCustomerType(product.getAllowCustomerType());
			existProduct.setAllowSpecificCurrency(product.isAllowSpecificCurrency());
			existProduct.setAllowSpecificCustomerType(product.isAllowSpecificCustomerType());
			existProduct.updateRecord();
			cache.putIfAbsent(existProduct.getType(), existProduct);
		}
	}

	@Override
	public Product getByType(String type) {
		Cache cache = this.cacheManager.getCache(cacheName);
		Product product = cache.get(type, Product.class);
		
		if (product == null) {
			return null;
		}
		
		return product;
	}

}
