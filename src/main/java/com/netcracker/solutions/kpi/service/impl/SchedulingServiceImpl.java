package com.netcracker.solutions.kpi.service.impl;

import com.netcracker.solutions.kpi.persistence.dao.SchedulingSettingsDao;
import com.netcracker.solutions.kpi.persistence.model.*;
import com.netcracker.solutions.kpi.persistence.repository.*;
import com.netcracker.solutions.kpi.service.RecruitmentService;
import com.netcracker.solutions.kpi.service.SchedulingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class SchedulingServiceImpl implements SchedulingService {

    @Autowired
    private ScheduleDayPointRepository scheduleDayPointRepository;

    @Autowired
    private ScheduleTimePointRepository scheduleTimePointRepository;

    @Autowired
    private UserTimeRepository userTimeRepository;

    @Autowired
    private TimeTypeRepository timeTypeRepository;

    @Autowired
    private RecruitmentService recruitmentService;

    //TODO delete this later (Olesia)
    @Autowired
    private SchedulingSettingsDao schedulingSettingsDao;

    //FOR WORK WITH SCHEDULE

        //Day
    @Override
    @Transactional
    public void createScheduleDayPoints(List<ScheduleDayPoint> scheduleDayPoints) {
        scheduleDayPointRepository.save(scheduleDayPoints);
    }

    @Transactional
    @Override
    public void deleteScheduleDayPoints(List<ScheduleDayPoint> scheduleDayPoints) {
        scheduleDayPointRepository.delete(scheduleDayPoints);
    }

    @Transactional
    @Override
    public void deleteScheduleDayPointsById(ArrayList<Short> ids) {
        scheduleDayPointRepository.deleteByIdIn(ids);
    }

    @Override
    public ScheduleDayPoint findScheduleDayPoint(Short id) {
        return scheduleDayPointRepository.findOne(id);
    }

    @Override
    public List<ScheduleDayPoint> findAllScheduleDayPoints() {
        return scheduleDayPointRepository.findAll();
    }

        //Time

    @Transactional
    @Override
    public void createScheduleTimePoints(List<ScheduleTimePoint> scheduleTimePoints) {
        scheduleTimePointRepository.save(scheduleTimePoints);
    }

    @Transactional
    @Override
    public void deleteScheduleTimePoints(List<ScheduleTimePoint> scheduleTimePoints) {
        scheduleTimePointRepository.delete(scheduleTimePoints);
    }

        //Time type

    public TimeType findTimeTypeByName(String name) {
        return timeTypeRepository.findOneByType(name);
    }

    //FOR WORK WITH RECRUITMENT PARAMETERS

    @Override
    public void addTimeInterviewTechAndSoft(Short timeInterviewTech, Short timeInterviewSoft, Long recruitmentId) {
        recruitmentService.updateTimeInterviewTechAndSoft(timeInterviewTech, timeInterviewSoft, recruitmentId);
    }

    //FOR WORK WITH INTERVIEWERS IN RECRUITMENT

    @Override
    public void addTechInterviewerForInterview(UserTime userTime) {
        userTimeRepository.save(userTime);
    }

    //TODO Rewrite (Olesia)
    @Override
    public int deleteAll(){
        return schedulingSettingsDao.deleteAll();
    }

    @Override
    public List<SchedulingSettings> getAll() {
        return schedulingSettingsDao.getAll();
    }
}
