package com.netcracker.solutions.kpi.service.impl;

import com.netcracker.solutions.kpi.persistence.dao.ApplicationFormDao;
import com.netcracker.solutions.kpi.persistence.dao.DataSourceSingleton;
import com.netcracker.solutions.kpi.persistence.dao.FormAnswerDao;
import com.netcracker.solutions.kpi.persistence.model.*;
import com.netcracker.solutions.kpi.persistence.repository.ApplicationFormRepository;
import com.netcracker.solutions.kpi.service.ApplicationFormService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Service
public class ApplicationFormServiceImpl implements ApplicationFormService {

	private static Logger log = LoggerFactory.getLogger(ApplicationFormServiceImpl.class);

    @Autowired
	private ApplicationFormDao applicationFormDao;

    @Autowired
    private ApplicationFormRepository applicationFormRepository;

    @Autowired
    private FormAnswerDao formAnswerDao;

	@Override
	public ApplicationForm getApplicationFormById(Long id) {
		return applicationFormRepository.findOne(id);
	}

	@Override
	public List<ApplicationForm> getByUserId(Long id) {
		return applicationFormDao.getByUserId(id);
	}

	@Override
	public List<ApplicationForm> getByStatus(String status) {
		return applicationFormDao.getByStatus(status);
	}

	@Override
	public List<ApplicationForm> getByState(boolean state) {
		return applicationFormDao.getByState(state);
	}

	@Override
	public Long getCountRejectedAppForm() {
		return applicationFormDao.getCountRejectedAppForm();
	}

	@Override
	public Long getCountToWorkAppForm() {
		return applicationFormDao.getCountToWorkAppForm();
	}

	@Override
	public Long getCountGeneralAppForm() {
		return applicationFormDao.getCountGeneralAppForm();
	}

	@Override
	public Long getCountAdvancedAppForm() {
		return applicationFormDao.getCountAdvancedAppForm();
	}

	@Override
	public void deleteApplicationForm(ApplicationForm applicationForm) {
        applicationFormRepository.delete(applicationForm);
	}

	@Override
	public boolean insertApplicationForm(ApplicationForm applicationForm) {
		try (Connection connection = DataSourceSingleton.getInstance().getConnection()) {
			connection.setAutoCommit(false);
			Long generatedId = applicationFormDao.insertApplicationForm(applicationForm, connection);
			applicationForm.setId(generatedId);
			for (FormAnswer formAnswer : applicationForm.getAnswers()) {
				formAnswerDao.insertFormAnswerForApplicationForm(formAnswer, formAnswer.getFormQuestion(),
						 applicationForm, connection);
			}
			connection.commit();
		} catch (SQLException e) {
			if (log.isWarnEnabled()) {
				log.error("Cannot insert Application form",e);
			}
			return false;
		}
		return true;
	}

	@Override
	public List<ApplicationForm> getAll() {
		return applicationFormRepository.findAll();
	}

	@Override
	public void updateApplicationForm(ApplicationForm applicationForm) {
        applicationFormRepository.save(applicationForm);
	}

	@Override
	public ApplicationForm getCurrentApplicationFormByUserId(Long id) {
		return applicationFormDao.getCurrentApplicationFormByUserId(id);
	}

	@Override
	public List<ApplicationForm> getOldApplicationFormsByUserId(Long id) {
		return applicationFormDao.getOldApplicationFormsByUserId(id);
	}

	@Override
	public ApplicationForm getLastApplicationFormByUserId(Long id) {
		return applicationFormDao.getLastApplicationFormByUserId(id);
	}

	@Override
	public List<ApplicationForm> getByInterviewer(User interviewer) {
		return applicationFormDao.getByInterviewer(interviewer);
	}

	@Override
	public boolean isAssignedForThisRole(ApplicationForm applicationForm, Role role) {
		return applicationFormDao.isAssignedForThisRole(applicationForm, role);
	}

	@Override
	public int changeCurrentsAppFormStatus(Long fromIdStatus, Long toIdStatus) {
		return applicationFormDao.changeCurrentsAppFormStatus(fromIdStatus, toIdStatus);
	}

	@Override
	public Long getCountRecruitmentStudents(Long id) {
		return applicationFormDao.getCountRecruitmentStudents(id);
	}

	@Override
	public Long getCountApprovedStudentsByRecruitmentId(Short id){
		return applicationFormDao.getApprovedStudentsByRecruitmentId(id);
	}

	@Override
	public List<ApplicationForm> getCurrentsApplicationForms(Long fromRow, Long rowsNum, Long sortingCol, boolean increase) {
		return applicationFormDao.getCurrentApplicationForms(fromRow, rowsNum, sortingCol, increase);
	}

	@Override
	public List<ApplicationForm> getApplicationFormsSorted(Long fromRow, Long rowsNum, Long sortingCol, boolean increase) {
		return applicationFormDao.getApplicationFormsSorted(fromRow, rowsNum, sortingCol, increase);
	}

	@Override
	public List<ApplicationForm> getCurrentsApplicationFormsFiltered(Long fromRow, Long rowsNum, Long sortingCol,
																	 boolean increase, List<FormQuestion> questions,
																	 List<String> statuses, boolean isActive) {
		return applicationFormDao.getCurrentApplicationFormsFiltered(fromRow, rowsNum, sortingCol, increase, questions, statuses, isActive);
	}
	public List<ApplicationForm> getSearchAppFormByNameFromToRows(String lastName, Long fromRows, Long rowsNum){
		return applicationFormDao.getSearchAppFormByNameFromToRows(lastName, fromRows, rowsNum);
	}
	@Override
	public Long getCountInReviewAppForm() {
		return applicationFormDao.getCountInReviewAppForm();
	}

	@Override
	public List<ApplicationForm> getCurrentApplicationForms() {
		return applicationFormDao.getCurrentApplicationForms();
	}

	@Override
	public List<ApplicationForm> getByStatusAndRecruitment(Status status, Recruitment recruitment) {
		return applicationFormDao.getByStatusAndRecruitment(status, recruitment);
	}

	@Override
	public List<ApplicationForm> getByRecruitment(Recruitment recruitment) {
		return applicationFormDao.getByRecruitment(recruitment);
	}

	@Override
	public List<ApplicationForm> getRejectedAfterInterview(Recruitment recruitment) {
		return applicationFormDao.getRejectedAfterInterview(recruitment);
	}

	@Override
	public Long getCountApprovedAppForm() {
		return applicationFormDao.getCountApprovedAppForm();
	}
	
	@Override
	public boolean updateApplicationFormWithAnswers(ApplicationForm applicationForm) {
		try (Connection connection = DataSourceSingleton.getInstance().getConnection()) {
			connection.setAutoCommit(false);
			applicationFormDao.updateApplicationForm(applicationForm, connection);
			formAnswerDao.deleteNotPresented(applicationForm.getAnswers(), applicationForm, connection);
			for (FormAnswer formAnswer : applicationForm.getAnswers()) {
				if (formAnswer.getId() == null) {
					formAnswerDao.insertFormAnswerForApplicationForm(formAnswer, formAnswer.getFormQuestion(),
							applicationForm, connection);
				} else {
					formAnswerDao.updateFormAnswer(formAnswer);
				}
			}
			connection.commit();
		} catch (SQLException e) {
			if (log.isWarnEnabled()) {
				log.error("Cannot update Application form", e);
			}
			return false;
		}
		return true;
	}
}
