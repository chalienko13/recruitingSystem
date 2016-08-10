package com.netcracker.solutions.kpi.service;

import com.netcracker.solutions.kpi.persistence.model.Recruitment;

import java.util.List;

/**
 * @author Chalienko  22.04.2016.
 */
public interface RecruitmentService {

    Recruitment getRecruitmentById(Long id);

    Recruitment getRecruitmentByName(String name);

    int updateRecruitment(Recruitment recruitment);

    void updateTimeInterviewTechAndSoft(Short timeInterviewTech, Short timeInterviewSoft,Long recruitmentId);

    boolean addRecruitment(Recruitment recruitment);

    int deleteRecruitment(Recruitment recruitment);

    List<Recruitment> getAll();
    
    List<Recruitment> getAllSorted();
    
    Recruitment getCurrentRecruitmnet();

    Recruitment getLastRecruitment();
}
