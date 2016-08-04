package com.netcracker.solutions.kpi.persistence.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

/**
 * Created by olil0716 on 8/4/2016.
 */
@Entity
@Table(name = "schedule_time_point")
public class ScheduleTimePoint implements Serializable {
    private static final long serialVersionUID = 4720561457216297170L;

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "schedule_time_point", unique = true, nullable = false)
    private Timestamp timePoint;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_day_point_id")
    private ScheduleDayPoint dayPoint;

    public ScheduleTimePoint() {
    }

    public ScheduleTimePoint(Timestamp timePoint) {
        this.timePoint = timePoint;
    }

    public ScheduleTimePoint(Timestamp timePoint, ScheduleDayPoint dayPoint) {
        this.timePoint = timePoint;
        this.dayPoint = dayPoint;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public Timestamp getTimePoint() {
        return timePoint;
    }
    public void setTimePoint(Timestamp timePoint) {
        this.timePoint = timePoint;
    }

    public ScheduleDayPoint getDayPoint() {
        return dayPoint;
    }
    public void setDayPoint(ScheduleDayPoint dayPoint) {
        this.dayPoint = dayPoint;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ScheduleTimePoint that = (ScheduleTimePoint) o;

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
