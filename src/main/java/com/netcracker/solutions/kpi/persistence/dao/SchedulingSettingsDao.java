package com.netcracker.solutions.kpi.persistence.dao;

import com.netcracker.solutions.kpi.persistence.model.SchedulingSettings;

import java.util.List;

/**
 * @author Korzh
 */
public interface SchedulingSettingsDao {

    SchedulingSettings getById(Long id);

    int deleteAll();

    Long insertTimeRange(SchedulingSettings schedulingSettings);

    int updateTimeRange(SchedulingSettings schedulingSettings);

    int deleteTimeRange(Long id);

    List<SchedulingSettings> getAll();
}
