package com.test.orderprocessingsystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
import com.test.orderprocessingsystem.exception.OrderNotFoundException;
import com.test.orderprocessingsystem.exception.ProductAllowCustomerTypeException;
import com.test.orderprocessingsystem.exception.ProductAllowSpecificCurrencyException;
import com.test.orderprocessingsystem.exception.ProductDisallowSpecificCurrencyException;
import com.test.orderprocessingsystem.exception.ProductNotFoundException;
import com.test.orderprocessingsystem.model.Order;
import com.test.orderprocessingsystem.model.Product;
import com.test.orderprocessingsystem.usecase.OrderUseCase;
import com.test.orderprocessingsystem.usecase.ProductUseCase;

@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderServiceTests {

	@Autowired
	OrderUseCase orderUseCase;
	
	@Autowired
	ProductUseCase productUseCase;
	
	@Autowired
	CacheManager cacheManager;
	
	private final String cacheName = "orders";
	
	Order order = new Order();
	Order order2 = new Order();
	Order order3 = new Order();
	Order order4 = new Order();

	@Test
	public void contextLoads() { }
	
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
		order2.setCustomerType(CustomerType.VIP.name());
		order2.setProduct(product2);
		order2.setAmount(new BigDecimal(80.00));
		order2.setCurrency(Currency.EUR.name());
		
		Product product3 = this.productUseCase.getByType("AAA");
		
		order3.setCustomerName("William Robin");
		order3.setCustomerType(CustomerType.NORMAL.name());
		order3.setProduct(product3);
		order3.setAmount(new BigDecimal(200.00));
		order3.setCurrency(Currency.JPY.name());
		
		order4.setCustomerName("Yang Jian");
		order4.setCustomerType(CustomerType.VIP.name());
		order4.setProduct(product);
		order4.setAmount(new BigDecimal(1000.00));
		order4.setCurrency(Currency.MYR.name());
	}
	
	@Test
	public void testAdd() {
		Cache cache = this.cacheManager.getCache(cacheName);
		
		String id = this.orderUseCase.addOrder(order);
		assertNotNull(id);
		
		Order ord = cache.get(order.getCustomerName(), Order.class);
		assertEquals(ord.getCustomerName(), order.getCustomerName());
		assertNotNull(ord.getId());
		assertEquals(ord.getCreatedDateTime(), ord.getUpdatedDateTime());
		
		String id2 = this.orderUseCase.addOrder(order2);
		assertNotNull(id2);
		
		Order ord2 = cache.get(order2.getCustomerName(), Order.class);
		assertEquals(ord2.getCustomerName(), order2.getCustomerName());
		assertNotNull(ord2.getId());
		assertEquals(ord2.getCreatedDateTime(), ord2.getUpdatedDateTime());
		
		String id3 = this.orderUseCase.addOrder(order3);
		assertNotNull(id3);
		
		Order ord3 = cache.get(order3.getCustomerName(), Order.class);
		assertEquals(ord3.getCustomerName(), order3.getCustomerName());
		assertNotNull(ord3.getId());
		assertEquals(ord3.getCreatedDateTime(), ord3.getUpdatedDateTime());			
	}
	
	@Test
	public void testAddWithException() {
		Exception exception = assertThrows(ProductAllowSpecificCurrencyException.class, () -> {
			this.orderUseCase.addOrder(order4);
		});
		
		String expectedMsg = "Current product type chosen only allow currency as ";
		String actualMsg = exception.getMessage();
		
		assertTrue(actualMsg.contains(expectedMsg));
		
		order4.setCurrency(Currency.USD.name());
		order4.setCustomerType(CustomerType.NORMAL.name());
		Exception exception2 = assertThrows(ProductAllowCustomerTypeException.class, () -> {
			this.orderUseCase.addOrder(order4);
		});
		
		String expectedMsg2 = "Current product type chosen only allow customer type as ";
		String actualMsg2 = exception2.getMessage();
		
		assertTrue(actualMsg2.contains(expectedMsg2));
		
		Product product = new Product();
		product.setType("XYZ");
		order4.setProduct(product);
		order4.setCurrency(Currency.JPY.name());
		Exception exception3 = assertThrows(ProductDisallowSpecificCurrencyException.class, () -> {
			this.orderUseCase.addOrder(order4);
		});
		
		String expectedMsg3 = "Current product type chosen disallow currency as ";
		String actualMsg3 = exception3.getMessage();
		
		assertTrue(actualMsg3.contains(expectedMsg3));
		
		product.setType("MMM");
		order4.setProduct(product);
		Exception exception4 = assertThrows(ProductNotFoundException.class, () -> {
			this.orderUseCase.addOrder(order4);
		});
		
		String expectedMsg4 = "Current product type ";
		String actualMsg4 = exception4.getMessage();
		
		assertTrue(actualMsg4.contains(expectedMsg4));
	}
	
	@Test
	public void testQuery() {
		Cache cache = this.cacheManager.getCache(cacheName);
		
		String id = this.orderUseCase.addOrder(order);
		
		Order ord = cache.get(order.getCustomerName(), Order.class);
		assertNotNull(ord);
		
		Order ord2 = cache.get("Aloha", Order.class);
		assertNull(ord2);
		
		Order ord3 = cache.get(id, Order.class);
		assertNotNull(ord3);
	}
	
	@Test
	public void testUpdate() {
		Cache cache = this.cacheManager.getCache(cacheName);
		
		String id2 = this.orderUseCase.addOrder(order2);
		
		Order o2 = cache.get(id2, Order.class);
		BigDecimal beforeUpdateAmount = o2.getAmount();
		String customerName = o2.getCustomerName();
		order2.setId(id2);
		order2.setAmount(new BigDecimal(8000.00));
		
		Order ord2 = this.orderUseCase.updateOrder(order2);
		assertEquals(ord2.getId(), o2.getId());
		assertEquals(ord2.getCustomerName(), o2.getCustomerName());
		assertNotEquals(ord2.getAmount(), beforeUpdateAmount);
		
		order2.setCustomerName("Power Ranger");
		Order ord3 = this.orderUseCase.updateOrder(order2);
		assertEquals(ord3.getId(), o2.getId());
		assertNotEquals(customerName, ord3.getCustomerName());
		
		Order o3 = cache.get(order.getCustomerName(), Order.class);
		assertNull(o3);
		
		Order o4 = cache.get(order2.getCustomerName(), Order.class);
		assertNotNull(o4);
	}
	
	@Test
	public void testUpdateWithException() {
		order4.setId("ORD20250114120000");
		
		Exception exception = assertThrows(OrderNotFoundException.class, () -> {
			this.orderUseCase.updateOrder(order4);
		});
		
		String expectedMsg = "Current order ID ";
		String actualMsg = exception.getMessage();
		
		assertTrue(actualMsg.contains(expectedMsg));
		
		order4.setCurrency(Currency.USD.name());
		String id = this.orderUseCase.addOrder(order4);
		
		order4.setId(id);
		order4.setCurrency(Currency.EUR.name());
		
		Exception exception1 = assertThrows(ProductAllowSpecificCurrencyException.class, () -> {
			this.orderUseCase.updateOrder(order4);
		});
		
		String expectedMsg1 = "Current product type chosen only allow currency as ";
		String actualMsg1 = exception1.getMessage();
		
		assertTrue(actualMsg1.contains(expectedMsg1));
		
		order4.setCurrency(Currency.USD.name());
		order4.setCustomerType(CustomerType.NORMAL.name());
		Exception exception2 = assertThrows(ProductAllowCustomerTypeException.class, () -> {
			this.orderUseCase.updateOrder(order4);
		});
		
		String expectedMsg2 = "Current product type chosen only allow customer type as ";
		String actualMsg2 = exception2.getMessage();
		
		assertTrue(actualMsg2.contains(expectedMsg2));
		
		Product product = new Product();
		product.setType("XYZ");
		order4.setProduct(product);
		order4.setCurrency(Currency.JPY.name());
		Exception exception3 = assertThrows(ProductDisallowSpecificCurrencyException.class, () -> {
			this.orderUseCase.updateOrder(order4);
		});
		
		String expectedMsg3 = "Current product type chosen disallow currency as ";
		String actualMsg3 = exception3.getMessage();
		
		assertTrue(actualMsg3.contains(expectedMsg3));
		
		product.setType("MMM");
		order4.setProduct(product);
		Exception exception4 = assertThrows(ProductNotFoundException.class, () -> {
			this.orderUseCase.updateOrder(order4);
		});
		
		String expectedMsg4 = "Current product type ";
		String actualMsg4 = exception4.getMessage();
		
		assertTrue(actualMsg4.contains(expectedMsg4));
	}
	
	@Test
	public void testDelete() {
		Cache cache = this.cacheManager.getCache(cacheName);
		
		String id = this.orderUseCase.addOrder(order);
		
		Order o1 = cache.get(id, Order.class);
		assertNotNull(o1);
		
		this.orderUseCase.deleteOrder(id);
		
		Order o2 = cache.get(id, Order.class);
		assertNull(o2);
		
	}
	
	
}