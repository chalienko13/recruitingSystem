package com.netcracker.solutions.kpi.persistence.dto.scheduling;

import java.sql.Timestamp;

/**
 * Created by Alona on 17.05.2016.
 */
public class ScheduleOverallDto {
    private Long id;
    private Timestamp timePoint;
    private long amountOfStudents;
    private long amountOfTech;
    private long amountOfSoft;

    public ScheduleOverallDto() {
    }

    public ScheduleOverallDto(Timestamp timePoint) {
        this.timePoint = timePoint;
    }

    public ScheduleOverallDto(Long id, Timestamp timePoint, long amountOfStudents, long amountOfTech, long amountOfSoft) {
        this.id = id;
        this.timePoint = timePoint;
        this.amountOfStudents = amountOfStudents;
        this.amountOfTech = amountOfTech;
        this.amountOfSoft = amountOfSoft;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getTimePoint() {
        return timePoint;
    }

    public void setTimePoint(Timestamp timePoint) {
        this.timePoint = timePoint;
    }

    public long getAmountOfStudents() {
        return amountOfStudents;
    }

    public void setAmountOfStudents(long amountOfStudents) {
        this.amountOfStudents = amountOfStudents;
    }

    public long getAmountOfTech() {
        return amountOfTech;
    }

    public void setAmountOfTech(long amountOfTech) {
        this.amountOfTech = amountOfTech;
    }

    public long getAmountOfSoft() {
        return amountOfSoft;
    }

    public void setAmountOfSoft(long amountOfSoft) {
        this.amountOfSoft = amountOfSoft;
    }
}
