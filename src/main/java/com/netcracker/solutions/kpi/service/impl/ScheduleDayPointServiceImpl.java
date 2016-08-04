package com.netcracker.solutions.kpi.service.impl;

import com.netcracker.solutions.kpi.persistence.dao.ScheduleDayPointDao;
import com.netcracker.solutions.kpi.service.ScheduleDayPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by olil0716 on 8/4/2016.
 */

@Service
public class ScheduleDayPointServiceImpl implements ScheduleDayPointService {
    @Autowired
    ScheduleDayPointDao scheduleDayPointDao;

    @Override
    public int deleteAll() {
        return scheduleDayPointDao.deleteAll();
    }
}
