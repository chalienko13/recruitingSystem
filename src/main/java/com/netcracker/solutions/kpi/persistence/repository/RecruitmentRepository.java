package com.netcracker.solutions.kpi.persistence.repository;

import com.netcracker.solutions.kpi.persistence.model.Recruitment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface RecruitmentRepository extends JpaRepository<Recruitment, Long> {

    @Transactional
    @Modifying
    @Query("update Recruitment rec set rec.timeInterviewTech = ?2, rec.timeInterviewSoft = ?3 where rec.id = ?1")
    void updateTimeInterviewTech(short recruitmentId, short timeInterviewTech, short timeInterviewSoft);
}
