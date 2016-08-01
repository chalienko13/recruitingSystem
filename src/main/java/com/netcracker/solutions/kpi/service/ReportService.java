package com.netcracker.solutions.kpi.service;

import com.netcracker.solutions.kpi.persistence.model.ReportInfo;
import com.netcracker.solutions.kpi.report.Report;

import java.util.Set;

/**
 * Created by Nikita on 24.04.2016.
 */
public interface ReportService {

    ReportInfo getByID(Long id);
    
    Report getReportOfApproved();
    
    Report getReportOfAnswers(Long idQuestion);

    ReportInfo getByTitle(String title);

    Set<ReportInfo> getAll();

}
