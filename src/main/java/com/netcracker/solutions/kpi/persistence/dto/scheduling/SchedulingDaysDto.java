package com.netcracker.solutions.kpi.persistence.dto.scheduling;

/**
 * Created by IO on 17.05.2016.
 */
public class SchedulingDaysDto {
    private Long id;
    private Long day;
    private int hourStart;
    private int hourEnd;
    private boolean changed;

    public SchedulingDaysDto() {
    }

    public SchedulingDaysDto(Long day, int hourStart, int hourEnd) {
        this.day = day;
        this.hourStart = hourStart;
        this.hourEnd = hourEnd;
    }

    public SchedulingDaysDto(Long id, Long day, int hourStart, int hourEnd) {
        this.id = id;
        this.day = day;
        this.hourStart = hourStart;
        this.hourEnd = hourEnd;
    }

    public SchedulingDaysDto(Long id, Long day, int hourStart, int hourEnd, boolean changed) {
        this.id = id;
        this.day = day;
        this.hourStart = hourStart;
        this.hourEnd = hourEnd;
        this.changed = changed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDay() {
        return day;
    }

    public void setDay(Long day) {
        this.day = day;
    }

    public int getHourStart() {
        return hourStart;
    }

    public void setHourStart(int hourStart) {
        this.hourStart = hourStart;
    }

    public int getHourEnd() {
        return hourEnd;
    }

    public void setHourEnd(int hourEnd) {
        this.hourEnd = hourEnd;
    }

    public boolean isChanged() {
        return changed;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }

    @Override
    public String toString() {
        return "SchedulingDaysDto{" +
                "day=" + day +
                ", hourStart=" + hourStart +
                ", hourEnd=" + hourEnd +
                '}';
    }
}
