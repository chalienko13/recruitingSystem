package com.netcracker.solutions.kpi.service;

import com.netcracker.solutions.kpi.persistence.model.ReportInfo;
import com.netcracker.solutions.kpi.report.Report;

import java.util.Set;

public interface ReportService {

    ReportInfo getByID(Long id);

    Report getReportOfApproved();

    Report getReportOfAnswers(Long idQuestion);

    ReportInfo getByTitle(String title);

    Set<ReportInfo> getAll();

}
