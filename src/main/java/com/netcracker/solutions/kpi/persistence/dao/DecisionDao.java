package com.netcracker.solutions.kpi.persistence.dao;

import com.netcracker.solutions.kpi.persistence.model.Decision;

import java.util.List;

/**
 * Created by Алексей on 22.04.2016.
 */
public interface DecisionDao {
	Decision getByMarks(int softMark, int techMark);

	Long insertDecision(Decision decision);

	int updateDecision(Decision decision);

	int deleteDecision(Decision decision);

	List<Decision> getAll();

	int[] updateDecisionMatrix(List<Decision> decisionMatrix);
}
