package com.netcracker.solutions.kpi.service.impl;

import com.netcracker.solutions.kpi.persistence.dao.ReportDao;
import com.netcracker.solutions.kpi.persistence.model.ReportInfo;
import com.netcracker.solutions.kpi.persistence.model.enums.ReportTypeEnum;
import com.netcracker.solutions.kpi.report.Line;
import com.netcracker.solutions.kpi.report.Report;
import com.netcracker.solutions.kpi.service.FormAnswerVariantService;
import com.netcracker.solutions.kpi.service.FormQuestionService;
import com.netcracker.solutions.kpi.service.RecruitmentService;
import com.netcracker.solutions.kpi.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private FormQuestionService formQuestionService;// = ServiceFactory.getFormQuestionService();

    @Autowired
    private RecruitmentService recruitmentService;// = ServiceFactory.getRecruitmentService();

    @Autowired
    private FormAnswerVariantService variantService;// = ServiceFactory.getFormAnswerVariantService();

    @Autowired
	private ReportDao reportDao;

	/*public ReportServiceImpl(ReportDao reportDao) {
		this.reportDao = reportDao;
	}*/

	@Override
	public ReportInfo getByID(Long id) {
		return reportDao.getByID(id);
	}

	@Override
	public ReportInfo getByTitle(String title) {
		return reportDao.getByTitle(title);
	}

	@Override
	public Set<ReportInfo> getAll() {
		return reportDao.getAll();
	}

	@Override
	public Report getReportOfApproved() {
		ReportInfo reportInfo = getByID(ReportTypeEnum.APPROVED.getId());
		Report report = new Report(reportInfo.getTitle());
		List<Line> lines = reportDao.extractWithMetaData(reportInfo);
		if (lines.size() > 0) {
			report.setHeader(lines.get(0));
			report.setLines(lines.subList(1, lines.size()));
		}
		return report;
	}

	@Override
	public Report getReportOfAnswers(Long idQuestion) {
		ReportInfo reportInfo = getByID(ReportTypeEnum.ANSWERS.getId());
		Report report = new Report(reportInfo.getTitle());
		List<Recruitment> recruitments = recruitmentService.getAllSorted();
		report.getHeader().addCell("Recruitment");
		for (Recruitment recruitment : recruitments) {
			report.getHeader().addCell(recruitment.getName());
		}
		FormQuestion formQuestion = formQuestionService.getById(idQuestion);
		if (formQuestion != null) {
			List<FormAnswerVariant> variants = variantService.getAnswerVariantsByQuestion(formQuestion);
			for (FormAnswerVariant variant : variants) {
				Line line = reportDao.getAnswerVariantLine(reportInfo, formQuestion, variant);
				line.addFirstCell(variant.getAnswer());
				report.addRow(line);
			}
		}
		return report;
	}

}
