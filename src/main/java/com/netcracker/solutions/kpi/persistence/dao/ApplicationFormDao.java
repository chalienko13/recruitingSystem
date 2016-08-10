package com.netcracker.solutions.kpi.persistence.dao;

import com.netcracker.solutions.kpi.persistence.model.*;

import java.sql.Connection;
import java.util.List;

public interface ApplicationFormDao {

    ApplicationForm getById(Long id);

    List<ApplicationForm> getByUserId(Long id);

    List<ApplicationForm> getByStatus(String status);

    List<ApplicationForm> getByState(boolean state);

    int deleteApplicationForm(ApplicationForm applicationForm);

    Long insertApplicationForm(ApplicationForm applicationForm, Connection connection);

    int updateApplicationForm(ApplicationForm applicationForm);

    List<ApplicationForm> getSearchAppFormByNameFromToRows(String lastName, Long fromRows, Long rowsNum);

    List<ApplicationForm> getAll();

    Long getCountRejectedAppForm();

    Long getCountToWorkAppForm();

    Long getCountGeneralAppForm();

    Long getCountAdvancedAppForm();

    ApplicationForm getCurrentApplicationFormByUserId(Long id);

    List<ApplicationForm> getOldApplicationFormsByUserId(Long id);

    ApplicationForm getLastApplicationFormByUserId(Long id);

    List<ApplicationForm> getByInterviewer(User interviewer);

    boolean isAssignedForThisRole(ApplicationForm applicationForm, Role role);

    int changeCurrentsAppFormStatus(Long fromIdStatus, Long toIdStatus);

    Long getCountRecruitmentStudents(Long id);

    Long getApprovedStudentsByRecruitmentId(Short id);

    List<ApplicationForm> getCurrentApplicationForms(Long fromRow, Long rowsNum, Long sortingCol, boolean increase);

    List<ApplicationForm> getCurrentApplicationForms();

    List<ApplicationForm> getCurrentApplicationFormsFiltered(Long fromRow, Long rowsNum, Long sortingCol,
                                                             boolean increase, List<FormQuestion> questions,
                                                             List<String> statuses, boolean isActive);

    List<ApplicationForm> getApplicationFormsSorted(Long fromRow, Long rowsNum, Long sortingCol, boolean increase);

    Long getCountInReviewAppForm();

    List<ApplicationForm> getByStatusAndRecruitment(Status status, Recruitment recruitment);

    List<ApplicationForm> getByRecruitment(Recruitment recruitment);

    List<ApplicationForm> getRejectedAfterInterview(Recruitment recruitment);

    Long getCountApprovedAppForm();

    int updateApplicationForm(ApplicationForm applicationForm, Connection connection);
}
