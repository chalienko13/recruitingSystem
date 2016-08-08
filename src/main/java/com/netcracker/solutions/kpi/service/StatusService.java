package com.netcracker.solutions.kpi.service;

import com.netcracker.solutions.kpi.persistence.model.Status;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StatusService {

    Status getStatusById(Long id);

    /* int insertStatus(Status status);

     int updateStatus(Status status);

     int deleteStatus(Status status);
 */
    List<Status> getAllStatuses();

    Status getByName(String name);
}
