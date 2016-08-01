package com.netcracker.solutions.kpi.persistence.dao.impl;

import com.netcracker.solutions.kpi.persistence.dao.EmailTemplateDao;
import com.netcracker.solutions.kpi.persistence.model.EmailTemplate;
import com.netcracker.solutions.kpi.persistence.model.NotificationType;
import com.netcracker.solutions.kpi.persistence.util.JdbcTemplate;
import com.netcracker.solutions.kpi.persistence.util.ResultSetExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class EmailTemplateDaoImpl implements EmailTemplateDao {
    private static Logger log = LoggerFactory.getLogger(EmailTemplateDaoImpl.class.getName());

    @Autowired
    private JdbcDaoSupport jdbcDaoSupport;

    private ResultSetExtractor<EmailTemplate> extractor = resultSet -> {
        EmailTemplate emailTemplate = new EmailTemplate();
        emailTemplate.setId(resultSet.getLong(ID_COL));
        emailTemplate.setTitle(resultSet.getString(TITLE_COL));
        emailTemplate.setText(resultSet.getString(TEXT_COL));
        emailTemplate.setNotificationType(
                new NotificationType(resultSet.getLong("n_id"), resultSet.getString("n_title")));
        return emailTemplate;
    };

    static final String TABLE_NAME = "email_template";

    static final String ID_COL = "id";
    static final String TITLE_COL = "title";
    static final String N_TITLE_COL = "n_title";
    static final String TEXT_COL = "text";
    static final String ID_NOTIFICATION_TYPE_COL = "id_notification";

    private static final String GET_BY_ID = "SELECT e." + ID_COL + ", e." + TITLE_COL + ", e." + TEXT_COL + ", n.n_"
            + TITLE_COL + " " + ", n.id n_id FROM public." + TABLE_NAME + " e, public.notification_type  n "
            + "WHERE n.id = e." + ID_NOTIFICATION_TYPE_COL + " and e." + ID_COL + "=?;";
    private static final String GET_BY_TITLE = "SELECT e." + ID_COL + ", e." + TITLE_COL + ", e." + TEXT_COL + ", n.n_"
            + TITLE_COL + " " + ", n.id n_id FROM public." + TABLE_NAME + " e, public.notification_type n  "
            + "WHERE n.id = e." + ID_COL + " and n." + N_TITLE_COL + "=?;";
    private static final String GET_BY_NOTIFICATION_TYPE = "SELECT e." + ID_COL + ", e." + TITLE_COL + ", e." + TEXT_COL
            + ", n.n_" + TITLE_COL + " " + ", n.id n_id FROM public." + TABLE_NAME + " e, public.notification_type  n "
            + "WHERE n.id = e." + ID_COL + " and n.id=?;";
    private static final String UPDATE_EMAIL_TEMPLATE = "UPDATE public." + TABLE_NAME + " SET " + TITLE_COL + " = ? , "
            + TEXT_COL + " = ?" + "WHERE " + TABLE_NAME + ".id = ?;";
    private static final String DELETE_EMAIL_TEMPLATE = "DELETE FROM public." + TABLE_NAME + " WHERE id = ?;";

   /* public EmailTemplateDaoImpl(DataSource dataSource) {
        this.jdbcDaoSupport = new JdbcDaoSupport();
        jdbcDaoSupport.setJdbcTemplate(new JdbcTemplate(dataSource));
    }*/

    @Override
    public EmailTemplate getById(Long id) {
        log.info("Looking for form email template with id  = {}", id);
        return jdbcDaoSupport.getJdbcTemplate().queryWithParameters(GET_BY_ID, extractor, id);
    }

    @Override
    public EmailTemplate getByTitle(String title) {
        log.info("Looking for form email template with title  = {}", title);
        return jdbcDaoSupport.getJdbcTemplate().queryWithParameters(GET_BY_TITLE, extractor, title);
    }

    @Override
    public EmailTemplate getByNotificationType(NotificationType notificationType) {
        log.info("Looking for form email template with notificationType = {}", notificationType.getTitle());
        return jdbcDaoSupport.getJdbcTemplate().queryWithParameters(GET_BY_NOTIFICATION_TYPE, extractor,
                notificationType.getId());
    }

    @Override
    public int updateEmailTemplate(EmailTemplate emailTemplate) {
        log.info("Updating email template with id  = {}", emailTemplate.getId());
        return jdbcDaoSupport.getJdbcTemplate().update(UPDATE_EMAIL_TEMPLATE, emailTemplate.getTitle(), emailTemplate.getText(),
                emailTemplate.getId());
    }

    @Override
    public int deleteEmailTemplate(EmailTemplate emailTemplate) {
        log.info("Deleting email template with id  = {}", emailTemplate.getId());
        return jdbcDaoSupport.getJdbcTemplate().update(DELETE_EMAIL_TEMPLATE, emailTemplate.getId());
    }
}
