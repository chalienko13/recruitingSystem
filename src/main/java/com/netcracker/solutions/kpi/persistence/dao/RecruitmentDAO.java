package com.netcracker.solutions.kpi.persistence.dao;

import java.util.List;

/**
 * Created by Vova on 21.04.2016.
 */
public interface RecruitmentDAO  {

    Recruitment getRecruitmentById(Long id);

    Recruitment getRecruitmentByName(String name);

    int updateRecruitment(Recruitment recruitment);

    boolean addRecruitment(Recruitment recruitment);

    int deleteRecruitment(Recruitment recruitment);

    List<Recruitment> getAll();

	Recruitment getCurrentRecruitmnet();

	List<Recruitment> getAllSorted();

    Recruitment getLastRecruitment();


}
