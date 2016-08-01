package com.netcracker.solutions.kpi.persistence.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by Admin on 06.05.2016.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RecruitmentSettingsDto {

    private Long id;

    private String name;

    private String registrationDeadline;

    private String scheduleChoicesDeadline;

    private String endDate;

    private int maxGeneralGroup;

    private int maxAdvancedGroup;

    private int softDuration;

    private int techDuration;

    public RecruitmentSettingsDto() {
    }


    public RecruitmentSettingsDto(Long id, String name, String registrationDeadline, String scheduleChoicesDeadline, String endDate, int maxGeneralGroup, int maxAdvancedGroup) {
        this.id = id;
        this.name = name;
        this.registrationDeadline = registrationDeadline;
        this.scheduleChoicesDeadline = scheduleChoicesDeadline;
        this.endDate = endDate;
        this.maxGeneralGroup = maxGeneralGroup;
        this.maxAdvancedGroup = maxAdvancedGroup;
    }

    public RecruitmentSettingsDto(int softDuration, int techDuration) {
        this.softDuration = softDuration;
        this.techDuration = techDuration;
    }

    public int getSoftDuration() {
        return softDuration;
    }

    public void setSoftDuration(int softDuration) {
        this.softDuration = softDuration;
    }

    public int getTechDuration() {
        return techDuration;
    }

    public void setTechDuration(int techDuration) {
        this.techDuration = techDuration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegistrationDeadline() {
        return registrationDeadline;
    }

    public void setRegistrationDeadline(String registrationDeadline) {
        this.registrationDeadline = registrationDeadline;
    }

    public String getScheduleChoicesDeadline() {
        return scheduleChoicesDeadline;
    }

    public void setScheduleChoicesDeadline(String scheduleChoicesDeadline) {
        this.scheduleChoicesDeadline = scheduleChoicesDeadline;
    }

    public int getMaxGeneralGroup() {
        return maxGeneralGroup;
    }

    public void setMaxGeneralGroup(int maxGeneralGroup) {
        this.maxGeneralGroup = maxGeneralGroup;
    }

    public int getMaxAdvancedGroup() {
        return maxAdvancedGroup;
    }

    public void setMaxAdvancedGroup(int maxAdvancedGroup) {
        this.maxAdvancedGroup = maxAdvancedGroup;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "RecruitmentSettingsDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", registrationDeadline='" + registrationDeadline + '\'' +
                ", scheduleChoicesDeadline='" + scheduleChoicesDeadline + '\'' +
                ", endDate='" + endDate + '\'' +
                ", maxGeneralGroup=" + maxGeneralGroup +
                ", maxAdvancedGroup=" + maxAdvancedGroup +
                ", softDuration=" + softDuration +
                ", techDuration=" + techDuration +
                '}';
    }

}
