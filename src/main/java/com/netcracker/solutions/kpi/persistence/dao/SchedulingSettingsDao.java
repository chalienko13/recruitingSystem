package com.netcracker.solutions.kpi.persistence.dao;

import com.netcracker.solutions.kpi.persistence.model.SchedulingSettings;

import java.util.List;

/**
 * @author Korzh
 */
//TODO delete (Olesia)
public interface SchedulingSettingsDao {

    SchedulingSettings getById(Long id);

    int deleteAll();

    List<SchedulingSettings> getAll();
}
