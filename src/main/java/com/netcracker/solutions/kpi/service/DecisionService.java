package com.netcracker.solutions.kpi.service;

import com.netcracker.solutions.kpi.persistence.model.Decision;
import com.netcracker.solutions.kpi.persistence.model.Status;

import java.util.List;

public interface DecisionService {

    Decision getByMarks(int softMark, int techMark);

    List<Decision> getAll();

    Status getStatusByFinalMark(int finalMark);

    void updateDecisionMatrix(List<Decision> decisionMatrix);
}
