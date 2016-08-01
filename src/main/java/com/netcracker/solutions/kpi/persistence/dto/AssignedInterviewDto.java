package com.netcracker.solutions.kpi.persistence.dto;

public class AssignedInterviewDto {

	private Long id;
	private Long role;
	private boolean hasMark;
	
	public AssignedInterviewDto() {
	}

	public AssignedInterviewDto(Long id, Long role, boolean hasMark) {
		super();
		this.id = id;
		this.role = role;
		this.hasMark = hasMark;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRole() {
		return role;
	}

	public void setRole(Long role) {
		this.role = role;
	}

	public boolean isHasMark() {
		return hasMark;
	}

	public void setHasMark(boolean hasMark) {
		this.hasMark = hasMark;
	}

	@Override
	public String toString() {
		return "AssignedInterviewDto [id=" + id + ", role=" + role + ", hasMark=" + hasMark + "]";
	}

	
	
}
