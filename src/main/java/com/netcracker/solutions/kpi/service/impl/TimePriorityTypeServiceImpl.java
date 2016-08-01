package com.netcracker.solutions.kpi.service.impl;

import com.netcracker.solutions.kpi.persistence.dao.TimePriorityTypeDao;
import com.netcracker.solutions.kpi.persistence.model.TimePriorityType;
import com.netcracker.solutions.kpi.service.TimePriorityTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimePriorityTypeServiceImpl implements TimePriorityTypeService {

    @Autowired
    private TimePriorityTypeDao timePriorityTypeDao;

    @Override
    public TimePriorityType getByID(Long id) {
        return timePriorityTypeDao.getByID(id);
    }

    @Override
    public TimePriorityType getByPriority(String priority) {
        return timePriorityTypeDao.getByPriority(priority);
    }

	@Override
	public List<TimePriorityType> getAll() {
		return timePriorityTypeDao.getAll();
	}
}
