package com.netcracker.solutions.kpi.persistence.model.enums;

public enum ReportTypeEnum {
	APPROVED(1L), ANSWERS(2L);

	ReportTypeEnum(Long id) {
		this.id = id;
	}

	private Long id;

	public Long getId() {
		return id;
	}
}
