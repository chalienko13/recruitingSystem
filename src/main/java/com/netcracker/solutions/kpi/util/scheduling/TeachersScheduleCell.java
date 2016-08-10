package com.netcracker.solutions.kpi.util.scheduling;

import java.sql.Timestamp;
import java.util.ArrayList;

public class TeachersScheduleCell {

    private Timestamp date;
    private ArrayList<User> teachers = new ArrayList<>();

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public ArrayList<User> getTeachers() {
        return teachers;
    }

    @Override
    public String toString() {
        return date.toString() + " | Teachers: " + teachers;
    }
}
