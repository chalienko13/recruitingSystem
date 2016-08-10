package com.netcracker.solutions.kpi.persistence.repository;

import com.netcracker.solutions.kpi.persistence.model.Recruitment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
