package com.netcracker.solutions.kpi.persistence.dto;

import java.sql.Timestamp;

/**
 * Created by Admin on 17.05.2016.
 */
public class UserTimePriorityDto {

    private Long timeStampId;
    private Timestamp timePoint;
    private Long idPriorityType;

    public UserTimePriorityDto() {
    }

    public UserTimePriorityDto(Long timeStampId, Timestamp timePoint, Long idPriorityType) {
        this.timeStampId = timeStampId;
        this.timePoint = timePoint;
        this.idPriorityType = idPriorityType;
    }

    public Long getTimeStampId() {
        return timeStampId;
    }

    public void setTimeStampId(Long timeStampId) {
        this.timeStampId = timeStampId;
    }

    public Timestamp getTimePoint() {
        return timePoint;
    }

    public void setTimePoint(Timestamp timePoint) {
        this.timePoint = timePoint;
    }

    public Long getIdPriorityType() {
        return idPriorityType;
    }

    public void setIdPriorityType(Long idPriorityType) {
        this.idPriorityType = idPriorityType;
    }

    @Override
    public String toString() {
        return "UserTimePriorityDto{" +
                "timePoint=" + timePoint +
                ", idPriorityType=" + idPriorityType +
                '}';
    }
}
