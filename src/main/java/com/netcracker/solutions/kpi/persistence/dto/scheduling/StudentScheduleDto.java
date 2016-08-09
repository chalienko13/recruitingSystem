package com.netcracker.solutions.kpi.persistence.dto.scheduling;

import com.netcracker.solutions.kpi.persistence.dto.scheduling.StudentSchedulePriorityDto;

import java.util.List;

public class StudentScheduleDto {

	private List<String> priorityTypes;
	private List<StudentSchedulePriorityDto> priorities;

	public StudentScheduleDto() {
	}

	public StudentScheduleDto(List<String> priorityTypes, List<StudentSchedulePriorityDto> priorities) {
		super();
		this.priorityTypes = priorityTypes;
		this.priorities = priorities;
	}

	public List<String> getPriorityTypes() {
		return priorityTypes;
	}

	public void setPriorityTypes(List<String> priorityTypes) {
		this.priorityTypes = priorityTypes;
	}

	public List<StudentSchedulePriorityDto> getPriorities() {
		return priorities;
	}

	public void setPriorities(List<StudentSchedulePriorityDto> priorities) {
		this.priorities = priorities;
	}

	@Override
	public String toString() {
		return "StudentScheduleDto [priorityTypes=" + priorityTypes + ", priorities=" + priorities + "]";
	}



}
