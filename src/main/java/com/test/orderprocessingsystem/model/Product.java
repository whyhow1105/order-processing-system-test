package com.test.orderprocessingsystem.model;

import java.io.Serializable;

public class Product extends Maintenance implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String type;
	
	private boolean allowSpecificCurrency;
	
	private String allowCurrency;
	
	private boolean disallowSpecificCurrency;
	
	private String disallowCurrency;
	
	private boolean allowSpecificCustomerType;
	
	private String allowCustomerType;

	public Product() {
		super("PROD");
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
