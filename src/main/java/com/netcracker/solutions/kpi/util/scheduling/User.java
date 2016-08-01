package com.netcracker.solutions.kpi.util.scheduling;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by natalya on 14.05.2016.
 */
public class User {
    private Long id;
    private List<Timestamp> timesAndDates = new ArrayList<>();

    public User() {
    }

    public User(Long id, List<Timestamp> timesAndDates) {
        this.id = id;
        this.timesAndDates = timesAndDates;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Timestamp> getTimesAndDates() {
        return timesAndDates;
    }

    public void setTimesAndDates(List<Timestamp> timesAndDates) {
        this.timesAndDates = timesAndDates;
    }

    @Override
    public String toString() {
        return id +
                ", timesAndDates=" + timesAndDates +
                " --|||||-- ";
    }
}