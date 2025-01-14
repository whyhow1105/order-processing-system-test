package com.test.orderprocessingsystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;

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
import com.test.orderprocessingsystem.model.Order;
import com.test.orderprocessingsystem.model.Product;
import com.test.orderprocessingsystem.usecase.OrderUseCase;
import com.test.orderprocessingsystem.usecase.ProductUseCase;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BatchServiceTests {

	@Autowired
	BatchService batchService;
	
	@Autowired
	ProductUseCase productUseCase;
	
	@Autowired
	OrderUseCase orderUseCase;
	
	@Autowired
	CacheManager cacheManager;
	
	private final String cacheName = "orders";
	
	Order order = new Order();
	Order order2 = new Order();
	
	@Before
	public void init() {
		
		Product product = this.productUseCase.getByType("ABC");
		
		order.setCustomerName("Anne Tan");
		order.setCustomerType(CustomerType.VIP.name());
		order.setProduct(product);
		order.setAmount(new BigDecimal(100.00));
		order.setCurrency(Currency.USD.name());
		
		Product product2 = this.productUseCase.getByType("XYZ");
		
		order2.setCustomerName("Charles Ranger");
		order2.setCustomerType(CustomerType.NORMAL.name());
		order2.setProduct(product2);
		order2.setAmount(new BigDecimal(80.00));
		order2.setCurrency(Currency.EUR.name());
	}
	
	@Test
	public void contextLoads() { }
	
	@Test
	public void runBatch() {
		String id = this.orderUseCase.addOrder(order);
		String id2 = this.orderUseCase.addOrder(order2);
		
		Cache cache = this.cacheManager.getCache(cacheName);
		
		Order o = cache.get(id, Order.class);
		String ordStatus = o.getStatus();
		assertNotNull(o);
		
		Order o2 = cache.get(id2, Order.class);
		String ordStatus2 = o2.getStatus();
		assertNotNull(o2);
		
		batchService.run();
		
		Order o3 = cache.get(id, Order.class);
		Order o4 = cache.get(id2, Order.class);
		
		assertEquals(ordStatus, o3.getStatus());
		assertEquals(ordStatus2, o4.getStatus());
	}
}