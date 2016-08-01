package com.netcracker.solutions.kpi.service;

import com.netcracker.solutions.kpi.persistence.model.TimePriorityType;

import java.util.List;

/**
 * Created by Chalienko on 21.04.2016.
 */
public interface TimePriorityTypeService {
    TimePriorityType getByID(Long id);

    TimePriorityType getByPriority(String priority);

	List<TimePriorityType> getAll();
}
