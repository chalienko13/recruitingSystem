package com.netcracker.solutions.kpi.service;

import com.netcracker.solutions.kpi.persistence.model.*;

import java.util.List;

/**
 * Created by Chalienko on 21.04.2016.
 */
public interface ApplicationFormService {

    ApplicationForm getApplicationFormById(Long id);

    ApplicationForm getCurrentApplicationFormByUserId(Long id);

    List<ApplicationForm> getOldApplicationFormsByUserId(Long id);

    ApplicationForm getLastApplicationFormByUserId(Long id);

    List<ApplicationForm> getByUserId(Long id);

    List<ApplicationForm> getByStatus(String status);

    List<ApplicationForm> getByState(boolean state);

    Long getCountRejectedAppForm();

    Long getCountToWorkAppForm();

    Long getCountGeneralAppForm();

    Long getCountAdvancedAppForm();

    Long getCountApprovedAppForm();

    int deleteApplicationForm(ApplicationForm applicationForm);

    boolean insertApplicationForm(ApplicationForm applicationForm);

    int updateApplicationForm(ApplicationForm applicationForm);

    List<ApplicationForm> getAll();

    List<ApplicationForm> getByInterviewer(User interviewer);

    boolean isAssignedForThisRole(ApplicationForm applicationForm, Role role);

    int changeCurrentsAppFormStatus(Long fromIdStatus, Long toIdStatus);

    Long getCountRecruitmentStudents(Long id);

    Long getCountApprovedStudentsByRecruitmentId(Long id);

    List<ApplicationForm> getCurrentsApplicationForms(Long fromRow, Long rowsNum, Long sortingCol, boolean increase);

    List<ApplicationForm> getApplicationFormsSorted(Long fromRow, Long rowsNum, Long sortingCol, boolean increase);

    List<ApplicationForm> getSearchAppFormByNameFromToRows(String lastName, Long fromRows, Long rowsNum);

    List<ApplicationForm> getCurrentsApplicationFormsFiltered(Long fromRow, Long rowsNum, Long sortingCol,
                                                              boolean increase, List<FormQuestion> questions,
                                                              List<String> statuses, boolean isActive);

    Long getCountInReviewAppForm();

    List<ApplicationForm> getCurrentApplicationForms();

    List<ApplicationForm> getByStatusAndRecruitment(Status status, Recruitment recruitment);

    List<ApplicationForm> getByRecruitment(Recruitment recruitment);

    List<ApplicationForm> getRejectedAfterInterview(Recruitment recruitment);

	boolean updateApplicationFormWithAnswers(ApplicationForm applicationForm);
}
