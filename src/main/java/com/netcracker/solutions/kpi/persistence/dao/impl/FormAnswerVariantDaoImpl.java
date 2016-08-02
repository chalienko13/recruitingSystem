package com.netcracker.solutions.kpi.persistence.dao.impl;

import com.netcracker.solutions.kpi.persistence.dao.FormAnswerVariantDao;
import com.netcracker.solutions.kpi.persistence.model.impl.proxy.FormQuestionProxy;
import com.netcracker.solutions.kpi.persistence.model.impl.real.FormAnswerVariantImpl;
import com.netcracker.solutions.kpi.persistence.util.ResultSetExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.util.List;

@Repository
public class FormAnswerVariantDaoImpl implements FormAnswerVariantDao {
    private static Logger log = LoggerFactory.getLogger(FormAnswerVariantDaoImpl.class.getName());

    @Autowired
    private JdbcDaoSupport jdbcDaoSupport;

    /*public FormAnswerVariantDaoImpl(DataSource dataSource) {
        this.jdbcDaoSupport = new JdbcDaoSupport();
        jdbcDaoSupport.setJdbcTemplate(new JdbcTemplate(dataSource));
    }*/

    private ResultSetExtractor<FormAnswerVariant> extractor = resultSet -> {
        FormAnswerVariant formAnswerVariant = new FormAnswerVariantImpl();
        formAnswerVariant.setId(resultSet.getLong(ID_COL));
        formAnswerVariant.setAnswer(resultSet.getString("" + ANSWER_COL + ""));
        formAnswerVariant.setFormQuestion(new FormQuestionProxy(resultSet.getLong(ID_QUESTION_COL)));
        return formAnswerVariant;
    };

    static final String TABLE_NAME = "form_answer_variant";

    static final String ID_COL = "id";
    static final String ANSWER_COL = "answer";
    static final String ID_QUESTION_COL = "id_question";

    private static final String SQL_GET = "SELECT " + ID_COL + ", " + ANSWER_COL + ", " + ID_QUESTION_COL + " from \""
            + TABLE_NAME + "\"";

    private static final String SQL_GET_BY_ID = SQL_GET + " WHERE " + ID_COL + " = ?;";

    private static final String SQL_GET_BY_TITLE_QUESTION = SQL_GET + " WHERE " + ANSWER_COL + " = ? AND " + ID_QUESTION_COL + " = ?";

    private static final String SQL_GET_BY_QUESTION_ID = SQL_GET + " WHERE " + ID_QUESTION_COL + " = ? ORDER BY " + ID_COL;

    private static final String SQL_INSERT = "INSERT INTO " + TABLE_NAME + " (" + ANSWER_COL + ", " + ID_QUESTION_COL
            + ") VALUES (?,?);";

    private static final String SQL_UPDATE = "UPDATE \"" + TABLE_NAME + "\" " + "SET " + ANSWER_COL + " = ?, "
            + ID_QUESTION_COL + " = ? WHERE " + ID_COL + " = ?;";

    private static final String SQL_DELETE = "DELETE FROM \"" + TABLE_NAME + "\" WHERE \"" + TABLE_NAME + "\".id = ?;";

    @Override
    public FormAnswerVariant getById(Long id) {
        log.info("Looking for FormAnswerVarian with id = {}", id);
        return jdbcDaoSupport.getJdbcTemplate().queryWithParameters(SQL_GET_BY_ID, extractor, id);
    }

    @Override
    public List<FormAnswerVariant> getByQuestionId(Long id) {
        log.info("Looking for FormAnswerVarian with QuestionId = {}", id);
        return jdbcDaoSupport.getJdbcTemplate().queryForList(SQL_GET_BY_QUESTION_ID, extractor, id);
    }

    @Override
    public Long insertFormAnswerVariant(FormAnswerVariant formatVariant, Connection connection) {
        log.info("Insert FormAnswerVariant with answer = {}", formatVariant.getAnswer());
        return jdbcDaoSupport.getJdbcTemplate().insert(SQL_INSERT, connection, formatVariant.getAnswer(),
                formatVariant.getFormQuestion().getId());
    }

    @Override
    public Long insertFormAnswerVariant(FormAnswerVariant formatVariant) {
        log.info("Insert FormAnswerVariant with answer = {}", formatVariant.getAnswer());
        return jdbcDaoSupport.getJdbcTemplate().insert(SQL_INSERT, formatVariant.getAnswer(),
                formatVariant.getFormQuestion().getId());
    }

    @Override
    public int updateFormAnswerVariant(FormAnswerVariant formAnswerVariant) {
        log.info("Update FormAnswerVariant with answer = {}", formAnswerVariant.getAnswer());
        return jdbcDaoSupport.getJdbcTemplate().update(SQL_UPDATE, formAnswerVariant.getAnswer(),
                formAnswerVariant.getFormQuestion().getId(), formAnswerVariant.getId());
    }

    @Override
    public int deleteFormAnswerVariant(FormAnswerVariant formVariant) {
        log.info("Delete formVariant with id = {}", formVariant.getId());
        return jdbcDaoSupport.getJdbcTemplate().update(SQL_DELETE, formVariant.getId());
    }

    @Override
    public int deleteFormAnswerVariant(FormAnswerVariant formVariant, Connection connection) {
        log.info("Delete formVariant with id = {}", formVariant.getId());
        return jdbcDaoSupport.getJdbcTemplate().update(SQL_DELETE, connection, formVariant.getId());
    }

    @Override
    public List<FormAnswerVariant> getAll() {
        log.info("Get all FormAnswerVariant");
        return jdbcDaoSupport.getJdbcTemplate().queryForList(SQL_GET, extractor);
    }

    @Override
    public FormAnswerVariant getAnswerVariantByTitleAndQuestion(String title, FormQuestion question) {
        log.trace("Looking for answer variant with title = {} and questionId = {})", title, question.getId());
        return jdbcDaoSupport.getJdbcTemplate().queryWithParameters(SQL_GET_BY_TITLE_QUESTION, extractor, title, question.getId());
    }
}
