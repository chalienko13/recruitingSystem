package com.netcracker.solutions.kpi.persistence.dto;

import java.util.List;

public class AssignedStudentDto {

	private Long id;
	private String photoScope;
	private String firstName;
	private String secondName;
	private String lastName;
	private List<AssignedInterviewDto> interviews;
	
	public AssignedStudentDto() {
	}

	public AssignedStudentDto(Long id, String photoScope, String firstName, String secondName, String lastName,
			List<AssignedInterviewDto> interviews) {
		super();
		this.id = id;
		this.photoScope = photoScope;
		this.firstName = firstName;
		this.secondName = secondName;
		this.lastName = lastName;
		this.interviews = interviews;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPhotoScope() {
		return photoScope;
	}

	public void setPhotoScope(String photoScope) {
		this.photoScope = photoScope;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	
	public List<AssignedInterviewDto> getInterviews() {
		return interviews;
	}

	public void setInterviews(List<AssignedInterviewDto> interviews) {
		this.interviews = interviews;
	}

	@Override
	public String toString() {
		return "AssignedStudentDto [id=" + id + ", photoScope=" + photoScope + ", firstName=" + firstName
				+ ", secondName=" + secondName + ", lastName=" + lastName + ", interviews=" + interviews + "]";
	}

}
