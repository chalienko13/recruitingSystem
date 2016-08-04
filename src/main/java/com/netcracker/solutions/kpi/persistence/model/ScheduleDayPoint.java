package com.netcracker.solutions.kpi.persistence.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * Created by olil0716 on 8/4/2016.
 */
@Table(name = "schedule_day_point")
public class ScheduleDayPoint {

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    Integer id;

    @Column(name = "day_point")
    private Date timePoint;

    //mby useless
    @Transient
    private Set<User> users;

    //TODO create UserTime
    /*@Transient
    private Set<UserTime> userTimePriorities;*/

    public ScheduleDayPoint() {
    }

    public ScheduleDayPoint(Date timePoint) {
        this.timePoint = timePoint;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public Date getTimePoint() {
        return timePoint;
    }
    public void setTimePoint(Date timePoint) {
        this.timePoint = timePoint;
    }

    public Set<User> getUsers() {
        return users;
    }
    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ScheduleDayPoint that = (ScheduleDayPoint) o;

        if (!id.equals(that.id)) return false;
        return timePoint.equals(that.timePoint);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + timePoint.hashCode();
        return result;
    }
}
