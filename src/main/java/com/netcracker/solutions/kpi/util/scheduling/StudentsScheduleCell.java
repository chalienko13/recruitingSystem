package com.netcracker.solutions.kpi.util.scheduling;

import java.sql.Timestamp;
import java.util.ArrayList;

public class StudentsScheduleCell {
    private Timestamp dateAndHour;
    private int numberOfFreePlace;
    private ArrayList<User> students = new ArrayList<>();

    public Timestamp getDateAndHour() {
        return dateAndHour;
    }

    public void setDateAndHour(Timestamp dateAndHour) {
        this.dateAndHour = dateAndHour;
    }

    public int getNumberOfFreePlace() {
        return numberOfFreePlace;
    }

    public void setNumberOfFreePlace(int numberOfFreePlace) {
        this.numberOfFreePlace = numberOfFreePlace;
    }

    public ArrayList<User> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<User> students) {
        this.students = students;
    }

    @Override
    public String toString() {
        return dateAndHour.toString() + " | free place: " + numberOfFreePlace + " | Students: " + students;
    }
}