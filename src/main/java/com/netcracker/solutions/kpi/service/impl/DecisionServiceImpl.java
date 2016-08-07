package com.netcracker.solutions.kpi.service.impl;

import com.netcracker.solutions.kpi.persistence.dao.DecisionDao;
import com.netcracker.solutions.kpi.persistence.model.Decision;
import com.netcracker.solutions.kpi.persistence.model.Status;
import com.netcracker.solutions.kpi.persistence.model.enums.StatusEnum;
import com.netcracker.solutions.kpi.service.DecisionService;
import com.netcracker.solutions.kpi.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DecisionServiceImpl implements DecisionService {

    @Autowired
    private DecisionDao decisionDao;

    @Autowired
    private StatusService statusService;

    @Override
    public Decision getByMarks(int softMark, int techMark) {
        return decisionDao.getByMarks(softMark, techMark);
    }

    @Override
    public List<Decision> getAll() {
        return decisionDao.getAll();
    }

    @Override
    public Status getStatusByFinalMark(int finalMark) {
        switch (finalMark) {
            case 3:
                return StatusEnum.APPROVED_TO_JOB.getStatus();
            case 2:
                return StatusEnum.APPROVED_TO_GENERAL_COURSES.getStatus();
            case 1:
                return StatusEnum.REJECTED.getStatus();
        }
        return null;
    }

    @Override
    public void updateDecisionMatrix(List<Decision> decisionMatrix) {
        decisionDao.updateDecisionMatrix(decisionMatrix);
    }


}
