package com.netcracker.solutions.kpi.persistence.dao.impl;

import com.netcracker.solutions.kpi.persistence.dao.FormQuestionDao;
import com.netcracker.solutions.kpi.persistence.model.*;
import com.netcracker.solutions.kpi.persistence.util.ResultSetExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Repository
public class FormQuestionDaoImpl implements FormQuestionDao {
    private static Logger log = LoggerFactory.getLogger(FormQuestionDaoImpl.class.getName());

    @Autowired
    private JdbcDaoSupport jdbcDaoSupport;

    private ResultSetExtractor<FormQuestion> extractor = resultSet -> {
        FormQuestion formQuestion = new FormQuestion();
        formQuestion.setId(resultSet.getLong(ID_COL));
        formQuestion.setEnable(resultSet.getBoolean(ENABLE_COL));
        formQuestion.setMandatory(resultSet.getBoolean(MANDATORY_COL));
        formQuestion.setOrder(resultSet.getInt(ORDER_COL));
       // formQuestion.setQuestionRoles(getRoles(resultSet.getLong(ID_COL)));
        formQuestion.setTitle(resultSet.getString(TITLE_COL));
        formQuestion.setQuestionType(new QuestionType(resultSet.getLong(ID_QUESTION_TYPE_COL),
                resultSet.getString(QuestionTypeDaoImpl.TYPE_TITLE_COL)));
        formQuestion.setFormAnswerVariants(getAnswerVariants(resultSet.getLong(ID_COL)));
        return formQuestion;
    };

    static final String ROLE_MAP_TABLE_NAME = "form_question_role";
    static final String ROLE_MAP_TABLE_ROLE_ID = "id_role";
    static final String ROLE_MAP_TABLE_FORM_QUESTION_ID = "id_form_question";

    static final String TABLE_NAME = "form_question";

    static final String ID_COL = "id";
    static final String TITLE_COL = "title";
    static final String ID_QUESTION_TYPE_COL = "id_question_type";
    static final String ENABLE_COL = "enable";
    static final String MANDATORY_COL = "mandatory";
    static final String ORDER_COL = "order";

    private static final String SQL_GET_ALL = "SELECT fq." + ID_COL + ", fq." + TITLE_COL + ", fq."
            + ID_QUESTION_TYPE_COL + ", fq." + ENABLE_COL + ", fq." + MANDATORY_COL + ", fq." + ORDER_COL + ", fqt."
            + QuestionTypeDaoImpl.TYPE_TITLE_COL + " FROM " + TABLE_NAME + " fq INNER JOIN "
            + QuestionTypeDaoImpl.TABLE_NAME + " fqt ON fqt." + QuestionTypeDaoImpl.ID_COL + " = fq."
            + ID_QUESTION_TYPE_COL + "";

    private static final String SQL_GET_BY_ID = SQL_GET_ALL + " WHERE fq." + ID_COL + " = ?;";

    private static final String SQL_GET_BY_ROLE = "SELECT fq.id, fq.title, fq.id_question_type, fq.enable, fq.mandatory, fq.order, fqt.type_title FROM form_question fq\n" +
            "  INNER JOIN form_question_type fqt ON fqt.id = fq.id_question_type\n" +
            "  INNER JOIN form_question_role fqr ON fqr.id_form_question = fq.id WHERE fqr.id_role = ? ORDER BY fq.order";

    private static final String SQL_GET_BY_ROLE_NONTEXT = SQL_GET_ALL + " INNER JOIN " + ROLE_MAP_TABLE_NAME + " fqr ON fqr."
            + ROLE_MAP_TABLE_FORM_QUESTION_ID + " = fq." + ID_COL + " WHERE fqr." + ROLE_MAP_TABLE_ROLE_ID + " = ? AND (fq."
            + ID_QUESTION_TYPE_COL + "= ANY('{2,3,4}'::int[]));";

    private static final String SQL_GET_ENABLE_BY_ROLE = SQL_GET_ALL + " INNER JOIN " + ROLE_MAP_TABLE_NAME + " fqr ON fqr."
            + ROLE_MAP_TABLE_FORM_QUESTION_ID + " = fq." + ID_COL + " WHERE fqr." + ROLE_MAP_TABLE_ROLE_ID + " = ? AND fq." + ENABLE_COL + " = true";
    
	private static final String SQL_GET_WITH_VARIANTS_BY_ROLE = SQL_GET_ALL + " INNER JOIN " + ROLE_MAP_TABLE_NAME
			+ " fqr ON fqr." + ROLE_MAP_TABLE_FORM_QUESTION_ID + " = fq." + ID_COL + " WHERE fqr."
			+ ROLE_MAP_TABLE_ROLE_ID
			+ " = ? AND EXISTS (SELECT 1 FROM form_answer_variant fav WHERE fav.id_question = fq." + ID_COL + ")";

    private static final String SQL_GET_BY_APPLICATION_FORM = SQL_GET_ALL + " INNER JOIN form_answer fa ON fa.id_question = fq.id WHERE fa.id_application_form = ?;";

    private static final String SQL_ENABLE_GET_BY_APPLICATION_FORM = SQL_GET_ALL + " INNER JOIN form_answer fa ON fa.id_question = fq.id WHERE fa.id_application_form = ? AND fq.enable = true;";

    private static final String SQL_UNCONNECTED = SQL_GET_ALL + " INNER JOIN form_question_role fqr ON fqr.id_form_question = fq.id" +
            " WHERE fqr.id_role = 3 AND NOT EXISTS(SELECT fa.id from form_answer fa WHERE fa.id_question = fq.id AND fa.id_application_form = ?)";

    private static final String SQL_INSERT = "INSERT INTO " + TABLE_NAME + " ( " + TITLE_COL + ", " + ENABLE_COL + ", "
            + MANDATORY_COL + ", " + ID_QUESTION_TYPE_COL + ", \"" + ORDER_COL + "\") " + "VALUES (?,?,?,?,?)";

    private static final String SQL_UPDATE = "UPDATE " + TABLE_NAME + " SET " + TITLE_COL + " = ?, " + ENABLE_COL
            + " = ?, " + ID_QUESTION_TYPE_COL + " = ?, " + MANDATORY_COL + " = ?, \"" + ORDER_COL + "\" = ? WHERE " + ID_COL + " = ?;";

    private static final String SQL_DELETE = "DELETE FROM " + TABLE_NAME + " WHERE " + ID_COL + " = ?";

/*
    public FormQuestionDaoImpl(DataSource dataSource) {
        this.jdbcDaoSupport = new JdbcDaoSupport();
        jdbcDaoSupport.setJdbcTemplate(new JdbcTemplate(dataSource));
    }*/

    @Override
    public Long insertFormQuestion(FormQuestion formQuestion, Connection connection) {
        log.info("Insert question with title, enable, mandatory = {}, {}, {}", formQuestion.getTitle(),
                formQuestion.isEnable(), formQuestion.isMandatory());
        return jdbcDaoSupport.getJdbcTemplate().insert(SQL_INSERT, connection, formQuestion.getTitle(), formQuestion.isEnable(),
                formQuestion.isMandatory(), formQuestion.getQuestionType().getId(), formQuestion.getOrder());
    }

    @Override
    public int deleteFormQuestion(FormQuestion formQuestion) {
        log.info("Deleting form question with id = ", formQuestion.getId());
        return jdbcDaoSupport.getJdbcTemplate().update(SQL_DELETE, formQuestion.getId());
    }

    private static final String SQL_ROLE_MAP_INSERT = "INSERT INTO " + ROLE_MAP_TABLE_NAME + " ("
            + ROLE_MAP_TABLE_FORM_QUESTION_ID + ", " + ROLE_MAP_TABLE_ROLE_ID + ") VALUES (?,?)";

    @Override
    public boolean addRole(FormQuestion formQuestion, Role role) {
        if (formQuestion.getId() == null) {
            log.warn("User don`t have id", formQuestion.getTitle());
            return false;
        }
        return jdbcDaoSupport.getJdbcTemplate().insert(SQL_ROLE_MAP_INSERT, formQuestion.getId(), role.getId()) > 0;
    }

    @Override
    public boolean addRole(FormQuestion formQuestion, Role role, Connection connection) {
        if (formQuestion.getId() == null) {
            log.warn("User: don`t have id", formQuestion.getTitle());
            return false;
        }
        return jdbcDaoSupport.getJdbcTemplate().insert(SQL_ROLE_MAP_INSERT, connection, formQuestion.getId(), role.getId()) > 0;
    }

    @Override
    public int deleteRole(FormQuestion formQuestion, Role role) {
        if (formQuestion.getId() == null) {
            log.warn("Form question: don`t have id", formQuestion.getTitle());
            return 0;
        }
        return jdbcDaoSupport.getJdbcTemplate().update("DELETE FROM " + ROLE_MAP_TABLE_NAME + " WHERE "
                        + ROLE_MAP_TABLE_FORM_QUESTION_ID + "= ? AND " + ROLE_MAP_TABLE_ROLE_ID + "=?;", formQuestion.getId(),
                role.getId());
    }

    @Override
    public FormQuestion getById(Long id) {
        log.info("Looking for form question with id = {}", id);
        return jdbcDaoSupport.getJdbcTemplate().queryWithParameters(SQL_GET_BY_ID, extractor, id);
    }

    @Override
    public List<FormQuestion> getByRole(Role role) {
        log.info("Looking for form question by role = {}", role.getRoleName());
        return jdbcDaoSupport.getJdbcTemplate().queryForList(SQL_GET_BY_ROLE, extractor, role.getId());
    }

    @Override
    public List<FormQuestion> getByRoleNonText(Role role) {
        log.info("Looking for non text form question by role = {}", role.getRoleName());
        return jdbcDaoSupport.getJdbcTemplate().queryForList(SQL_GET_BY_ROLE_NONTEXT, extractor, role.getId());
    }

    @Override
    public List<FormQuestion> getAll() {
        log.info("Get all form questions");
        return jdbcDaoSupport.getJdbcTemplate().queryForList(SQL_GET_ALL, extractor);
    }

    private List<Role> getRoles(Long formQuestionID) {
        return jdbcDaoSupport.getJdbcTemplate().queryWithParameters("SELECT fqr." + ROLE_MAP_TABLE_ROLE_ID + " FROM "
                        + ROLE_MAP_TABLE_NAME + " fqr\n" + "WHERE fqr." + ROLE_MAP_TABLE_FORM_QUESTION_ID + " = ?;",
                resultSet -> {
                    List<Role> roles = new ArrayList<>();
                    do {
                        roles.add(new Role(resultSet.getLong(ROLE_MAP_TABLE_ROLE_ID)));
                    } while (resultSet.next());
                    return roles;
                }, formQuestionID);
    }

    private List<FormAnswerVariant> getAnswerVariants(Long formQuestionID) {
        return jdbcDaoSupport.getJdbcTemplate().queryWithParameters("SELECT fav.id FROM form_answer_variant fav WHERE fav.id_question = ? ORDER BY fav.id",
                resultSet -> {
                    List<FormAnswerVariant> answersVariants = new ArrayList<>();
                    do {
                        answersVariants.add(new FormAnswerVariant(resultSet.getLong(FormAnswerVariantDaoImpl.ID_COL)));
                    } while (resultSet.next());
                    return answersVariants;
                }, formQuestionID);
    }

    @Override
    public int updateFormQuestion(FormQuestion formQuestion) {
        if ((formQuestion.getId() == null) && (log.isDebugEnabled())) {
            log.warn("Form question: don`t have id", formQuestion.getTitle());
            return 0;
        }
        return jdbcDaoSupport.getJdbcTemplate().update(SQL_UPDATE, formQuestion.getTitle(), formQuestion.isEnable(),
                formQuestion.getQuestionType().getId(), formQuestion.isMandatory(), formQuestion.getOrder(),
                formQuestion.getId());
    }

    @Override
    public int updateFormQuestion(FormQuestion formQuestion, Connection connection) {
        if ((formQuestion.getId() == null) && (log.isDebugEnabled())) {
            log.warn("Form question: don`t have id", formQuestion.getTitle());
            return 0;
        }
        log.info("Updating Question with Id = {}", formQuestion.getId());
        return jdbcDaoSupport.getJdbcTemplate().update(SQL_UPDATE, connection, formQuestion.getTitle(), formQuestion.isEnable(),
                formQuestion.getQuestionType().getId(), formQuestion.isMandatory(), formQuestion.getOrder(),
                formQuestion.getId());
    }

    @Override
    public Set<FormQuestion> getEnableByRoleAsSet(Role role) {
        log.info("Looking for set of form questions by role = {}", role.getRoleName());
        return jdbcDaoSupport.getJdbcTemplate().queryForSet(SQL_GET_ENABLE_BY_ROLE, extractor, role.getId());
    }

    @Override
    public List<FormQuestion> getEnableByRole(Role role) {
        log.info("Looking for form question by role = {}", role.getRoleName());
        return jdbcDaoSupport.getJdbcTemplate().queryForList(SQL_GET_ENABLE_BY_ROLE, extractor, role.getId());
    }

    @Override
    public Set<FormQuestion> getByApplicationFormAsSet(ApplicationForm applicationForm) {
        return jdbcDaoSupport.getJdbcTemplate().queryForSet(SQL_ENABLE_GET_BY_APPLICATION_FORM, extractor, applicationForm.getId());
    }

    @Override
    public List<FormQuestion> getEnableUnconnectedQuestion(Role role, ApplicationForm applicationForm) {
        return jdbcDaoSupport.getJdbcTemplate().queryForList(SQL_UNCONNECTED, extractor, applicationForm.getId());
    }

	@Override
	public List<FormQuestion> getWithVariantsByRole(Role role) {
		log.trace("Getting form questions with variants and role = {}", role.getRoleName());
		return jdbcDaoSupport.getJdbcTemplate().queryForList(SQL_GET_WITH_VARIANTS_BY_ROLE, extractor, role.getId());
	}
}
