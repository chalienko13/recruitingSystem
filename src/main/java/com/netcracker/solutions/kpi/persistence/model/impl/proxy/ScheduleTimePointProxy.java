package com.netcracker.solutions.kpi.persistence.model.impl.proxy;

import com.netcracker.solutions.kpi.persistence.model.ScheduleTimePoint;
import com.netcracker.solutions.kpi.persistence.model.User;
import com.netcracker.solutions.kpi.persistence.model.UserTimePriority;
import com.netcracker.solutions.kpi.persistence.model.impl.real.ScheduleTimePointImpl;
import com.netcracker.solutions.kpi.service.ScheduleTimePointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import java.sql.Timestamp;
import java.util.Set;

@Configurable
public class ScheduleTimePointProxy implements ScheduleTimePoint {

    private static final long serialVersionUID = -3918415375041281959L;

    private Long id;

    private ScheduleTimePointImpl scheduleTimePoint;

    @Autowired
    private ScheduleTimePointService service;

    public ScheduleTimePointProxy() {
    }

    public ScheduleTimePointProxy(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Timestamp getTimePoint() {
        checkScheduleTimePoit();
        return scheduleTimePoint.getTimePoint();
    }

    @Override
    public void setTimePoint(Timestamp timePoint) {
        checkScheduleTimePoit();
        scheduleTimePoint.setTimePoint(timePoint);
    }

    @Override
    public Set<User> getUsers() {
        checkScheduleTimePoit();
        return scheduleTimePoint.getUsers();
    }

    @Override
    public void setUsers(Set<User> users) {
        checkScheduleTimePoit();
        scheduleTimePoint.setUsers(users);
    }

    @Override
    public Set<UserTimePriority> getUserTimePriorities() {
        checkScheduleTimePoit();
        return scheduleTimePoint.getUserTimePriorities();
    }

    @Override
    public void setUserTimePriorities(Set<UserTimePriority> priorities) {
        checkScheduleTimePoit();
        scheduleTimePoint.setUserTimePriorities(priorities);
    }

    private void checkScheduleTimePoit(){
        if (scheduleTimePoint == null) {
            scheduleTimePoint = downloadTimePoint();
        }
    }

    private ScheduleTimePointImpl downloadTimePoint() {
        return (ScheduleTimePointImpl) service.getScheduleTimePointById(id);
    }

}
