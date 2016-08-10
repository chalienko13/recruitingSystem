package com.netcracker.solutions.kpi.persistence.dao.impl;

import com.netcracker.solutions.kpi.persistence.dao.FormAnswerDao;
import com.netcracker.solutions.kpi.persistence.model.*;
import com.netcracker.solutions.kpi.persistence.util.ResultSetExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.util.List;

@Repository
public class FormAnswerDaoImpl implements FormAnswerDao {

    static final String TABLE_NAME = "form_answer";
    static final String ID_COL = "id";
    static final String ANSWER_COL = "answer";
    static final String ID_QUESTION_COL = "id_question";
    static final String ID_VARIANT_COL = "id_variant";
    static final String ID_INTERVIEW_COL = "id_interview";
    static final String ID_APPLICATION_FORM_COL = "id_application_form";
    private static final String SQL_GET = "SELECT fa." + ID_COL + ", fa." + ANSWER_COL + ", fa." + ID_QUESTION_COL
            + ", fa." + ID_APPLICATION_FORM_COL + ", fa." + ID_VARIANT_COL + ", fa." + ID_INTERVIEW_COL + " FROM "
            + TABLE_NAME + " fa";
    private static final String SQL_GET_BY_ID = SQL_GET + " WHERE fa." + ID_COL + " = ?;";
    private static final String SQL_GET_BY_INTERVIEW_AND_QUESTION = SQL_GET + " WHERE fa." + ID_INTERVIEW_COL
            + "= ? and  fa." + ID_QUESTION_COL + " = ?;";
    private static final String SQL_GET_BY_APPLICATION_FORM_AND_QUESTION = SQL_GET + " WHERE fa."
            + ID_APPLICATION_FORM_COL + "= ? and  fa." + ID_QUESTION_COL + " = ?;";
    private static final String SQL_INSERT_FOR_INTERVIEW = "INSERT INTO " + TABLE_NAME + " (" + ANSWER_COL + ", "
            + ID_QUESTION_COL + ", " + ID_VARIANT_COL + ", " + ID_INTERVIEW_COL + ") \n" + "VALUES (?,?,?,?);";
    private static final String SQL_INSERT_FOR_APPLICATION_FORM = "INSERT INTO " + TABLE_NAME + " (" + ANSWER_COL + ", "
            + ID_QUESTION_COL + ", " + ID_APPLICATION_FORM_COL + ", " + ID_VARIANT_COL + ") \n" + "VALUES (?,?,?,?);";
    private static final String SQL_INSERT = "INSERT INTO " + TABLE_NAME + " (" + ANSWER_COL + ", " + ID_QUESTION_COL
            + ", " + ID_APPLICATION_FORM_COL + ", " + ID_VARIANT_COL + ", " + ID_INTERVIEW_COL + ") \n"
            + "VALUES (?,?,?,?,?);";
    private static final String SQL_UPDATE = "UPDATE " + TABLE_NAME + " SET " + ANSWER_COL + " = ?, " + ID_VARIANT_COL
            + " = ? WHERE " + ID_COL + "= ?;";
    private static final String SQL_DELETE = "DELETE FROM " + TABLE_NAME + " WHERE " + ID_COL + " = ?;";
    private static final String SQL_DELETE_NOT_PRESENTED_APP_FORM = "DELETE FROM " + TABLE_NAME + " WHERE " + ID_APPLICATION_FORM_COL + " = ? ";
    private static final String SQL_DELETE_NOT_PRESENTED_INTERVIEW = "DELETE FROM " + TABLE_NAME + " WHERE " + ID_INTERVIEW_COL + " = ? ";
    private static Logger log = LoggerFactory.getLogger(FormAnswerDaoImpl.class.getName());
    @Autowired
    private JdbcDaoSupport jdbcDaoSupport;
    private ResultSetExtractor<FormAnswer> extractor = resultSet -> {
        FormAnswer formAnswer = new FormAnswer();
        formAnswer.setId(resultSet.getLong(ID_COL));
        formAnswer.setAnswer(resultSet.getString(ANSWER_COL));
        formAnswer.setInterview(new Interview(resultSet.getLong(ID_INTERVIEW_COL)));
        formAnswer.setApplicationForm(new ApplicationForm(resultSet.getLong(ID_APPLICATION_FORM_COL)));
        long answerVariantId = resultSet.getLong(ID_VARIANT_COL);
        if (answerVariantId != 0)
            formAnswer.setFormAnswerVariant(new FormAnswerVariant(resultSet.getLong(ID_VARIANT_COL)));
        formAnswer.setFormQuestion(new FormQuestion(resultSet.getLong(ID_QUESTION_COL)));
        return formAnswer;
    };

    @Override
    public FormAnswer getById(Long id) {
        log.info("Looking for answer with id = ", id);
        return jdbcDaoSupport.getJdbcTemplate().queryWithParameters(SQL_GET_BY_ID, extractor, id);
    }

    @Override
    public List<FormAnswer> getByInterviewAndQuestion(Interview interview, FormQuestion question) {
        log.info("Looking for answer with interview_id, question= ", interview.getId(), question.getTitle());
        return jdbcDaoSupport.getJdbcTemplate().queryForList(SQL_GET_BY_INTERVIEW_AND_QUESTION, extractor, interview.getId(),
                question.getId());
    }

    @Override
    public Long insertFormAnswer(FormAnswer formAnswer, Interview interview, FormQuestion question,
                                 FormAnswerVariant answerVariant, ApplicationForm applicationForm, Connection connection) {
        log.info(
                "Inserting form answer with interview_id, question_id, application form id,"
                        + "form answer variant id=",
                interview.getId(), question.getTitle(), applicationForm.getId(), answerVariant.getId());
        return jdbcDaoSupport.getJdbcTemplate().insert(SQL_INSERT, connection, formAnswer.getAnswer(), question.getId(),
                applicationForm.getId(), answerVariant.getId(), interview.getId());
    }

    @Override
    public int updateFormAnswer(FormAnswer formAnswer) {
        log.info("Update form answer with id = ", formAnswer.getId());
        FormAnswerVariant variant = formAnswer.getFormAnswerVariant();
        return jdbcDaoSupport.getJdbcTemplate().update(SQL_UPDATE, formAnswer.getAnswer(), variant != null ? variant.getId() : null,
                formAnswer.getId());
    }

    @Override
    public int deleteFormAnswer(FormAnswer formAnswer) {
        log.info("Delete form answer with id = ", formAnswer.getId());
        return jdbcDaoSupport.getJdbcTemplate().update(SQL_DELETE, formAnswer.getId());
    }

    @Override
    public Long insertFormAnswerForApplicationForm(FormAnswer formAnswer) {
        log.info("Insert form answer with id = ", formAnswer.getId());
        return jdbcDaoSupport.getJdbcTemplate()
                .insert("INSERT INTO " + TABLE_NAME + " (" + ID_QUESTION_COL + ", " + ID_APPLICATION_FORM_COL + ", "
                                + ID_VARIANT_COL + ") \n" + "VALUES (?,?,?);", formAnswer.getFormQuestion().getId(),
                        formAnswer.getApplicationForm().getId(), formAnswer.getFormAnswerVariant().getId());
    }

    @Override
    public Long insertBlankFormAnswerForApplicationForm(FormAnswer formAnswer) {
        log.info("Insert form answer with id = ", formAnswer.getId());
        return jdbcDaoSupport.getJdbcTemplate()
                .insert("INSERT INTO " + TABLE_NAME + " (" + ID_QUESTION_COL + ", " + ID_APPLICATION_FORM_COL + ") "
                                + "VALUES (?,?);", formAnswer.getFormQuestion().getId(),
                        formAnswer.getApplicationForm().getId());
    }

    @Override
    public Long insertFormAnswerForApplicationForm(FormAnswer formAnswer, FormQuestion question,
                                                   ApplicationForm applicationForm, Connection connection) {
        log.info("Inserting form answer with question_id, application form id, form answer variant id= ",
                question.getTitle(), applicationForm.getId());
        FormAnswerVariant variant = formAnswer.getFormAnswerVariant();
        return jdbcDaoSupport.getJdbcTemplate().insert(SQL_INSERT_FOR_APPLICATION_FORM, connection, formAnswer.getAnswer(),
                question.getId(), applicationForm.getId(), variant != null ? variant.getId() : null);
    }

    @Override
    public Long insertFormAnswerForInterview(FormAnswer formAnswer, FormQuestion question,
                                             FormAnswerVariant answerVariant, Interview interview, Connection connection) {
        log.info("Inserting form answer with interview_id, question_id, form answer variant id= ", interview.getId(),
                question.getTitle(), answerVariant != null ? answerVariant.getId() : null);
        return jdbcDaoSupport.getJdbcTemplate().insert(SQL_INSERT_FOR_INTERVIEW, connection, formAnswer.getAnswer(),
                question.getId(), answerVariant != null ? answerVariant.getId() : null, interview.getId());
    }

    @Override
    public List<FormAnswer> getByApplicationFormAndQuestion(ApplicationForm applicationForm, FormQuestion question) {
        return jdbcDaoSupport.getJdbcTemplate().queryForList(SQL_GET_BY_APPLICATION_FORM_AND_QUESTION, extractor,
                applicationForm.getId(), question.getId());
    }

    @Override
    public Long insertFormAnswerForInterview(FormAnswer formAnswer) {
        FormAnswerVariant variant = formAnswer.getFormAnswerVariant();
        log.info("Inserting form answer with interview_id, question_id, form answer variant id= ", formAnswer.getInterview().getId(),
                formAnswer.getFormQuestion().getTitle(), variant != null ? variant.getId() : null);
        return jdbcDaoSupport.getJdbcTemplate().insert(SQL_INSERT_FOR_INTERVIEW, formAnswer.getAnswer(),
                formAnswer.getFormQuestion().getId(), variant != null ? variant.getId() : null, formAnswer.getInterview().getId());
    }

    @Override
    public void deleteNotPresented(List<FormAnswer> presentedAnswers, ApplicationForm applicationForm,
                                   Connection connection) {
        log.trace("Deleting form answers from appliction form {}", applicationForm.getId());
        String sql = getDeleteNotPresentedSql(SQL_DELETE_NOT_PRESENTED_APP_FORM, presentedAnswers);
        jdbcDaoSupport.getJdbcTemplate().update(sql, connection, applicationForm.getId());
    }

    @Override
    public void deleteNotPresented(List<FormAnswer> presentedAnswers, Interview interview, Connection connection) {
        log.trace("Deleting form answers from interview {}", interview.getId());
        String sql = getDeleteNotPresentedSql(SQL_DELETE_NOT_PRESENTED_INTERVIEW, presentedAnswers);
        jdbcDaoSupport.getJdbcTemplate().update(sql, connection, interview.getId());
    }

    private String getDeleteNotPresentedSql(String startSql, List<FormAnswer> presentedAnswers) {
        if (!presentedAnswers.isEmpty()) {
            StringBuilder notInQuery = new StringBuilder(" AND " + ID_COL + " NOT IN (");
            int i = 0;
            for (FormAnswer formAnswer : presentedAnswers) {
                if (formAnswer.getId() != null) {
                    if (i++ == 0)
                        notInQuery.append(Long.toString(formAnswer.getId()));
                    else
                        notInQuery.append("," + formAnswer.getId());
                }
            }
            notInQuery.append(')');
            startSql += notInQuery;
        }
        return startSql;
    }
}