package com.test.orderprocessingsystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.test.orderprocessingsystem.constant.Currency;
import com.test.orderprocessingsystem.constant.CustomerType;
import com.test.orderprocessingsystem.model.Product;
import com.test.orderprocessingsystem.usecase.ProductUseCase;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductServiceTests {
	
	@Autowired
	ProductUseCase productUseCase;
	
	@Autowired
	CacheManager cacheManager;
	
	private final String cacheName = "products";

	Product product = new Product();
	Product product2 = new Product();
	Product product3 = new Product();
	Product product4 = new Product();
	
	@Test
	public void contextLoads() { }
	
	@Before
	public void init() {
		product.setType("CCC");
		product.setAllowSpecificCurrency(true);
		product.setAllowCurrency(Currency.USD.name());
		product.setAllowSpecificCustomerType(true);
		product.setAllowCustomerType(CustomerType.VIP.name());
		product.setDisallowSpecificCurrency(true);
		product.setDisallowCurrency(Currency.MYR.name());
		
		product2.setType("ZZZ");
		product2.setAllowSpecificCurrency(false);
		product2.setAllowSpecificCustomerType(false);
		product2.setDisallowSpecificCurrency(true);
		product2.setDisallowCurrency(Currency.USD.name());
		
		product3.setType("ZZZ");
		product3.setAllowSpecificCurrency(false);
		product3.setAllowSpecificCustomerType(false);
		product3.setDisallowSpecificCurrency(true);
		product3.setDisallowCurrency(Currency.JPY.name());
		
		product4.setType("BBB");
		product4.setAllowSpecificCurrency(false);
		product4.setAllowSpecificCustomerType(false);
		product4.setDisallowSpecificCurrency(false);
	}
	
	@Test
	public void testAdd() {
		Cache cache = this.cacheManager.getCache(cacheName);
		
		this.productUseCase.addProduct(product);
		Product pro = cache.get(product.getType(), Product.class);
		assertEquals(pro.getType(), product.getType());
		assertNotNull(pro.getId());
		assertEquals(pro.getCreatedDateTime(), pro.getUpdatedDateTime());
		
		this.productUseCase.addProduct(product2);
		Product pro2 = cache.get(product2.getType(), Product.class);
		assertEquals(pro2.getType(), product2.getType());
		assertNotNull(pro2.getId());
		assertEquals(pro2.getCreatedDateTime(), pro2.getUpdatedDateTime());
		
		this.productUseCase.addProduct(product3);
		Product pro3 = cache.get(product3.getType(), Product.class);
		assertEquals(pro3.getType(), product3.getType());
		assertNotNull(pro3.getId());
		assertNotEquals(pro3.getCreatedDateTime(), pro3.getUpdatedDateTime());
		
		this.productUseCase.addProduct(product4);
		Product pro4 = cache.get(product4.getType(), Product.class);
		assertEquals(pro4.getType(), product4.getType());
		assertNotNull(pro4.getId());
		assertEquals(pro4.getCreatedDateTime(), pro4.getUpdatedDateTime());		
	}
	
	@Test
	public void testGetByType() {
		Cache cache = this.cacheManager.getCache(cacheName);
		
		Product pro = cache.get(product4.getType(), Product.class);
		assertNotNull(pro);
		
		Product pro2 = cache.get("QWE", Product.class);
		assertNull(pro2);
	}
}

