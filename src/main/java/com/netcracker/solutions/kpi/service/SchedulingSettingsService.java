package com.netcracker.solutions.kpi.service;

import com.netcracker.solutions.kpi.persistence.model.SchedulingSettings;

import java.util.List;

public interface SchedulingSettingsService {

    SchedulingSettings getById(Long id);

    int deleteAll();

    Long insertTimeRange(SchedulingSettings schedulingSettings);

    int updateTimeRange(SchedulingSettings schedulingSettings);

    int deleteTimeRange(Long id);

    List<SchedulingSettings> getAll();
}
