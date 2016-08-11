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
    @Query("update Recruitment rec set rec.timeInterviewTech = ?1, rec.timeInterviewSoft = ?2 " +
            "where rec.id = ?3")
    void updateTimeInterviewTechAndSoft(short timeInterviewTech,
                                        short timeInterviewSoft,
                                        long recruitmentId);

    @Query(value = "SELECT * FROM recruitment ORDER BY id DESC LIMIT 1", nativeQuery = true)
    Recruitment findLast();

    @Query(value = "SELECT r.id, r.name,r.start_date,r.end_date,\n" +
            "r.max_general_group, r.max_advanced_group, r.registration_deadline, r.schedule_choices_deadline,\n" +
            " r.schedule_choices_deadline, r.students_on_interview  ,r.time_interview_soft, r.time_interview_tech,\n" +
            "r.number_tech_interviewers, r.number_soft_interviewers,  r.scheduling_status, r.number_of_days,  \n" +
            "ss.title FROM \"recruitment\" r JOIN scheduling_status ss on (ss.id = r.scheduling_status) \n" +
            "WHERE r.end_date > CURRENT_DATE;", nativeQuery = true)
    Recruitment getCurrentRecruitmnet();


    @Query(value = "SELECT * FROM recruitment WHERE end_date > CURRENT_DATE", nativeQuery = true)
    Recruitment getCurrent();
}
