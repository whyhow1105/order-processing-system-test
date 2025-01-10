package com.test.orderprocessingsystem.exception;

public class ProductAllowCustomerTypeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ProductAllowCustomerTypeException(String orderCustomerType, 
			String productAllowCustomerType) {
		super("Current order customer type is choose " + orderCustomerType + 
				". Current product type chosen only allow customer type as " + productAllowCustomerType + " for order.");
	}
}
