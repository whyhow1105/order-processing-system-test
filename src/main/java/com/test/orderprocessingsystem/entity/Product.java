package com.test.orderprocessingsystem.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity(name="product")
public class Product extends Maintenance implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name="type", unique=true, nullable=false, length=255)
	private String type;
	
	@Column(name="allow_specific_currency")
	private boolean allowSpecificCurrency;
	
	@Column(name="allow_currency", length=3)
	private String allowCurrency;
	
	@Column(name="disallow_specific_currency")
	private boolean disallowSpecificCurrency;
	
	@Column(name="disallow_currency", length=3)
	private String disallowCurrency;
	
	@Column(name="allow_specific_customer_type")
	private boolean allowSpecificCustomerType;
	
	@Column(name="allow_customer_type", length=10)
	private String allowCustomerType;

	public Product() {
		super();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isAllowSpecificCurrency() {
		return allowSpecificCurrency;
	}

	public void setAllowSpecificCurrency(boolean allowSpecificCurrency) {
		this.allowSpecificCurrency = allowSpecificCurrency;
	}

	public String getAllowCurrency() {
		return allowCurrency;
	}

	public void setAllowCurrency(String allowCurrency) {
		this.allowCurrency = allowCurrency;
	}

	public boolean isDisallowSpecificCurrency() {
		return disallowSpecificCurrency;
	}

	public void setDisallowSpecificCurrency(boolean disallowSpecificCurrency) {
		this.disallowSpecificCurrency = disallowSpecificCurrency;
	}

	public String getDisallowCurrency() {
		return disallowCurrency;
	}

	public void setDisallowCurrency(String disallowCurrency) {
		this.disallowCurrency = disallowCurrency;
	}

	public boolean isAllowSpecificCustomerType() {
		return allowSpecificCustomerType;
	}

	public void setAllowSpecificCustomerType(boolean allowSpecificCustomerType) {
		this.allowSpecificCustomerType = allowSpecificCustomerType;
	}

	public String getAllowCustomerType() {
		return allowCustomerType;
	}

	public void setAllowCustomerType(String allowCustomerType) {
		this.allowCustomerType = allowCustomerType;
	}
}
