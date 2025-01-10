package com.test.orderprocessingsystem.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity(name="orders")
public class Order extends Maintenance implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name="customer_name", nullable=false, length=255)
	private String customerName;
	
	@Column(name="customer_type", nullable=false, length=10)
	private String customerType;
	
	@OneToOne
	@JoinColumn(name="product_id", referencedColumnName="id", nullable=false)
	private Product product;
	
	@Column(name="amount", nullable=false, precision=14, scale=5)
	private BigDecimal amount;
	
	@Column(name="currency", nullable=false, length=3)
	private String currency;
	
	@Column(name="status", nullable=false, length=10)
	private String status;

	public Order() {
		super();
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
}
