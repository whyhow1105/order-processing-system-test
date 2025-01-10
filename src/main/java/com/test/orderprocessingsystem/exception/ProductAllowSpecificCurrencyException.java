package com.test.orderprocessingsystem.exception;

public class ProductAllowSpecificCurrencyException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ProductAllowSpecificCurrencyException(String orderCurrency, 
			String productAllowCurrency) {
		super("Current order currency is choose " + orderCurrency + 
				". Current product type chosen only allow currency as " + productAllowCurrency + " for order.");
	}
}
