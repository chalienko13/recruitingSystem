package com.netcracker.solutions.kpi.service;

import com.netcracker.solutions.kpi.persistence.model.*;

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

    ScheduleDayPoint findScheduleDayPoint(Short id);

    void createScheduleTimePoints(List<ScheduleTimePoint> scheduleTimePoints);

    void deleteScheduleTimePoints(List<ScheduleTimePoint> scheduleTimePoints);

    TimeType findTimeTypeByName(String name);

    //FOR WORK WITH RECRUITMENT PARAMETERS

    void addTimeInterviewTechAndSoft(Short timeInterviewTech, Short timeInterviewSoft, Long recruitmentId);

    //FOR WORK WITH INTERVIEWERS IN RECRUITMENT

    void addTechInterviewerForInterview(UserTime userTime);

    //FOR WORK WITH STUDENTS IN RECRUITMENT


    //TODO rewrite deleteAll() and getAll() (Olesia)
    int deleteAll();

    List<SchedulingSettings> getAll();
}
