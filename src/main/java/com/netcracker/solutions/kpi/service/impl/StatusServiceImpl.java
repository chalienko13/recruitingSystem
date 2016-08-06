package com.netcracker.solutions.kpi.service.impl;

import com.netcracker.solutions.kpi.persistence.model.Status;
import com.netcracker.solutions.kpi.persistence.repository.StatusRepository;
import com.netcracker.solutions.kpi.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusServiceImpl implements StatusService {
    @Autowired
    private StatusRepository statusRepository;

    @Override
    public Status getStatusById(Long id) {
        return statusRepository.getOne(id);
    }

    @Override
    public List<Status> getAllStatuses() {
        return statusRepository.findAll();
    }

    @Override
    public Status getByName(String name) {
        return statusRepository.getByTitle(name);
    }
}
