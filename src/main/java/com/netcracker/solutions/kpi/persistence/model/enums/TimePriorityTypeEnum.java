package com.netcracker.solutions.kpi.persistence.model.enums;

public enum TimePriorityTypeEnum {

	WANT(1L), CAN(2L), CANNOT(3L);

	private Long id;
	
	TimePriorityTypeEnum(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}
	
}
