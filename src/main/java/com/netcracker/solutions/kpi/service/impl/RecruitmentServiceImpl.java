package com.netcracker.solutions.kpi.service.impl;

import com.netcracker.solutions.kpi.persistence.dao.RecruitmentDAO;
import com.netcracker.solutions.kpi.persistence.model.Recruitment;
import com.netcracker.solutions.kpi.persistence.repository.RecruitmentRepository;
import com.netcracker.solutions.kpi.service.RecruitmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class RecruitmentServiceImpl implements RecruitmentService {

    @Autowired
    private RecruitmentRepository recruitmentRepository;
    @Autowired
    private RecruitmentDAO recruitmentDAO;

    /*public RecruitmentServiceImpl(RecruitmentDAO recruitmentDAO) {
        this.recruitmentDAO = recruitmentDAO;
    }*/

    @Override
    public Recruitment getRecruitmentById(Long id) {
        return recruitmentRepository.findOne(id);
    }

/*    @Override
    public Recruitment getRecruitmentByName(String name) {
        return recruitmentDAO.getRecruitmentByName(name);
    }*/

    @Override
    public int updateRecruitment(Recruitment recruitment) {
        return null==recruitmentRepository.save(recruitment)?0:1;
    }

    @Override
    public void updateTimeInterviewTechAndSoft(Short timeInterviewTech, Short timeInterviewSoft, Long recruitmentId) {
        recruitmentRepository.updateTimeInterviewTechAndSoft(timeInterviewTech, timeInterviewSoft, recruitmentId);
    }

    @Override
    public boolean addRecruitment(Recruitment recruitment) {
        return recruitmentRepository.save(recruitment) == null;
    }

    @Override
    public List<Recruitment> getAll() {
        return recruitmentRepository.findAll();
    }

    @Override
    public Recruitment getCurrentRecruitmnet() {
        return recruitmentRepository.getCurrentRecruitmnet();
    }

    @Override
    public List<Recruitment> getAllSorted() {
        return recruitmentDAO.getAllSorted();
    }

    @Override
    public Recruitment getLastRecruitment() {
        return  recruitmentRepository.findLast();
    }
}
