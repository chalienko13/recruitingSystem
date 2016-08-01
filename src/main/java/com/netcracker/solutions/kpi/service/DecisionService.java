package com.netcracker.solutions.kpi.service;

import com.netcracker.solutions.kpi.persistence.model.Decision;
import com.netcracker.solutions.kpi.persistence.model.Status;

import java.util.List;

/**
 * Created by Chalienko on 21.04.2016.
 */
public interface DecisionService {
	
	Decision getByMarks(int softMark, int techMark);

    Long insertDecision(Decision decision);

    int updateDecision(Decision decision);

    int deleteDecision(Decision decision);
    
    List<Decision> getAll();
	
    Status getStatusByFinalMark(int finalMark);
    
    void updateDecisionMatrix(List<Decision> decisionMatrix);
}
