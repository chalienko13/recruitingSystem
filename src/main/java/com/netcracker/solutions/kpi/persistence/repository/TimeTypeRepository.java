package com.netcracker.solutions.kpi.persistence.repository;

import com.netcracker.solutions.kpi.persistence.model.TimeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by olil0716 on 8/10/2016.
 */
@Repository
public interface TimeTypeRepository extends JpaRepository<TimeType, Short> {

    @Transactional
    @Modifying
    TimeType findOneByType(String type);
}
