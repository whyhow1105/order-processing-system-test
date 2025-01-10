package com.test.orderprocessingsystem.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class Maintenance extends Auditable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name="id", unique=true, nullable=false, length=36)
	private String id;

	public Maintenance() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
