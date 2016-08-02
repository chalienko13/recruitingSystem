package com.netcracker.solutions.kpi.persistence.dao;

import java.sql.Connection;
import java.util.List;

/**
 * Created by Алексей on 21.04.2016.
 */
public interface InterviewDao {
    Interview getById(Long id);

    List<Interview> getByInterviewer(User user);

    List<Interview> getByApplicationForm(ApplicationForm applicationForm);

    Interview getByApplicationFormAndInterviewerRoleId(ApplicationForm applicationForm, Long interviewerRoleId );

    Long insertInterview(Interview interview, ApplicationForm applicationForm, User interviewer, Role role);

    Long insertInterview(Interview interview, Connection connection);

    int updateInterview(Interview interview);

    int deleteInterview(Interview interview);

    List<Interview> getAll();

    boolean haveNonAdequateMark(Long applicationFormID, Long interviewerId);

    boolean haveNonAdequateMarkForAdmin (Long applicationFormID);

	boolean isFormAssigned(ApplicationForm applicationForm, User interviewer);

	int updateInterview(Interview interview, Connection connection);
}
