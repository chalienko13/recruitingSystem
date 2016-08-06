package com.netcracker.solutions.kpi.service.impl;

import com.netcracker.solutions.kpi.persistence.dao.SchedulingSettingsDao;
import com.netcracker.solutions.kpi.persistence.model.SchedulingSettings;
import com.netcracker.solutions.kpi.service.SchedulingSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchedulingSettingsServiceImpl implements SchedulingSettingsService {

    @Autowired
    private SchedulingSettingsDao schedulingSettingsDao;

    @Override
    public int deleteAll() {
        return schedulingSettingsDao.deleteAll();
    }

    @Override
    public SchedulingSettings getById(Long id) {
        return schedulingSettingsDao.getById(id);
    }

    @Override
    public Long insertTimeRange(SchedulingSettings schedulingSettings) {
        return schedulingSettingsDao.insertTimeRange(schedulingSettings);
    }

    @Override
    public int updateTimeRange(SchedulingSettings schedulingSettings) {
        return schedulingSettingsDao.updateTimeRange(schedulingSettings);
    }

    @Override
    public int deleteTimeRange(Long id) {
        return schedulingSettingsDao.deleteTimeRange(id);
    }

    @Override
    public List<SchedulingSettings> getAll() {
        return schedulingSettingsDao.getAll();
    }
}
