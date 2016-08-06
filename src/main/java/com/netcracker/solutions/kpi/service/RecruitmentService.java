package com.netcracker.solutions.kpi.service;

import com.netcracker.solutions.kpi.persistence.model.Recruitment;

import java.util.List;

public interface RecruitmentService {

    Recruitment getRecruitmentById(Long id);

    int updateRecruitment(Recruitment recruitment);

    boolean addRecruitment(Recruitment recruitment);

    List<Recruitment> getAll();

    List<Recruitment> getAllSorted();

    Recruitment getCurrentRecruitmnet();

    Recruitment getLastRecruitment();
}
