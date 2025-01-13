package com.test.orderprocessingsystem.service;

import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.stereotype.Service;

import com.test.orderprocessingsystem.constant.CustomerType;
import com.test.orderprocessingsystem.constant.Status;
import com.test.orderprocessingsystem.model.Order;

@Service
public class BatchService implements Runnable {
	
	private final String cacheName = "orders";
	
	private final CacheManager cacheManager;
	
	private boolean anySpecificRecords = false; 
	
	public BatchService(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	@Override
	public void run() {
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		
		executor.scheduleAtFixedRate(
				() -> { 
					anySpecificRecords = false;
					this.updateSpecificCustomerTypeStatus(); 
					}
				, 0, 1, TimeUnit.SECONDS);
		
		executor.scheduleAtFixedRate(() -> System.out.println(anySpecificRecords), 0, 1, TimeUnit.SECONDS);
		
		if (anySpecificRecords) {
			executor.schedule(() -> this.updateOthersCustomerTypeStatus(), 0, TimeUnit.DAYS);
		}
		
	}
	
	private void updateSpecificCustomerTypeStatus() {
		this.updateStatus(true);
	}
	
	private void updateOthersCustomerTypeStatus() {
		this.updateStatus(false);
	}
	
	private void updateStatus(boolean checkEqual) {
		Cache cache = this.cacheManager.getCache(cacheName);
		CaffeineCacheManager caffeineCacheManager = (CaffeineCacheManager) cacheManager;
		CaffeineCache caffeineCache = (CaffeineCache) caffeineCacheManager.getCache(cacheName);
		com.github.benmanes.caffeine.cache.Cache<Object, Object> caffeine = caffeineCache.getNativeCache();
		
		Set<Object> orderSet = caffeine.asMap().keySet();
		
		for (Object key: orderSet) {
			Order order = cache.get(key, Order.class);
			
			if (this.checkSpecficCondition(order.getCustomerType(), checkEqual) && order.getStatus().equals(Status.PENDING.name())) {
				order.setStatus(Status.PROCESSED.name());
				order.updateRecord();
				cache.putIfAbsent(order.getId(), order);
				cache.putIfAbsent(order.getCustomerName(), order);
				if (checkEqual) {
					anySpecificRecords = true;
				}
			}
		}
	}
	
	private boolean checkSpecficCondition(String customerType, boolean checkEqual) {
		if (checkEqual) {
			return customerType.equals(CustomerType.VIP.name());
		} else {
			return !customerType.equals(CustomerType.VIP.name());
		}
	}

}
