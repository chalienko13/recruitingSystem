package com.netcracker.solutions.kpi.persistence.dto;

public class RecruitmentStatusDto {

	private boolean recruitmentExists;
	
	private boolean scheduleExists;

	public RecruitmentStatusDto() {}
	
	public RecruitmentStatusDto(boolean recruitmentExists, boolean scheduleExists) {
		this.recruitmentExists = recruitmentExists;
		this.scheduleExists = scheduleExists;
	}

	public boolean isRecruitmentExists() {
		return recruitmentExists;
	}

	public void setRecruitmentExists(boolean recruitmentExists) {
		this.recruitmentExists = recruitmentExists;
	}

	public boolean isScheduleExists() {
		return scheduleExists;
	}

	public void setScheduleExists(boolean scheduleExists) {
		this.scheduleExists = scheduleExists;
	}

	@Override
	public String toString() {
		return "RecruitmentStastusDto [recruitmentExists=" + recruitmentExists + ", scheduleExists=" + scheduleExists
				+ "]";
	}
	
	
	
}
