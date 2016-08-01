package com.netcracker.solutions.kpi.service;

import com.netcracker.solutions.kpi.persistence.model.*;

import java.util.List;

/**
 * Created by Chalienko on 21.04.2016.
 */
public interface InterviewService {

	Interview getById(Long id);

	List<Interview> getByInterviewer(User user);

	List<Interview> getByApplicationForm(ApplicationForm applicationForm);

	Interview getByApplicationFormAndInterviewerRoleId(ApplicationForm applicationForm, Long interviewerRoleId );

	Long insertInterview(Interview interview, ApplicationForm applicationForm, User interviewer, Role role);

	boolean insertInterviewWithAnswers(Interview interview, List<FormAnswer> formAnswers);

	boolean updateInterview(Interview interview);

	int deleteInterview(Interview interview);

	List<Interview> getAll();

	boolean haveNonAdequateMark(Long applicationFormID, Long interviewerId);

	boolean haveNonAdequateMarkForAdmin (Long applicationFormID);
	
	boolean isFormAssigned(ApplicationForm applicationForm, User interviewer);
	
	public void assignStudent(ApplicationForm applicationForm, User interviewer, Role role);
}
