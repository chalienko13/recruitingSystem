package com.netcracker.solutions.kpi.persistence.repository;

import com.netcracker.solutions.kpi.persistence.model.ScheduleDayPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.jdo.annotations.*;
import java.util.ArrayList;

/**
 * Created by olil0716 on 8/8/2016.
 */
@Repository
public interface ScheduleDayPointRepository extends JpaRepository<ScheduleDayPoint, Short> {
    @Transactional
    void deleteByIdIn(ArrayList<Short> ids);
}
