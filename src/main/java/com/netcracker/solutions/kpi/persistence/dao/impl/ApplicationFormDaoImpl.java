package com.netcracker.solutions.kpi.persistence.dao.impl;

import com.netcracker.solutions.kpi.persistence.dao.ApplicationFormDao;
import com.netcracker.solutions.kpi.persistence.model.*;
import com.netcracker.solutions.kpi.persistence.model.enums.StatusEnum;
import com.netcracker.solutions.kpi.persistence.repository.ApplicationFormRepository;
import com.netcracker.solutions.kpi.persistence.util.ResultSetExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ApplicationFormDaoImpl implements ApplicationFormDao {

    @Autowired
    ApplicationFormRepository applicationFormRepository;

    private static final String ID_COL = "id";
    private static final String ID_STATUS_COL = "id_status";
    private static final String IS_ACTIVE_COL = "is_active";
    private static final String ID_RECRUITMENT_COL = "id_recruitment";
    private static final String PHOTO_SCOPE_COL = "photo_scope";
    private static final String ID_USER_COL = "id_user";
    private static final String DATE_CREATE_COL = "date_create";
    private static final String FEEDBACK = "feedback";
    private static final String TABLE_NAME = "application_form";
    private static final String SQL_GET_BY_ID = "SELECT a." + ID_COL + ", a." + ID_STATUS_COL + ", a." + IS_ACTIVE_COL
            + ",a." + ID_RECRUITMENT_COL + ", a." + PHOTO_SCOPE_COL + ", " + "a." + ID_USER_COL + ", a."
            + DATE_CREATE_COL + ", a." + FEEDBACK + ", s.title \n" + "FROM \"" + TABLE_NAME
            + "\" a INNER JOIN status s ON s.id = a.id_status \n" + "WHERE a.id = ?;";
    private static final String SQL_GET_BY_USER_ID = "SELECT a." + ID_COL + ",  a.id_status, a.is_active,a."
            + ID_RECRUITMENT_COL + ", a." + PHOTO_SCOPE_COL + ", " + "a." + ID_USER_COL + ", a." + DATE_CREATE_COL
            + ", a." + FEEDBACK + ", s.title \n" + "FROM \"" + TABLE_NAME
            + "\" a INNER JOIN status s ON s.id = a.id_status \n" + "WHERE a." + ID_USER_COL + " = ?;";
    private static final String SQL_GET_BY_STATUS = "SELECT a." + ID_COL + ",  a." + ID_STATUS_COL + ", a."
            + IS_ACTIVE_COL + ",a." + ID_RECRUITMENT_COL + ", a." + PHOTO_SCOPE_COL + ", " + "a." + ID_USER_COL + ", a."
            + DATE_CREATE_COL + ", a." + FEEDBACK + ", s.title \n" + "FROM \"" + TABLE_NAME
            + "\" a INNER JOIN status s ON s.id = a." + ID_STATUS_COL + "\n" + "WHERE s.title = ?;";
    private static final String SQL_GET_BY_STATE = "SELECT a." + ID_COL + ",  a." + ID_STATUS_COL + ", a."
            + IS_ACTIVE_COL + ",a." + ID_RECRUITMENT_COL + ", a." + PHOTO_SCOPE_COL + ", " + "a." + ID_USER_COL + ", a."
            + DATE_CREATE_COL + ", a." + FEEDBACK + ", s.title \n" + "FROM \"" + TABLE_NAME
            + "\" a INNER JOIN status s ON s.id = a." + ID_STATUS_COL + "\n" + "WHERE a.is_active = ?;";
    private static final String SQL_DELETE = "DELETE FROM " + TABLE_NAME + " a WHERE a." + ID_COL + " = ?;";
    private static final String SQL_INSERT = "INSERT INTO " + TABLE_NAME + " (" + ID_STATUS_COL + ", " + IS_ACTIVE_COL
            + ", " + ID_RECRUITMENT_COL + ", " + PHOTO_SCOPE_COL + ", " + "" + ID_USER_COL + ", " + DATE_CREATE_COL
            + ", " + FEEDBACK + ") VALUES (?, ?, ?, ?, ?, ?, ?);";
    private static final String SQL_GET_ALL = "SELECT a." + ID_COL + ",  a." + ID_STATUS_COL + ", a." + IS_ACTIVE_COL
            + ",a." + ID_RECRUITMENT_COL + ", a." + PHOTO_SCOPE_COL + ", " + "a." + ID_USER_COL + ", a."
            + DATE_CREATE_COL + ", a." + FEEDBACK + ", s.title \n" + "FROM \"" + TABLE_NAME
            + "\" a INNER JOIN status s ON s.id = a." + ID_STATUS_COL + " \n;";
    private static final String SQL_UPDATE = "UPDATE \"" + TABLE_NAME + "\" SET " + ID_STATUS_COL + " = ?, "
            + IS_ACTIVE_COL + "  = ?, " + PHOTO_SCOPE_COL + " = ?, " + DATE_CREATE_COL + " = ?, " + FEEDBACK + " = ?, " + ID_RECRUITMENT_COL + " = ?"
            + " WHERE " + ID_COL + " = ?";
    private static final String SQL_GET_INTERVIEWS = "SELECT i.id\n" + "FROM \"interview\" i\n"
            + "WHERE i.id_application_form = ?";
    private static final String SQL_GET_CURRENT = "SELECT a." + ID_COL + ",  a.id_status, a.is_active,a."
            + ID_RECRUITMENT_COL + ", a." + PHOTO_SCOPE_COL + ", " + "a." + ID_USER_COL + ", a." + DATE_CREATE_COL
            + ", a." + FEEDBACK + ", s.title \n" + "FROM \"" + TABLE_NAME
            + "\" a INNER JOIN status s ON s.id = a.id_status \n"
            + "WHERE a.id_user = ? AND a.id_recruitment = (SELECT r.id FROM recruitment r WHERE r.end_date > CURRENT_DATE)";
    private static final String SQL_GET_LAST = "SELECT a." + ID_COL + ",  a.id_status, a.is_active,a."
            + ID_RECRUITMENT_COL + ", a." + PHOTO_SCOPE_COL + ", " + "a." + ID_USER_COL + ", a." + DATE_CREATE_COL
            + ", a." + FEEDBACK + ", s.title \n" + "FROM \"" + TABLE_NAME
            + "\" a INNER JOIN status s ON s.id = a.id_status \n"
            + "WHERE a.id_user = ? AND a.date_create = (SELECT MAX(a_in.date_create) from application_form a_in where a_in.id_user = ?)";
    private static final String SQL_GET_BY_INTERVIEWER = "SELECT a." + ID_COL + ",  a.id_status, a.is_active,a."
            + ID_RECRUITMENT_COL + ", a." + PHOTO_SCOPE_COL + ", " + "a." + ID_USER_COL + ", a." + DATE_CREATE_COL
            + ", a." + FEEDBACK + ", s.title \n" + "FROM \"" + TABLE_NAME
            + "\" a INNER JOIN status s ON s.id = a.id_status \n"
            + "WHERE EXISTS (SELECT i.id FROM interview i WHERE i.id_application_form = a." + ID_COL
            + " AND i.id_interviewer = ?) AND a." + IS_ACTIVE_COL + " = true AND a." + ID_STATUS_COL + " = "
            + StatusEnum.APPROVED.getId();
    private static final String SQL_GET_CURRENT_APP_FORMS_ASC = "Select a.id, a.id_status, a.is_active\n" +
            "  ,a.id_recruitment, a.photo_scope, a.id_user, a.\n" +
            "date_create, a.feedback, s.title from application_form a INNER JOIN recruitment r on a.id_recruitment = r.id\n" +
            "  INNER JOIN status s on a.id_status = s.id WHERE r.end_date > CURRENT_DATE ORDER BY ? ASC OFFSET ? LIMIT ?;";
    private static final String SQL_GET_All_APP_FORMS_SORTED = "SELECT apl.id, u.last_name, apl.id_status, apl.is_active,\n" +
            "  apl.id_recruitment, apl.photo_scope, apl.id_user, apl.date_create, apl.feedback, s.title\n" +
            "from \"user\" u INNER JOIN application_form apl on apl.id_user= u.id\n" +
            "  INNER JOIN recruitment r on apl.id_recruitment = r.id\n" +
            "  INNER JOIN status s on apl.id_status = s.id WHERE r.end_date >current_date and apl.id = ANY (\n" +
            "Select newest.applic FROM (Select DISTINCT MAX(s1.id) applic, u.id\n" +
            " from \"user\" u LEFT JOIN (SELECT a1.id id, id_status, is_active,\n" +
            "id_recruitment, photo_scope, id_user, date_create,end_date, feedback FROM application_form a1\n" +
            " INNER JOIN recruitment r1 on a1.id_recruitment = r1.id) s1 on u.id = s1.id_user\n" +
            "  GROUP BY u.id) newest)\n" +
            " ORDER BY ";
    private static final String SQL_GET_SEARCH_APP_FORMS = "Select DISTINCT s1.id , u.first_name, s1.id_status, s1.is_active,\n" +
            "s1.id_recruitment, s1.photo_scope, s1.id_user, s1.date_create, s1.feedback, s.title\n" +
            " from \"user\" u LEFT JOIN (SELECT a1.id id, id_status, is_active,\n" +
            "id_recruitment, photo_scope, id_user, date_create,end_date, feedback FROM application_form a1\n" +
            " INNER JOIN recruitment r1 on a1.id_recruitment = r1.id) s1 on u.id = s1.id_user\n" +
            " LEFT JOIN (SELECT * FROM application_form a2 \n" +
            " INNER JOIN recruitment r2 on a2.id_recruitment = r2.id) s2\n" +
            " on s1.id_user = s2.id_user AND s1.end_date < s2.end_date\n" +
            " INNER JOIN status s on s1.id_status = s.id\n" +
            "INNER JOIN user_role ur ON u.id = ur.id_user\n" +
            "WHERE (s1.end_date >current_date) and (ur.id_role = 3) AND  ((s1.id = ?) OR (u.last_name LIKE ?)) ORDER BY 2 OFFSET ? LIMIT ?;";
    private static final String SQL_QUERY_ENDING_ASC = " ASC OFFSET ? LIMIT ?;";
    private static final String SQL_QUERY_ENDING_DESC = " DESC OFFSET ? LIMIT ?;";
    private static final String SQL_GET_CURRENT_APP_FORMS_DESC = "Select a.id, a.id_status, a.is_active\n" +
            "  ,a.id_recruitment, a.photo_scope, a.id_user, a.\n" +
            "date_create, a.feedback, s.title from application_form a INNER JOIN recruitment r on a.id_recruitment = r.id\n" +
            "  INNER JOIN status s on a.id_status = s.id WHERE r.end_date > CURRENT_DATE ORDER BY ? DESC OFFSET ? LIMIT ?;";
    private static final String SQL_GET_CURRENT_APP_FORMS = "Select a.id, a.id_status, a.is_active\n" +
            "  ,a.id_recruitment, a.photo_scope, a.id_user, a.\n" +
            "date_create, a.feedback, s.title from application_form a INNER JOIN recruitment r on a.id_recruitment = r.id\n" +
            "  INNER JOIN status s on a.id_status = s.id WHERE r.end_date > CURRENT_DATE;";
    private static final String SQL_GET_CURRENT_APP_FORMS_FILTERED = "Select DISTINCT a.id, a.id_status, a.is_active"
            + "  ,a.id_recruitment, a.photo_scope, a.id_user, a."
            + "date_create, a.feedback, s.title from application_form a INNER JOIN recruitment r on a.id_recruitment = r.id"
            + "  INNER JOIN status s on a.id_status = s.id WHERE ";
    private static final String SQL_GET_APP_FORMS_FILTERED_CONDITION = "WHERE exists(SELECT 1 FROM form_answer fa\n" +
            "                INNER JOIN form_answer_variant fav on fa.id_variant = fav.id\n" +
            "                WHERE fav.answer = '?' and fa.id_question='?' and fa.id_application_form=a.id)\n" +
            "                AND";
    private static final String SQL_GET_BY_STATUS_RECRUITMENT = "SELECT a." + ID_COL + ",  a." + ID_STATUS_COL + ", a."
            + IS_ACTIVE_COL + ",a." + ID_RECRUITMENT_COL + ", a." + PHOTO_SCOPE_COL + ", " + "a." + ID_USER_COL + ", a."
            + DATE_CREATE_COL + ", a." + FEEDBACK + ", s.title \n" + "FROM \"" + TABLE_NAME
            + "\" a INNER JOIN status s ON s.id = a." + ID_STATUS_COL + "\n" + "WHERE a." + ID_STATUS_COL + " = ? AND a." + ID_RECRUITMENT_COL + " = ?";
    private static final String SQL_GET_REJECTED_AFTER_INTERVIEW = "SELECT a." + ID_COL + ",  a." + ID_STATUS_COL + ", a."
            + IS_ACTIVE_COL + ",a." + ID_RECRUITMENT_COL + ", a." + PHOTO_SCOPE_COL + ", " + "a." + ID_USER_COL + ", a."
            + DATE_CREATE_COL + ", a." + FEEDBACK + ", s.title \n" + "FROM \"" + TABLE_NAME
            + "\" a INNER JOIN status s ON s.id = a." + ID_STATUS_COL + "\n" + "WHERE a." + ID_RECRUITMENT_COL + " = ? AND (SELECT COUNT(*) FROM interview i WHERE i.id_application_form = a.id) = 2 AND a.id_status = 8";
    private static final String SQL_GET_RECRUITMENT = "SELECT a." + ID_COL + ", a." + ID_STATUS_COL + ", a." + IS_ACTIVE_COL
            + ",a." + ID_RECRUITMENT_COL + ", a." + PHOTO_SCOPE_COL + ", " + "a." + ID_USER_COL + ", a."
            + DATE_CREATE_COL + ", a." + FEEDBACK + ", s.title \n" + "FROM \"" + TABLE_NAME
            + "\" a INNER JOIN status s ON s.id = a.id_status \n" + "WHERE a.id_recruitment = ?;";
    private static final String SQL_SORT = " ORDER BY ";
    private static final String SQL_GET_COUNT_APP_FORM_STATUS = "select count(id_status) AS \"status_count\" from \"" + TABLE_NAME + "\" where id_status=? and is_active='true'";
    private static final String SQL_CHANGE_STATUS = "UPDATE application_form SET id_status = ? where id_status = ?;";
    private static final String SQL_IS_ASSIGNED = "SELECT EXISTS( SELECT i.id FROM interview i WHERE i.interviewer_role = ? AND i.id_application_form = ? )";
    private static final String SQL_GET_ALL_CURRENT_RECRUITMENT_STUDENTS = "SELECT COUNT(*) as rowcount " +
            "from application_form WHERE id_recruitment =?";
    private static final String SQL_GET_APPROVED_RECRUITMENT_STUDENTS = "SELECT COUNT(*) as rowcount " +
            "from application_form WHERE id_recruitment =? and id_status=?";
    private static final String SQL_GET_All = "SELECT a.id,  a.id_status, a.is_active,a."
            + "id_recruitment, a.photo_scope, a.id_user, a.date_create, a.feedback, s.title \n" + "FROM \""
            + TABLE_NAME + "\" a INNER JOIN status s ON s.id = a.id_status \n"
            + "WHERE a.id_user = ? AND a.id_recruitment <> (SELECT r.id FROM recruitment r WHERE r.end_date > CURRENT_DATE)";
    private static final String SQL_GET_ANSWERS = "SELECT fa.id\n FROM \"form_answer\" fa\n WHERE fa.id_application_form = ?;";
    private static Logger log = LoggerFactory.getLogger(UserDaoImpl.class.getName());
    @Autowired
    private JdbcDaoSupport jdbcDaoSupport;

    private ResultSetExtractor<ApplicationForm> extractor = resultSet -> {
        ApplicationForm applicationForm = new ApplicationForm();
        long id = resultSet.getLong(ID_COL);
        applicationForm.setActive(resultSet.getBoolean(IS_ACTIVE_COL));
        applicationForm.setAnswers(getAnswers(id));
        applicationForm.setDateCreate(resultSet.getTimestamp(DATE_CREATE_COL));
        applicationForm.setFeedback(resultSet.getString(FEEDBACK));
        applicationForm.setId(id);
        applicationForm.setRecruitment(new Recruitment(resultSet.getLong(ID_RECRUITMENT_COL)));
        if (resultSet.wasNull()) {
            applicationForm.setRecruitment(null);
        }
        applicationForm.setInterviews(getInterviews(id));
        applicationForm.setPhotoScope(resultSet.getString(PHOTO_SCOPE_COL));
        applicationForm.setStatus(new Status(resultSet.getLong(ID_STATUS_COL), resultSet.getString("title")));
        applicationForm.setUser(new User(resultSet.getLong(ID_USER_COL)));
        applicationForm.setQuestions(getQuestions(id));
        return applicationForm;
    };

    @Override
    public List<ApplicationForm> getSearchAppFormByNameFromToRows(String lastName, Long fromRows, Long rowsNum) {
        Long id = null;
        try {
            id = Long.parseLong(lastName);
        } catch (NumberFormatException e) {
            log.info("Search. Search field don`t equals id");
        }
        return jdbcDaoSupport.getJdbcTemplate().queryForList(SQL_GET_SEARCH_APP_FORMS, extractor, id, "%" + lastName + "%",
                fromRows, rowsNum);
    }

    @Override
    public ApplicationForm getById(Long id) {
        log.info("Looking for application form with id = {}", id);
        return jdbcDaoSupport.getJdbcTemplate().queryWithParameters(SQL_GET_BY_ID, extractor, id);
    }

    @Override
    public List<ApplicationForm> getByUserId(Long id) {
        log.info("Looking for application forms of user with id = {}", id);
        return jdbcDaoSupport.getJdbcTemplate().queryForList(SQL_GET_BY_USER_ID, extractor, id);
    }

    @Override
    public List<ApplicationForm> getByStatus(String status) {
        log.info("Looking for application forms with status = ", status);
        return jdbcDaoSupport.getJdbcTemplate().queryForList(SQL_GET_BY_STATUS, extractor, status);
    }

    @Override
    public List<ApplicationForm> getByState(boolean state) {
        log.info("Looking for application forms with is_active = {}", state);
        return jdbcDaoSupport.getJdbcTemplate().queryForList(SQL_GET_BY_STATE, extractor, state);
    }

    @Override
    public int deleteApplicationForm(ApplicationForm applicationForm) {
        log.info("Deleting application form with id = ", applicationForm.getId());
        return jdbcDaoSupport.getJdbcTemplate().update(SQL_DELETE, applicationForm.getId());
    }

    @Override
    public Long insertApplicationForm(ApplicationForm applicationForm, Connection connection) {
        log.info("Inserting application forms with user_id = " + applicationForm.getUser().getId());
        Recruitment recruitment = applicationForm.getRecruitment();
        return jdbcDaoSupport.getJdbcTemplate().insert(SQL_INSERT, connection, applicationForm.getStatus().getId(),
                applicationForm.isActive(), recruitment != null ? recruitment.getId() : null,
                applicationForm.getPhotoScope(), applicationForm.getUser().getId(), applicationForm.getDateCreate(),
                applicationForm.getFeedback());
    }

    @Override
    public int updateApplicationForm(ApplicationForm applicationForm) {
        log.info("Updating application forms with id = {}" + applicationForm.getId());
        Recruitment recruitment = applicationForm.getRecruitment();
        return jdbcDaoSupport.getJdbcTemplate().update(SQL_UPDATE, applicationForm.getStatus().getId(),
                applicationForm.isActive(), applicationForm.getPhotoScope(), applicationForm.getDateCreate(),
                applicationForm.getFeedback(), recruitment != null ? recruitment.getId() : null, applicationForm.getId());
    }

    @Override
    public List<ApplicationForm> getAll() {
        log.info("Get all application forms");
        return jdbcDaoSupport.getJdbcTemplate().queryForList(SQL_GET_ALL, extractor);
    }

    @Override
    public Long getCountRejectedAppForm() {
        log.info("Looking for Count Rejected AppForm");
        return jdbcDaoSupport.getJdbcTemplate().queryWithParameters(SQL_GET_COUNT_APP_FORM_STATUS, resultSet -> resultSet.getLong(1), new Long(8));
    }

    @Override
    public Long getCountToWorkAppForm() {
        log.info("Looking for Count To Work AppForm");
        return jdbcDaoSupport.getJdbcTemplate().queryWithParameters(SQL_GET_COUNT_APP_FORM_STATUS, resultSet -> resultSet.getLong(1), new Long(5));
    }

    @Override
    public Long getCountGeneralAppForm() {
        log.info("Looking for Count General AppForm");
        return jdbcDaoSupport.getJdbcTemplate().queryWithParameters(SQL_GET_COUNT_APP_FORM_STATUS, resultSet -> resultSet.getLong(1), new Long(6));
    }

    @Override
    public Long getCountAdvancedAppForm() {
        log.info("Looking for Count Advanced AppForm");
        return jdbcDaoSupport.getJdbcTemplate().queryWithParameters(SQL_GET_COUNT_APP_FORM_STATUS, resultSet -> resultSet.getLong(1), new Long(7));
    }

    private List<Interview> getInterviews(Long applicationFormId) {
        return jdbcDaoSupport.getJdbcTemplate().queryForList(SQL_GET_INTERVIEWS, (ResultSetExtractor<Interview>) resultSet -> {
            Interview interviewProxy = new Interview(resultSet.getLong("id"));
            return interviewProxy;
        }, applicationFormId);
    }

    private List<FormAnswer> getAnswers(Long applicationFormId) {
        return jdbcDaoSupport.getJdbcTemplate().queryForList(SQL_GET_ANSWERS, resultSet -> {
            FormAnswer formAnswerProxy = new FormAnswer(resultSet.getLong("id"));
            return formAnswerProxy;
        }, applicationFormId);
    }

    private List<FormQuestion> getQuestions(Long applicationFormId) {
        return jdbcDaoSupport.getJdbcTemplate().queryWithParameters("SELECT distinct q.id from form_question q join form_answer "
                + "a on (q.id= a.id_question) where a.id_application_form = ?;", resultSet -> {
            List<FormQuestion> questions = new ArrayList<>();
            do {
                questions.add(new FormQuestion(resultSet.getLong(FormQuestionDaoImpl.ID_COL)));
            } while (resultSet.next());
            return questions;
        }, applicationFormId);
    }

    @Override
    public ApplicationForm getCurrentApplicationFormByUserId(Long id) {
        log.trace("Looking for current application form with user id = {}", id);
        return jdbcDaoSupport.getJdbcTemplate().queryWithParameters(SQL_GET_CURRENT, extractor, id);
    }

    @Override
    public List<ApplicationForm> getOldApplicationFormsByUserId(Long id) {
        log.trace("Looking for current application form with user id = {}", id);
        return jdbcDaoSupport.getJdbcTemplate().queryForList(SQL_GET_All, extractor, id);
    }


    @Override
    public ApplicationForm getLastApplicationFormByUserId(Long id) {
        log.trace("Looking for last application form with user id = {}", id);
        return jdbcDaoSupport.getJdbcTemplate().queryWithParameters(SQL_GET_LAST, extractor, id, id);
    }

    @Override
    public List<ApplicationForm> getByInterviewer(User interviewer) {
        log.trace("Looking for application forms, thar are assigned for interviewer with id = {}", interviewer.getId());
        return jdbcDaoSupport.getJdbcTemplate().queryForList(SQL_GET_BY_INTERVIEWER, extractor, interviewer.getId());
    }

    @Override
    public boolean isAssignedForThisRole(ApplicationForm applicationForm, Role role) {
        return jdbcDaoSupport.getJdbcTemplate().queryWithParameters(SQL_IS_ASSIGNED, resultSet -> resultSet.getBoolean(1),
                role.getId(), applicationForm.getId());
    }

    @Override
    public int changeCurrentsAppFormStatus(Long fromIdStatus, Long toIdStatus) {
        return jdbcDaoSupport.getJdbcTemplate().update(SQL_CHANGE_STATUS, toIdStatus, fromIdStatus);
    }

    @Override
    public Long getCountRecruitmentStudents(Long id) {
        return jdbcDaoSupport.getJdbcTemplate().queryWithParameters(SQL_GET_ALL_CURRENT_RECRUITMENT_STUDENTS,
                resultSet -> resultSet.getLong("rowcount"), id);
    }

    @Override
    public Long getApprovedStudentsByRecruitmentId(Short id) {
        return jdbcDaoSupport.getJdbcTemplate().queryWithParameters(SQL_GET_APPROVED_RECRUITMENT_STUDENTS,
                resultSet -> resultSet.getLong("rowcount"), id, 3);
    }


    @Override
    public List<ApplicationForm> getCurrentApplicationForms(Long fromRow, Long rowsNum, Long sortingCol, boolean increase) {
        log.info("Looking for current application  forms");
        String sql = increase ? SQL_GET_CURRENT_APP_FORMS_ASC : SQL_GET_CURRENT_APP_FORMS_DESC;
        return jdbcDaoSupport.getJdbcTemplate().queryForList(sql, extractor, sortingCol, fromRow, rowsNum);
    }

    @Override
    public List<ApplicationForm> getCurrentApplicationForms() {
        log.info("Looking for current application  forms");
        return jdbcDaoSupport.getJdbcTemplate().queryForList(SQL_GET_CURRENT_APP_FORMS, extractor);
    }

    @Override
    public List<ApplicationForm> getApplicationFormsSorted(Long fromRow, Long rowsNum, Long sortingCol, boolean increase) {
        log.info("Looking for current application  forms sorted");
        String sql = SQL_GET_All_APP_FORMS_SORTED;
        sql += sortingCol.toString();
        sql += increase ? SQL_QUERY_ENDING_ASC : SQL_QUERY_ENDING_DESC;
        return jdbcDaoSupport.getJdbcTemplate().queryForList(sql, extractor, fromRow, rowsNum);
    }

    @Override
    public List<ApplicationForm> getCurrentApplicationFormsFiltered(Long fromRow, Long rowsNum, Long sortingCol,
                                                                    boolean increase, List<FormQuestion> questions,
                                                                    List<String> statuses, boolean isActive) {
        log.info("Looking for current filtered application forms");
        StringBuilder sbTotal = new StringBuilder();
        for (FormQuestion question : questions) {
            if (!question.getFormAnswerVariants().isEmpty()) {
                List<FormAnswerVariant> variants = question.getFormAnswerVariants();
                StringBuilder sb = new StringBuilder("(");
                sb.append("exists(SELECT 1 FROM form_answer fa " +
                        " INNER JOIN form_answer_variant fav on fa.id_variant = fav.id" +
                        " WHERE fa.id_application_form=a.id AND fa.id_question='" + question.getId()
                        + "' AND fav.answer = ANY ('{");
                for (FormAnswerVariant answerVariant : variants) {
                    sb.append(answerVariant.getAnswer() + ",");
                }
                sb.deleteCharAt(sb.length() - 1);
                sb.append("}'))) AND ");
                sbTotal.append(sb.toString());
            }
        }
        if (!statuses.isEmpty()) {
            sbTotal.append("(s.title = ANY ('{");
            for (String status : statuses) {
                sbTotal.append(status + ",");
            }
            sbTotal.deleteCharAt(sbTotal.length() - 1);
            sbTotal.append("}') )  AND");
        }
        sbTotal.append(" a.is_active = ?" + SQL_SORT);

        String sql = SQL_GET_CURRENT_APP_FORMS_FILTERED + sbTotal.toString();
        sql += sortingCol.toString();
        sql += increase ? SQL_QUERY_ENDING_ASC : SQL_QUERY_ENDING_DESC;

        return jdbcDaoSupport.getJdbcTemplate().queryForList(sql, extractor, isActive, fromRow, rowsNum);
    }

    @Override
    public Long getCountInReviewAppForm() {
        log.info("Looking for Count In review AppForm");
        return jdbcDaoSupport.getJdbcTemplate().queryWithParameters(SQL_GET_COUNT_APP_FORM_STATUS,
                resultSet -> resultSet.getLong(1), StatusEnum.IN_REVIEW.getId());
    }

    @Override
    public List<ApplicationForm> getByStatusAndRecruitment(Status status, Recruitment recruitment) {
        return jdbcDaoSupport.getJdbcTemplate().queryForList(SQL_GET_BY_STATUS_RECRUITMENT, extractor, status.getId(),
                recruitment.getId());
    }

    @Override
    public List<ApplicationForm> getByRecruitment(Recruitment recruitment) {
        return jdbcDaoSupport.getJdbcTemplate().queryForList(SQL_GET_RECRUITMENT, extractor, recruitment.getId());
    }

    @Override
    public List<ApplicationForm> getRejectedAfterInterview(Recruitment recruitment) {
        return jdbcDaoSupport.getJdbcTemplate().queryForList(SQL_GET_REJECTED_AFTER_INTERVIEW, extractor, recruitment.getId());
    }

    @Override
    public Long getCountApprovedAppForm() {
        log.info("Looking for Count of approved AppForm");
        return jdbcDaoSupport.getJdbcTemplate().queryWithParameters(SQL_GET_COUNT_APP_FORM_STATUS,
                resultSet -> resultSet.getLong(1), StatusEnum.APPROVED.getId());
    }

    @Override
    public int updateApplicationForm(ApplicationForm applicationForm, Connection connection) {
        log.info("Updating application forms with id = {}" + applicationForm.getId());
        Recruitment recruitment = applicationForm.getRecruitment();
        return jdbcDaoSupport.getJdbcTemplate().update(SQL_UPDATE, connection, applicationForm.getStatus().getId(),
                applicationForm.isActive(), applicationForm.getPhotoScope(), applicationForm.getDateCreate(),
                applicationForm.getFeedback(), recruitment != null ? recruitment.getId() : null, applicationForm.getId());
    }
}
