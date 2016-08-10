package com.netcracker.solutions.kpi.persistence.dto.scheduling;

public class StudentSchedulePriorityDto {

    private String timePoint;

    private String priority;

    public StudentSchedulePriorityDto() {
    }

    public StudentSchedulePriorityDto(String timePoint, String priority) {
        this.timePoint = timePoint;
        this.priority = priority;
    }

    public String getTimePoint() {
        return timePoint;
    }

    public void setTimePoint(String timePoint) {
        this.timePoint = timePoint;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

}
