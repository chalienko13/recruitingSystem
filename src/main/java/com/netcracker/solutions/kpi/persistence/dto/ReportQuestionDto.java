package com.netcracker.solutions.kpi.persistence.dto;

public class ReportQuestionDto {

	private Long id;
	private String title;
	
	public ReportQuestionDto() {
	}

	public ReportQuestionDto(Long id, String title) {
		super();
		this.id = id;
		this.title = title;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "ReportQuestionDto [id=" + id + ", title=" + title + "]";
	}

	
}
