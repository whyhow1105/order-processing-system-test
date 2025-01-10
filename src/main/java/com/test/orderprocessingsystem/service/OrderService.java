package com.test.orderprocessingsystem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.test.orderprocessingsystem.entity.Order;
import com.test.orderprocessingsystem.entity.Product;
import com.test.orderprocessingsystem.exception.OrderNotFoundException;
import com.test.orderprocessingsystem.exception.ProductAllowCustomerTypeException;
import com.test.orderprocessingsystem.exception.ProductAllowSpecificCurrencyException;
import com.test.orderprocessingsystem.exception.ProductDisallowSpecificCurrencyException;
import com.test.orderprocessingsystem.exception.ProductNotFoundException;
import com.test.orderprocessingsystem.repository.OrderRepository;
import com.test.orderprocessingsystem.usecase.OrderUseCase;
import com.test.orderprocessingsystem.usecase.ProductUseCase;

@Service
public class OrderService implements OrderUseCase {
	
	private final OrderRepository orderRepository;
	
	private final ProductUseCase productUseCase;
	
	public OrderService(OrderRepository orderRepository,
			ProductUseCase productUseCase) {
		this.orderRepository = orderRepository;
		this.productUseCase = productUseCase;
	}

	@Override
	@CachePut(value = "orders", key = "#order.customerName")
	public String addOrder(Order order) {
		try {
			this.validateBusinessRequirement(order);
			order = this.orderRepository.save(order);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return order.getId();
	}

	@Override
	@Cacheable(value = "orders", key = "#query")
	public List<Order> queryOrder(String query) {
		List<Order> orderList = this.orderRepository.getByIdOrCustomerName(query, query);
		if (orderList.isEmpty()) {
			return null;
		}
		return orderList;
	}

	@Override
	@CachePut(value = "orders", key = "#order.id")
	public Order updateOrder(Order order) {
		try {
			this.validateOrderById(order.getId());
			this.validateBusinessRequirement(order);
			order = this.orderRepository.save(order);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return order;
	}

	@Override
	@CacheEvict(value = "orders", key = "#id")
	public void deleteOrder(String id) {
		try {
			this.validateOrderById(id);
			this.orderRepository.deleteById(id);
		} catch (Exception ex) {
			ex.printStackTrace();
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
	
	private void validateOrderById(String id) throws OrderNotFoundException {
		Optional<Order> order = this.orderRepository.findById(id);
		
		if (!order.isPresent()) {
			throw new OrderNotFoundException(id);
		} 
	}

}
