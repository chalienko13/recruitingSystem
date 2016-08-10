package com.netcracker.solutions.kpi.service.impl;

import com.netcracker.solutions.kpi.persistence.dao.ScheduleTimePointDao;
import com.netcracker.solutions.kpi.persistence.model.ScheduleTimePoint;
import com.netcracker.solutions.kpi.persistence.model.User;
import com.netcracker.solutions.kpi.service.ScheduleTimePointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ScheduleTimePointServiceImpl implements ScheduleTimePointService {

    @Autowired
    private ScheduleTimePointDao scheduleTimePointDao;

    @Override
    public int deleteAll() {
        return scheduleTimePointDao.deleteAll();
    }

    @Override
    public ScheduleTimePoint getScheduleTimePointById(Long id) {
        return scheduleTimePointDao.getFinalTimePointById(id);
    }

    @Override
    public List<ScheduleTimePoint> getFinalTimePointByUserId(Long id) {
        return scheduleTimePointDao.getFinalTimePointByUserId(id);
    }

    @Override
    public int[] batchInsert(List<Timestamp> timestaps) {
        return scheduleTimePointDao.batchInsert(timestaps);
    }

    @Override
    public int deleteUserTimeFinal(User user, ScheduleTimePoint scheduleTimePoint) {
        return scheduleTimePointDao.deleteUserTimeFinal(user, scheduleTimePoint);
    }

    @Override
    public ScheduleTimePoint getScheduleTimePointByTimepoint(Timestamp timestamp) {
        return scheduleTimePointDao.getScheduleTimePointByTimepoint(timestamp);
    }

    @Override
    public List<ScheduleTimePoint> getAll() {
        return Collections.emptyList();/*scheduleTimePointDao.getAll()*/
    }

    @Override
    public boolean isScheduleDatesExists() {
        return scheduleTimePointDao.isScheduleDatesExists();
    }

    @Override
    public boolean isScheduleExists() {
        return scheduleTimePointDao.isScheduleExists();
    }

    @Override
    public Map<Long, Long> getUsersNumberInFinalTimePoint(Timestamp timePoint) {
        return scheduleTimePointDao.getUsersNumberInFinalTimePoint(timePoint);
    }

    @Override
    public Long addUserToTimepoint(User user, ScheduleTimePoint timePoint) {
        return scheduleTimePointDao.addUserToTimepoint(user, timePoint);
    }
}
