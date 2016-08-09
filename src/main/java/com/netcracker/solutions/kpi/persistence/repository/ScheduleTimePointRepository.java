package com.netcracker.solutions.kpi.persistence.repository;


import com.netcracker.solutions.kpi.persistence.model.ScheduleTimePoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.jdo.annotations.Transactional;
import java.util.ArrayList;

@Repository
public interface ScheduleTimePointRepository extends JpaRepository<ScheduleTimePoint, Long>{
}
