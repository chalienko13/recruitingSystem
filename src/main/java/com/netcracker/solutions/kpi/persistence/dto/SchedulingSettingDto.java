package com.netcracker.solutions.kpi.persistence.dto;

/**
 * Created by IO on 16.05.2016.
 */
public class SchedulingSettingDto {
    private long amountOfStudents;
    private long amountOfTech;
    private long amountOfSoft;

    public SchedulingSettingDto() {
    }

    public SchedulingSettingDto(long amountOfStudents) {
        this.amountOfStudents = amountOfStudents;
    }

    public SchedulingSettingDto(long amountOfStudents, long amountOfTech, long amountOfSoft) {
        this.amountOfStudents = amountOfStudents;
        this.amountOfTech = amountOfTech;
        this.amountOfSoft = amountOfSoft;
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

