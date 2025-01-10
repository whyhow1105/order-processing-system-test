package com.test.orderprocessingsystem.exception;

public class ProductDisallowSpecificCurrencyException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ProductDisallowSpecificCurrencyException(String currency) {
		super("Current order currency is choose " + currency + 
				". Current product type chosen disallow currency as " + currency + " for order.");
	}
}
