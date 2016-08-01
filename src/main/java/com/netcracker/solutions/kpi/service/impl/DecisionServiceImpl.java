package com.netcracker.solutions.kpi.service.impl;

import com.netcracker.solutions.kpi.persistence.dao.DecisionDao;
import com.netcracker.solutions.kpi.persistence.model.Decision;
import com.netcracker.solutions.kpi.persistence.model.Status;
import com.netcracker.solutions.kpi.persistence.model.enums.StatusEnum;
import com.netcracker.solutions.kpi.service.DecisionService;
import com.netcracker.solutions.kpi.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DecisionServiceImpl implements DecisionService {

    @Autowired
    private DecisionDao decisionDao;

    @Autowired
    private StatusService statusService;

    /*public DecisionServiceImpl(DecisionDao decisionDao) {
        this.decisionDao = decisionDao;
        statusService = ServiceFactory.getStatusService();
    }*/

    @Override
    public Decision getByMarks(int softMark, int techMark) {
        return decisionDao.getByMarks(softMark, techMark);
    }

    @Override
    public Long insertDecision(Decision decision) {
        return decisionDao.insertDecision(decision);
    }

    @Override
    public int updateDecision(Decision decision) {
        return decisionDao.updateDecision(decision);
    }

    @Override
    public int deleteDecision(Decision decision) {
        return decisionDao.deleteDecision(decision);
    }

    @Override
    public List<Decision> getAll() {
        return decisionDao.getAll();
    }

    @Override
    public Status getStatusByFinalMark(int finalMark) {
        switch (finalMark) {
            case 3:
                return statusService.getStatusById(StatusEnum.APPROVED_TO_JOB.getId());
            case 2:
                return statusService.getStatusById(StatusEnum.APPROVED_TO_GENERAL_COURSES.getId());
            case 1:
                return statusService.getStatusById(StatusEnum.REJECTED.getId());
        }
        return null;
    }

    @Override
    public void updateDecisionMatrix(List<Decision> decisionMatrix) {
        decisionDao.updateDecisionMatrix(decisionMatrix);
    }


}
