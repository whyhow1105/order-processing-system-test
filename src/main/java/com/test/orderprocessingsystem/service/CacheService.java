package com.test.orderprocessingsystem.service;

import java.util.Collection;

import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.stereotype.Service;

@Service
public class CacheService {

	private final CacheManager cm;
	
	public CacheService(CacheManager cm) {
		this.cm = cm;
	}
	
	public void getCacheDetail() {
		Collection<String> caches = this.cm.getCacheNames();
		
		for (String cache: caches) {
			System.out.println(cache);
			
			CaffeineCacheManager caffeineCacheManager = (CaffeineCacheManager) cm;
			CaffeineCache c = (CaffeineCache) caffeineCacheManager.getCache(cache);
			com.github.benmanes.caffeine.cache.Cache<Object, Object> caffeine = c.getNativeCache();
			
			System.out.println(caffeine.asMap().keySet());
		}
	}
}
