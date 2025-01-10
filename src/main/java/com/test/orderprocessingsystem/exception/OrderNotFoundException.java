package com.test.orderprocessingsystem.exception;

public class OrderNotFoundException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public OrderNotFoundException(String id) {
		super("Current order ID " + id + " is not found !!!");
	}

}
