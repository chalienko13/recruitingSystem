package com.netcracker.solutions.kpi.persistence.repository;

import com.netcracker.solutions.kpi.persistence.model.Recruitment;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface RecruitmentRepository extends JpaRepository<Recruitment, Long> {

    @Transactional
    @Modifying
    @Query("update Recruitment rec set rec.timeInterviewTech = ?1, rec.timeInterviewSoft = ?2 " +
            "where rec.id = ?3")
    void updateTimeInterviewTechAndSoft( short timeInterviewTech,
                                         short timeInterviewSoft,
                                         long recruitmentId);
}
    @Query(value = "SELECT * FROM recruitment ORDER BY id DESC LIMIT 1", nativeQuery = true)
    Recruitment findLast();

    @Query(value = "SELECT * FROM recruitment WHERE end_date > CURRENT_DATE", nativeQuery = true)
    Recruitment getCurrent();
}