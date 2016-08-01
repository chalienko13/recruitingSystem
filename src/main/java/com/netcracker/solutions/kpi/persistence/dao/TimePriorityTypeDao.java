package com.netcracker.solutions.kpi.persistence.dao;

import com.netcracker.solutions.kpi.persistence.model.TimePriorityType;

import java.util.List;

/**
 * @author Korzh
 */
public interface TimePriorityTypeDao {
    TimePriorityType getByID(Long id);

    TimePriorityType getByPriority(String priority);

	List<TimePriorityType> getAll();
}
