package com.netcracker.solutions.kpi.persistence.repository;

import com.netcracker.solutions.kpi.persistence.model.Recruitment;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

@Repository
public interface RecruitmentRepository extends JpaRepository<Recruitment, Long> {
    @Query(value = "SELECT * FROM recruitment ORDER BY id DESC LIMIT 1", nativeQuery = true)
    Recruitment findLast();

    @Query(value = "SELECT * FROM recruitment WHERE end_date > CURRENT_DATE", nativeQuery = true)
    Recruitment getCurrent();
}