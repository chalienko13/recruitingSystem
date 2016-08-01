package com.netcracker.solutions.kpi.persistence.model.enums;

public enum FormQuestionTypeEnum {
	INPUT(1L, "input"), CHECKBOX(2L, "checkbox"), SELECT(3L, "select"), RADIO(4L, "radio"), TEXTAREA(5L, "textarea");

	FormQuestionTypeEnum(Long id, String title) {
		this.id = id;
		this.title = title;
	}

	private Long id;

	private String title;
	
	public Long getId() {
		return id;
	}
	
	public String getTitle() {
		return title;
	}
}
