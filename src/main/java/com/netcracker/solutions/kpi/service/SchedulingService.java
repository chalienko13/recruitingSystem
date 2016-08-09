package com.netcracker.solutions.kpi.service;

import com.netcracker.solutions.kpi.persistence.model.ScheduleDayPoint;
import com.netcracker.solutions.kpi.persistence.model.ScheduleTimePoint;
import com.netcracker.solutions.kpi.persistence.model.SchedulingSettings;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Korzh
 */
public interface SchedulingService {

    //FOR WORK WITH SCHEDULE

    void createScheduleDayPoints(List<ScheduleDayPoint> scheduleDayPoints);

    void deleteScheduleDayPoints(List<ScheduleDayPoint> scheduleDayPoints);

    void deleteScheduleDayPointsById(ArrayList<Short> ids);

    List<ScheduleDayPoint> findAllScheduleDayPoints();

    void createScheduleTimePoints(List<ScheduleTimePoint> scheduleTimePoints);

    void deleteScheduleTimePoints(List<ScheduleTimePoint> scheduleTimePoints);

    //FOR WORK WITH INTERVIEWERS IN RECRUITMENT

    //FOR WORK WITH STUDENTS IN RECRUITMENT


    //TODO rewrite deleteAll() and getAll() (Olesia)
    int deleteAll();

    List<SchedulingSettings> getAll();
}
