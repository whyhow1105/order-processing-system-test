package com.test.orderprocessingsystem.service;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import com.test.orderprocessingsystem.exception.OrderNotFoundException;
import com.test.orderprocessingsystem.exception.ProductAllowCustomerTypeException;
import com.test.orderprocessingsystem.exception.ProductAllowSpecificCurrencyException;
import com.test.orderprocessingsystem.exception.ProductDisallowSpecificCurrencyException;
import com.test.orderprocessingsystem.exception.ProductNotFoundException;
import com.test.orderprocessingsystem.model.Order;
import com.test.orderprocessingsystem.model.Product;
import com.test.orderprocessingsystem.usecase.OrderUseCase;
import com.test.orderprocessingsystem.usecase.ProductUseCase;

@Service
public class OrderService implements OrderUseCase {
	
	private final CacheManager cacheManager;

	private final ProductUseCase productUseCase;
	
	private final String cacheName = "orders";
	
	public OrderService(CacheManager cacheManager,
			ProductUseCase productUseCase) {
		this.cacheManager = cacheManager;
		this.productUseCase = productUseCase;
	}

	@Override
	public String addOrder(Order order) {
		Cache cache = this.cacheManager.getCache(cacheName);
		try {
			this.validateBusinessRequirement(order);
			order.saveRecord();
			cache.put(order.getId(), order);
			cache.put(order.getCustomerName(), order);
		} catch (Exception ex) {
			throw ex;
		}
		return order.getId();
	}

	@Override
	public Order queryOrder(String query) {
		Cache cache = this.cacheManager.getCache(cacheName);
		Order order = new Order();
		Order orderById = cache.get(query, Order.class);
		Order orderByCustomerName = cache.get(query, Order.class);

		if (orderById != null) {
			order = orderById;
		} else if (orderByCustomerName != null) {
			order = orderByCustomerName;
		} else {
			order = null;
		}
		return order;
	}

	@Override
	public Order updateOrder(Order order) {
		Cache cache = this.cacheManager.getCache(cacheName);
		try {
			Order existingOrder = this.validateOrderById(order.getId(), cache);
			if (!existingOrder.getCustomerName().equals(order.getCustomerName())) {
				cache.evict(existingOrder.getCustomerName());
			}
			existingOrder.setCustomerName(order.getCustomerName());
			existingOrder.setCustomerType(order.getCustomerType());
			existingOrder.setProduct(order.getProduct());
			existingOrder.setAmount(order.getAmount());
			existingOrder.setCurrency(order.getCurrency());
			this.validateBusinessRequirement(existingOrder);
			existingOrder.updateRecord();
			cache.putIfAbsent(existingOrder.getId(), existingOrder);
			cache.putIfAbsent(existingOrder.getCustomerName(), existingOrder);
			return existingOrder;
		} catch (Exception ex) {
			throw ex;
		}
	}

	@Override
	public void deleteOrder(String id) {
		Cache cache = this.cacheManager.getCache(cacheName);
		try {
			Order order = this.validateOrderById(id, cache);
			cache.evict(id);
			cache.evict(order.getCustomerName());
		} catch (Exception ex) {
			throw ex;
		}
	}
	
	private void validateBusinessRequirement(Order order) throws ProductNotFoundException, ProductAllowSpecificCurrencyException, ProductDisallowSpecificCurrencyException, ProductAllowCustomerTypeException {
		
		String orderCurrrency = order.getCurrency();
		String orderCustomerType = order.getCustomerType();
		
		if (order.getProduct() == null) {
			throw new ProductNotFoundException("Current product type is not found");
		} else {
			
			String orderProductType = order.getProduct().getType();
			
			Product product = this.productUseCase.getByType(orderProductType);
			
			if (product == null) {
				throw new ProductNotFoundException("Current product type " + orderProductType + " is not found !!!");
			} else {
				
				String productAllowCurrency = product.getAllowCurrency();
				String productAllowCustomerType = product.getAllowCustomerType();
				
				if (product.isAllowSpecificCurrency() && 
						!productAllowCurrency.equals(orderCurrrency)) {
					throw new ProductAllowSpecificCurrencyException(orderCurrrency, productAllowCurrency);
				} else if (product.isDisallowSpecificCurrency() &&
						product.getDisallowCurrency().equals(orderCurrrency)) {
					throw new ProductDisallowSpecificCurrencyException(orderCurrrency);
				} else if (product.isAllowSpecificCustomerType() &&
						!productAllowCustomerType.equals(orderCustomerType)) {
					throw new ProductAllowCustomerTypeException(orderCustomerType, productAllowCustomerType);
				}
				
				order.setProduct(product);
			}
		}
	}
	
	private Order validateOrderById(String id, Cache cache) throws OrderNotFoundException {
		Order order = cache.get(id, Order.class);
		
		if (order == null) {
			throw new OrderNotFoundException(id);
		}
		return order;
	}

}
