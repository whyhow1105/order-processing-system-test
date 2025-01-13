package com.test.orderprocessingsystem.model;

import java.util.Date;

public abstract class Auditable {

	private Date createdDateTime;
	
	private Date updatedDateTime;
	
	public Auditable() {
		
	}
	
	public Date getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(Date createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	public Date getUpdatedDateTime() {
		return updatedDateTime;
	}

	public void setUpdatedDateTime(Date updatedDateTime) {
		this.updatedDateTime = updatedDateTime;
	}
	
	public abstract void saveRecord();
	
	public abstract void updateRecord();
}
