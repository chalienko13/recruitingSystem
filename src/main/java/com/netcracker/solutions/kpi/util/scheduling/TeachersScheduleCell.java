package com.netcracker.solutions.kpi.util.scheduling;

/**
 * Created by natalya on 14.05.2016.
 */
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

    public void setTeachers(ArrayList<User> teachers) {
        this.teachers = teachers;
    }

    @Override
    public String toString() {
        return date.toString() + " | Teachers: " + teachers;
    }
}
