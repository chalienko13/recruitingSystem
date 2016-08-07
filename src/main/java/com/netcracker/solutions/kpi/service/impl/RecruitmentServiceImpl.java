package com.netcracker.solutions.kpi.service.impl;

import com.netcracker.solutions.kpi.persistence.dao.RecruitmentDAO;
import com.netcracker.solutions.kpi.persistence.model.Recruitment;
import com.netcracker.solutions.kpi.service.RecruitmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RecruitmentServiceImpl implements RecruitmentService {

    @Autowired
    private RecruitmentDAO recruitmentDAO;

    @Override
    public Recruitment getRecruitmentById(Long id) {
        return recruitmentDAO.getRecruitmentById(id);
    }

    @Override
    public int updateRecruitment(Recruitment recruitment) {
        return recruitmentDAO.updateRecruitment(recruitment);
    }

    @Override
    public boolean addRecruitment(Recruitment recruitment) {
        return recruitmentDAO.addRecruitment(recruitment);
    }

    @Override
    public List<Recruitment> getAll() {
        return recruitmentDAO.getAll();
    }

    @Override
    public Recruitment getCurrentRecruitmnet() {
        return recruitmentDAO.getCurrentRecruitmnet();
    }

    @Override
    public List<Recruitment> getAllSorted() {
        return recruitmentDAO.getAllSorted();
    }

    @Override
    public Recruitment getLastRecruitment() {
        return recruitmentDAO.getLastRecruitment();
    }
}
