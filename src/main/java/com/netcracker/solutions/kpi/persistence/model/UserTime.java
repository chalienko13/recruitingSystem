package com.netcracker.solutions.kpi.persistence.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by olil0716 on 8/4/2016.
 */
@Entity
@Table(name = "user_time")
public class UserTime implements Serializable{
    private static final long serialVersionUID = 7104215664820502279L;

    @Id
    @Column
    Integer id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "id_user")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_day_point")
    private ScheduleDayPoint dayPoint;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "id_type")
    private TimeType timeType;

    public UserTime() {
    }

    public UserTime(User user, ScheduleDayPoint dayPoint, TimeType timeType) {
        this.user = user;
        this.dayPoint = dayPoint;
        this.timeType = timeType;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public ScheduleDayPoint getDayPoint() {
        return dayPoint;
    }
    public void setDayPoint(ScheduleDayPoint dayPoint) {
        this.dayPoint = dayPoint;
    }

    public TimeType getTimeType() {
        return timeType;
    }
    public void setTimeType(TimeType timeType) {
        this.timeType = timeType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserTime userTime = (UserTime) o;

        if (!id.equals(userTime.id)) return false;
        if (!user.equals(userTime.user)) return false;
        if (!dayPoint.equals(userTime.dayPoint)) return false;
        return timeType.equals(userTime.timeType);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + user.hashCode();
        result = 31 * result + dayPoint.hashCode();
        result = 31 * result + timeType.hashCode();
        return result;
    }
}
