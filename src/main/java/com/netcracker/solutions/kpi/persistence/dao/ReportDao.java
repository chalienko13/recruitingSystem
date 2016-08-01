package com.netcracker.solutions.kpi.persistence.dao;

import com.netcracker.solutions.kpi.persistence.model.FormAnswerVariant;
import com.netcracker.solutions.kpi.persistence.model.FormQuestion;
import com.netcracker.solutions.kpi.persistence.model.ReportInfo;
import com.netcracker.solutions.kpi.report.Line;

import java.util.List;
import java.util.Set;

/**
 * Created by Nikita on 24.04.2016.
 */
public interface ReportDao {
	ReportInfo getByID(Long id);

	ReportInfo getByTitle(String title);

	Set<ReportInfo> getAll();

	Long insertReport(ReportInfo report);

	int updateReport(ReportInfo report);

	int deleteReport(ReportInfo report);

	List<Line> extractWithMetaData(ReportInfo reportInfo);

	Line getAnswerVariantLine(ReportInfo reportInfo, FormQuestion question, FormAnswerVariant formAnswerVariant);
}
