package com.test.orderprocessingsystem.model;

import java.io.Serializable;
import java.math.BigDecimal;

import com.test.orderprocessingsystem.constant.Status;

public class Order extends Maintenance implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String customerName;
	
	private String customerType;
	
	private Product product;
	
	private BigDecimal amount;
	
	private String currency;
	
	private String status;

	public Order() {
		super("ORDR");
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Override
	public void saveRecord() {
		super.saveRecord();
		this.setStatus(Status.PENDING.name());
	}
}
