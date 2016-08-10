package com.netcracker.solutions.kpi.persistence.dao.impl;

import com.netcracker.solutions.kpi.persistence.dao.QuestionTypeDao;
import com.netcracker.solutions.kpi.persistence.model.QuestionType;
import com.netcracker.solutions.kpi.persistence.util.ResultSetExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class QuestionTypeDaoImpl implements QuestionTypeDao {
    static final String TABLE_NAME = "form_question_type";
    static final String ID_COL = "id";
    static final String TYPE_TITLE_COL = "type_title";
    private static final String SQL_GET_ID = "SELECT " + ID_COL + ", " + TYPE_TITLE_COL + " FROM " + TABLE_NAME
            + " WHERE " + ID_COL + " = ?";
    private static final String SQL_GET_BY_NAME = "SELECT " + ID_COL + ", " + TYPE_TITLE_COL + " FROM " + TABLE_NAME
            + " WHERE " + TYPE_TITLE_COL + " = ?";
    private static final String SQL_INSERT = "INSERT INTO " + TABLE_NAME + " (" + TYPE_TITLE_COL + ") VALUES(?);";
    private static final String SQL_DELETE = "DELETE FROM " + TABLE_NAME + " WHERE " + TABLE_NAME + " = ?;";
    private static final String SQL_GET_ALL = "SELECT " + ID_COL + ", " + TYPE_TITLE_COL + " FROM " + TABLE_NAME;
    private static Logger log = LoggerFactory.getLogger(QuestionTypeDaoImpl.class.getName());
    @Autowired
    private JdbcDaoSupport jdbcDaoSupport;
    private ResultSetExtractor<QuestionType> extractor = resultSet -> {
        QuestionType questionType = new QuestionType();
        questionType.setId(resultSet.getLong(ID_COL));
        questionType.setTypeTitle(resultSet.getString(TYPE_TITLE_COL));
        return questionType;
    };

   /* public QuestionTypeDaoImpl(DataSource dataSource) {
        this.jdbcDaoSupport = new JdbcDaoSupport();
        jdbcDaoSupport.setJdbcTemplate(new JdbcTemplate(dataSource));
    }*/

    public List<QuestionType> getAllQuestionType() {
        return jdbcDaoSupport.getJdbcTemplate().queryForList(SQL_GET_ALL, extractor);
    }

    @Override
    public QuestionType getById(Long id) {
        log.info("Looking for form question with id  = {}", id);
        return jdbcDaoSupport.getJdbcTemplate().queryWithParameters(SQL_GET_ID, extractor, id);
    }

    @Override
    public QuestionType getByName(String name) {
        log.info("Looking for form question with name  = {}", name);
        return jdbcDaoSupport.getJdbcTemplate().queryWithParameters(SQL_GET_BY_NAME, extractor, name);
    }

    @Override
    public Long persistQuestionType(QuestionType questionType) {
        return jdbcDaoSupport.getJdbcTemplate().insert(SQL_INSERT, questionType.getTypeTitle());
    }

    @Override
    public int deleteQuestionType(QuestionType questionType) {
        return jdbcDaoSupport.getJdbcTemplate().update(SQL_DELETE, questionType.getId());
    }
}
