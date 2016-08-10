package com.netcracker.solutions.kpi.persistence.dao.impl;

import com.netcracker.solutions.kpi.persistence.dao.InterviewDao;
import com.netcracker.solutions.kpi.persistence.model.*;
import com.netcracker.solutions.kpi.persistence.util.ResultSetExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.util.List;

@Repository
public class InterviewDaoImpl implements InterviewDao {
    private static final String SQL_GET_BY_ID = "SELECT i.id, i.mark, i.date, i.id_interviewer, i.interviewer_role, i.adequate_mark, i.id_application_form \n"
            + "FROM interview i \n" + "WHERE i.id = ?;";
    private static final String SQL_GET_ALL = "SELECT i.id, i.mark, i.date, i.id_interviewer, i.interviewer_role, i.adequate_mark, i.id_application_form \n"
            + "FROM interview i";
    private static final String SQL_GET_BY_INTERVIEWER = "SELECT i.id, i.mark, i.date, i.id_interviewer, i.interviewer_role, i.adequate_mark, i.id_application_form \n"
            + "FROM interview i \n" + "WHERE i.id_interviewer = ?;";
    private static final String SQL_GET_BY_APPLICATION_FORM = "SELECT i.id, i.mark, i.date, i.id_interviewer, i.interviewer_role, i.adequate_mark, i.id_application_form \n"
            + "FROM interview i \n" + "WHERE i.id_application_form = ?;";
    private static final String SQL_INSERT = "INSERT INTO interview( mark, date, id_interviewer, "
            + " interviewer_role, adequate_mark, id_application_form) " + "VALUES (?, ?, ?, ?, ?, ?);";
    private static final String SQL_DELETE = "DELETE FROM interview WHERE interview.id = ?;";
    private static final String SQL_UPDATE = "UPDATE interview SET mark = ?, date= ?,"
            + " id_interviewer = ?, interviewer_role= ?, adequate_mark= ?, id_application_form= ? WHERE id = ?";
    private static final String SQL_GET_BY_APPLICATION_FORM_AND_INTERVIEWER_ROLE_ID = "SELECT i.id, i.mark, i.date," +
            " i.id_interviewer, i.interviewer_role, i.adequate_mark, i.id_application_form FROM interview i " +
            "WHERE i.id_application_form = ? and i.interviewer_role=?;";
    private static final String SQL_IS_ASSIGNED = "SELECT EXISTS (SELECT 1 FROM interview i WHERE i.id_application_form = ? AND i.id_interviewer = ?)";
    private static final String SQL_GET_ANSWERS = "SELECT fa.id FROM \"form_answer\" fa WHERE fa.id_interview = ?;";
    private static Logger log = LoggerFactory.getLogger(InterviewDaoImpl.class.getName());
    @Autowired
    private JdbcDaoSupport jdbcDaoSupport;
    private ResultSetExtractor<Interview> extractor = resultSet -> {
        Interview interview = new Interview();
        interview.setId(resultSet.getLong("id"));
        interview.setAdequateMark(resultSet.getBoolean("adequate_mark"));
        interview.setDate(resultSet.getTimestamp("date"));
        interview.setMark(resultSet.getInt("mark"));
        if (resultSet.wasNull()) {
            interview.setMark(null);
        }
        interview.setRole(new Role(resultSet.getLong("interviewer_role")));
        interview.setApplicationForm(new ApplicationForm(resultSet.getLong("id_application_form")));
        interview.setInterviewer(new User(resultSet.getLong("id_interviewer")));
        interview.setAnswers(getAnswers(resultSet.getLong("id")));
        return interview;
    };

    @Override
    public Interview getById(Long id) {
        log.info("Looking for interview with id = ", id);
        return jdbcDaoSupport.getJdbcTemplate().queryWithParameters(SQL_GET_BY_ID, extractor, id);
    }

    @Override
    public List<Interview> getByInterviewer(User user) {
        log.info("Looking for interview with interviewer = {} {}", user.getFirstName(), user.getLastName());
        return jdbcDaoSupport.getJdbcTemplate().queryForList(SQL_GET_BY_INTERVIEWER, extractor, user.getId());
    }

    @Override
    public List<Interview> getByApplicationForm(ApplicationForm applicationForm) {
        log.info("Looking for interview with application form id = ", applicationForm.getId());
        return jdbcDaoSupport.getJdbcTemplate().queryForList(SQL_GET_BY_APPLICATION_FORM, extractor, applicationForm.getId());

    }

    @Override
    public Interview getByApplicationFormAndInterviewerRoleId(ApplicationForm applicationForm, Long interviewerRoleId) {
        return jdbcDaoSupport.getJdbcTemplate().queryWithParameters(SQL_GET_BY_APPLICATION_FORM_AND_INTERVIEWER_ROLE_ID, extractor,
                applicationForm.getId(), interviewerRoleId);
    }

    @Override
    public Long insertInterview(Interview interview, ApplicationForm applicationForm, User interviewer, Role role) {
        log.info("Insert interview with id = ", interview.getId());
        return jdbcDaoSupport.getJdbcTemplate().insert(SQL_INSERT, interview.getMark(), interview.getDate(), interview.getId(),
                role.getId(), interview.isAdequateMark(), applicationForm.getId());
    }

    @Override
    public Long insertInterview(Interview interview, Connection connection) {
        log.info("Insert interview with id = ", interview.getId());
        return jdbcDaoSupport.getJdbcTemplate().insert(SQL_INSERT, connection, interview.getMark(), interview.getDate(),
                interview.getInterviewer().getId(), interview.getRole().getId(), interview.isAdequateMark(),
                interview.getApplicationForm().getId());
    }

    @Override
    public int updateInterview(Interview interview) {
        log.info("Update interview with id = {}", interview.getId());
        return jdbcDaoSupport.getJdbcTemplate().update(SQL_UPDATE, interview.getMark(), interview.getDate(),
                interview.getInterviewer().getId(), interview.getRole().getId(), interview.isAdequateMark(), interview.getApplicationForm().getId(),
                interview.getId());
    }

    @Override
    public int deleteInterview(Interview interview) {
        log.info("Delete interview with id = " + interview.getId());
        return jdbcDaoSupport.getJdbcTemplate().update(SQL_DELETE, interview.getId());
    }

    @Override
    public List<Interview> getAll() {
        log.info("Getting all interviews");
        return jdbcDaoSupport.getJdbcTemplate().queryForList(SQL_GET_ALL, extractor);
    }

    @Override
    public boolean haveNonAdequateMark(Long applicationFormID, Long interviewerId) {
        log.info("Getting NonAdequateMarks");
        return jdbcDaoSupport.getJdbcTemplate().queryWithParameters("select exists( select i.adequate_mark from " +
                "interview i where i.adequate_mark=true and i.id_application_form = ? and i.id_interviewer <> ?) ", resultSet ->
                resultSet.getBoolean(1), applicationFormID, interviewerId);
    }

    @Override
    public boolean haveNonAdequateMarkForAdmin(Long applicationFormID) {
        log.info("Getting NonAdequateMarks");
        return jdbcDaoSupport.getJdbcTemplate().queryWithParameters("select exists( select i.adequate_mark from " +
                "interview i where i.adequate_mark=true and i.id_application_form = ? ) ", resultSet ->
                resultSet.getBoolean(1), applicationFormID);
    }

    private List<FormAnswer> getAnswers(Long interviewId) {
        return jdbcDaoSupport.getJdbcTemplate().queryForList(SQL_GET_ANSWERS, resultSet -> {
            FormAnswer formAnswerProxy = new FormAnswer(resultSet.getLong("id"));
            return formAnswerProxy;
        }, interviewId);
    }

    @Override
    public boolean isFormAssigned(ApplicationForm applicationForm, User interviewer) {
        log.trace("Checking is form with id = {} assigned to inteviewer with id = {}", applicationForm.getId(), interviewer.getId());
        return jdbcDaoSupport.getJdbcTemplate().queryWithParameters(SQL_IS_ASSIGNED, resultSet -> resultSet.getBoolean(1), applicationForm.getId(), interviewer.getId());
    }

    @Override
    public int updateInterview(Interview interview, Connection connection) {
        log.info("Update interview with id = {}", interview.getId());
        return jdbcDaoSupport.getJdbcTemplate().update(SQL_UPDATE, connection, interview.getMark(), interview.getDate(),
                interview.getInterviewer().getId(), interview.getRole().getId(), interview.isAdequateMark(),
                interview.getApplicationForm().getId(), interview.getId());
    }
}
