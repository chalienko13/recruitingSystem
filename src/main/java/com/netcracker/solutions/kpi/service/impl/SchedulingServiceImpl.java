package com.netcracker.solutions.kpi.service.impl;

import com.netcracker.solutions.kpi.persistence.dao.SchedulingSettingsDao;
import com.netcracker.solutions.kpi.persistence.model.ScheduleDayPoint;
import com.netcracker.solutions.kpi.persistence.model.ScheduleTimePoint;
import com.netcracker.solutions.kpi.persistence.model.SchedulingSettings;
import com.netcracker.solutions.kpi.persistence.repository.ScheduleDayPointRepository;
import com.netcracker.solutions.kpi.persistence.repository.ScheduleTimePointRepository;
import com.netcracker.solutions.kpi.service.SchedulingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SchedulingServiceImpl implements SchedulingService {

    @Autowired
    private ScheduleDayPointRepository scheduleDayPointRepository;

    @Autowired
    private ScheduleTimePointRepository scheduleTimePointRepository;

    //TODO delete this later (Olesia)
    @Autowired
    private SchedulingSettingsDao schedulingSettingsDao;

    //FOR WORK WITH SCHEDULE
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
    public void deleteScheduleDayPointsById(Short id) {
        scheduleDayPointRepository.deleteById(id);
    }

    @Override
    public List<ScheduleDayPoint> findAllScheduleDayPoints() {
        return scheduleDayPointRepository.findAll();
    }

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
