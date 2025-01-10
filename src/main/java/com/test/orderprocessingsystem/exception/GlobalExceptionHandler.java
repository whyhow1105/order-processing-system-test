package com.test.orderprocessingsystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler({Exception.class})
	public ResponseEntity<Object> handleException(Exception exception) {
		System.out.println("Exception");
		return ResponseEntity
				.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(exception.getMessage());
	}
	
	@ExceptionHandler({RuntimeException.class})
	public ResponseEntity<Object> handleRuntimeException(RuntimeException runtimeException) {
		System.out.println("RuntimeException");
		return ResponseEntity
				.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(runtimeException.getMessage());
	}
	
	@ExceptionHandler({OrderNotFoundException.class})
	public ResponseEntity<Object> handleOrderNotFoundException(OrderNotFoundException orderNotFoundException) {
		System.out.println("OrderNotFoundException");
		return ResponseEntity
				.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(orderNotFoundException.getMessage());
	}
	
	@ExceptionHandler({ProductAllowCustomerTypeException.class})
	public ResponseEntity<Object> handleProductAllowCustomerTypeException(ProductAllowCustomerTypeException productAllowCustomerTypeException) {
		System.out.println("ProductAllowCustomerTypeException");
		return ResponseEntity
				.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(productAllowCustomerTypeException.getMessage());
	}
	
	@ExceptionHandler({ProductAllowSpecificCurrencyException.class})
	public ResponseEntity<Object> handleProductAllowSpecificCurrencyException(ProductAllowSpecificCurrencyException productAllowSpecificCurrencyException) {
		System.out.println("ProductAllowSpecificCurrencyException");
		return ResponseEntity
				.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(productAllowSpecificCurrencyException.getMessage());
	}
	
	@ExceptionHandler({ProductDisallowSpecificCurrencyException.class})
	public ResponseEntity<Object> handleProductDisallowSpecificCurrencyException(ProductDisallowSpecificCurrencyException productDisallowSpecificCurrencyException) {
		System.out.println("ProductDisallowSpecificCurrencyException");
		return ResponseEntity
				.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(productDisallowSpecificCurrencyException.getMessage());
	}
	
	@ExceptionHandler(ProductNotFoundException.class)
	public ResponseEntity<Object> handleProductNotFoundException(ProductNotFoundException ex) {
		System.out.println("ProductNotFoundException");
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
