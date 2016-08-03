package com.netcracker.solutions.kpi.service.impl;

import com.netcracker.solutions.kpi.persistence.dao.ScheduleTimePointDao;
import com.netcracker.solutions.kpi.persistence.model.ScheduleTimePoint;
import com.netcracker.solutions.kpi.persistence.model.User;
import com.netcracker.solutions.kpi.service.ScheduleTimePointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Service
public class ScheduleTimePointServiceImpl implements ScheduleTimePointService {

    @Autowired
    private ScheduleTimePointDao scheduleTimePointDao;

    @Override
    public int deleteAll() {
        return scheduleTimePointDao.deleteAll();
    }

    @Override
    public List<ScheduleTimePoint> getFinalTimePointByUserId(Long id) {
        return scheduleTimePointDao.getFinalTimePointByUserId(id);
    }

    @Override
    public List<ScheduleTimePoint> getAll() {
        return scheduleTimePointDao.getAll();
    }

    @Override
    public boolean isScheduleDatesExists() {
        return scheduleTimePointDao.isScheduleDatesExists();
    }

}
