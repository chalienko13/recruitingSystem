package com.netcracker.solutions.kpi.persistence.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * Created by olil0716 on 8/4/2016.
 */
@Entity
@Table(name = "schedule_day_point")
public class ScheduleDayPoint implements Serializable{

    private static final long serialVersionUID = -6748097416467402041L;

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    private Short id;

    @Column(name = "day_point", unique = true, nullable = false)
    private Date dayPoint;

    //mby useless?
    @Transient
    private Set<User> users;

    @OneToMany(mappedBy = "dayPoint", fetch = FetchType.LAZY)
    private Set<ScheduleTimePoint> timePoints;

    public ScheduleDayPoint() {
    }

    public ScheduleDayPoint(Date dayPoint) {
        this.dayPoint = dayPoint;
    }

    public ScheduleDayPoint(Date dayPoint, Set<ScheduleTimePoint> timePoints) {
        this.dayPoint = dayPoint;
        this.timePoints = timePoints;
    }

    public Short getId() {
        return id;
    }
    public void setId(Short id) {
        this.id = id;
    }

    public Date getDayPoint() {
        return dayPoint;
    }
    public void setDayPoint(Date dayPoint) {
        this.dayPoint = dayPoint;
    }

    public Set<User> getUsers() {
        return users;
    }
    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<ScheduleTimePoint> getTimePoints() {
        return timePoints;
    }
    public void setTimePoints(Set<ScheduleTimePoint> timePoints) {
        this.timePoints = timePoints;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ScheduleDayPoint that = (ScheduleDayPoint) o;

        if (!id.equals(that.id)) return false;
        return dayPoint.equals(that.dayPoint);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + dayPoint.hashCode();
        return result;
    }
}
