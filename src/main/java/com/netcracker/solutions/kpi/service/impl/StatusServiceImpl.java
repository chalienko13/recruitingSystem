package com.netcracker.solutions.kpi.service.impl;

import com.netcracker.solutions.kpi.persistence.dao.StatusDao;
import com.netcracker.solutions.kpi.persistence.model.Status;
import com.netcracker.solutions.kpi.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusServiceImpl implements StatusService {

    @Autowired
    private StatusDao statusDao;

    /*public StatusServiceImpl(StatusDao statusDao) {
        this.statusDao = statusDao;
    }*/

    @Override
    public Status getStatusById(Long id) {
        return statusDao.getById(id);
    }

    @Override
    public int insertStatus(Status status) {
        return statusDao.insertStatus(status);
    }

    @Override
    public int updateStatus(Status status) {
      return  statusDao.updateStatus(status);
    }

    @Override
    public int deleteStatus(Status status) {
        return statusDao.deleteStatus(status);
    }

    @Override
    public List<Status> getAllStatuses() {
        return statusDao.getAllStatuses();
    }

    @Override
    public Status getByName(String name) {
        return statusDao.getByName(name);
    }
}
