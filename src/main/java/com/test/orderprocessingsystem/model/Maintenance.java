package com.test.orderprocessingsystem.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Maintenance extends Auditable {
	
	private String id;
	
	private String module;

	public Maintenance(String module) {
		super();
		this.module = module;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public void saveRecord() {
		Date currentTimestamp = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		this.setId(this.module + simpleDateFormat.format(currentTimestamp));
		this.setCreatedDateTime(currentTimestamp);
		this.setUpdatedDateTime(currentTimestamp);
	}

	@Override
	public void updateRecord() {
		this.setUpdatedDateTime(new Date());
	}
}
