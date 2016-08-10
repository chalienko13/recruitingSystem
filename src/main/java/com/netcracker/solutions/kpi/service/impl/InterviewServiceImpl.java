package com.netcracker.solutions.kpi.service.impl;

import com.netcracker.solutions.kpi.persistence.dao.FormAnswerDao;
import com.netcracker.solutions.kpi.persistence.dao.InterviewDao;
import com.netcracker.solutions.kpi.persistence.model.*;
import com.netcracker.solutions.kpi.service.ApplicationFormService;
import com.netcracker.solutions.kpi.service.FormQuestionService;
import com.netcracker.solutions.kpi.service.InterviewService;
import com.netcracker.solutions.kpi.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class InterviewServiceImpl implements InterviewService {
    private static Logger log = LoggerFactory.getLogger(InterviewServiceImpl.class);

    @Autowired
    private FormQuestionService questionService;// = ServiceFactory.getFormQuestionService();

    @Autowired
    private RoleService roleService;// = ServiceFactory.getRoleService();
    private ApplicationFormService applicationFormService;// = ServiceFactory.getApplicationFormService();

    @Autowired
    private InterviewDao interviewDao;

    @Autowired
    private FormAnswerDao formAnswerDao;// = DaoFactory.getFormAnswerDao();

    @Autowired
    private DataSource dataSource;

    @Override
    public List<Interview> getAll() {
        return interviewDao.getAll();
    }

    @Override
    public Interview getById(Long id) {
        return interviewDao.getById(id);
    }

    @Override
    public List<Interview> getByInterviewer(User user) {
        return interviewDao.getByInterviewer(user);
    }

    @Override
    public List<Interview> getByApplicationForm(ApplicationForm applicationForm) {
        return interviewDao.getByApplicationForm(applicationForm);
    }

    @Override
    public Interview getByApplicationFormAndInterviewerRoleId(ApplicationForm applicationForm, Long interviewerRoleId) {
        return interviewDao.getByApplicationFormAndInterviewerRoleId(applicationForm, interviewerRoleId);
    }

    @Override
    public boolean insertInterviewWithAnswers(Interview interview, List<FormAnswer> formAnswers) {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            Long generatedId = interviewDao.insertInterview(interview, connection);
            interview.setId(generatedId);

            for (FormAnswer formAnswer : formAnswers) {
                formAnswerDao.insertFormAnswerForInterview(formAnswer, formAnswer.getFormQuestion(),
                        formAnswer.getFormAnswerVariant(), interview, connection);
            }
            connection.commit();
        } catch (SQLException e) {
            log.error("Cannot insert Interview with answers {}", e);
            return false;
        }
        return true;
    }

    @Override
    public boolean updateInterview(Interview interview) {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            interviewDao.updateInterview(interview, connection);
            formAnswerDao.deleteNotPresented(interview.getAnswers(), interview, connection);
            for (FormAnswer formAnswer : interview.getAnswers()) {
                if (formAnswer.getId() == null) {
                    formAnswerDao.insertFormAnswerForInterview(formAnswer, formAnswer.getFormQuestion(), formAnswer.getFormAnswerVariant(),
                            interview, connection);
                } else {
                    formAnswerDao.updateFormAnswer(formAnswer);
                }
            }
            connection.commit();
        } catch (SQLException e) {
            if (log.isWarnEnabled()) {
                log.error("Cannot update Interview", e);
            }
            return false;
        }
        return true;
    }

    @Override
    public int deleteInterview(Interview interview) {
        return interviewDao.deleteInterview(interview);
    }

    @Override
    public boolean haveNonAdequateMark(Long applicationFormID, Long interviewerId) {
        return interviewDao.haveNonAdequateMark(applicationFormID, interviewerId);
    }

    @Override
    public boolean haveNonAdequateMarkForAdmin(Long applicationFormID) {
        return interviewDao.haveNonAdequateMarkForAdmin(applicationFormID);
    }

    @Override
    public boolean isFormAssigned(ApplicationForm applicationForm, User interviewer) {
        return interviewDao.isFormAssigned(applicationForm, interviewer);
    }

    public void assignStudent(ApplicationForm applicationForm, User interviewer, Role role) {
        log.info("Interviewer {} is assigning student {} for role {}", interviewer.getId(), applicationForm.getId(), role.getRoleName());
        if (roleService.isInterviewerRole(role)
                && !applicationFormService.isAssignedForThisRole(applicationForm, role)) {
            Interview interview = new Interview();
            interview.setInterviewer(interviewer);
            interview.setApplicationForm(applicationForm);
            interview.setDate(new Timestamp(System.currentTimeMillis()));
            interview.setRole(role);

            List<FormQuestion> questions = questionService.getEnableByRole(role);
            List<FormAnswer> answers = new ArrayList<>();
            for (FormQuestion formQuestion : questions) {
                FormAnswer formAnswer = new FormAnswer();
                formAnswer.setFormQuestion(formQuestion);
                formAnswer.setInterview(interview);
                answers.add(formAnswer);
            }
            insertInterviewWithAnswers(interview, answers);
        }
    }

}
