package com.netcracker.solutions.kpi.persistence.dao.impl;

import com.netcracker.solutions.kpi.persistence.dao.NotificationTypeDao;
import com.netcracker.solutions.kpi.persistence.model.NotificationType;
import com.netcracker.solutions.kpi.persistence.util.JdbcTemplate;
import com.netcracker.solutions.kpi.persistence.util.ResultSetExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Set;

@Repository
public class NotificationTypeDaoImpl implements NotificationTypeDao {
    private static Logger log = LoggerFactory.getLogger(NotificationTypeDaoImpl.class.getName());

    @Autowired
    private JdbcDaoSupport jdbcDaoSupport;

    private static final String ID_COL = "id";
    private static final String TITLE_COL = "n_title";

    private static final String TABLE_NAME = "notification_type";

    private static final String SQL_GET = "SELECT n." + ID_COL + ", n." + TITLE_COL + " FROM public." + TABLE_NAME
            + "  n ";
    private static final String SQL_GET_BY_ID = SQL_GET + " WHERE n." + ID_COL + " = ?;";
    private static final String SQL_GET_BY_TITLE = SQL_GET + " WHERE n." + TITLE_COL + " = ?;";
    private static final String SQL_UPDATE_NOTIFICATION_TYPE = "UPDATE public." + TABLE_NAME + " SET " + TITLE_COL
            + " = ? " + "WHERE " + TABLE_NAME + "." + ID_COL + " = ?;";
    private static final String SQL_DELETE_NOTIFICATION_TYPE = "DELETE FROM public." + TABLE_NAME + " WHERE " + ID_COL
            + " = ?;";

    private ResultSetExtractor<NotificationType> extractor = resultSet -> {
        NotificationType notificationType = new NotificationType();
        notificationType.setId(resultSet.getLong(ID_COL));
        notificationType.setTitle(resultSet.getString(TITLE_COL));
        return notificationType;
    };

  /*  public NotificationTypeDaoImpl(DataSource dataSource) {
        this.jdbcDaoSupport = new JdbcDaoSupport();
        jdbcDaoSupport.setJdbcTemplate(new JdbcTemplate(dataSource));
    }*/

    @Override
    public NotificationType getById(Long id) {
        log.trace("Looking for form notification type with id  = ", id);
        return jdbcDaoSupport.getJdbcTemplate().queryWithParameters(SQL_GET_BY_ID, extractor, id);
    }

    @Override
    public NotificationType getByTitle(String title) {
        log.trace("Looking for form notification type with title  = ", title);
        return jdbcDaoSupport.getJdbcTemplate().queryWithParameters(SQL_GET_BY_TITLE, extractor, title);
    }

    @Override
    public int updateNotificationType(NotificationType notificationType) {
        log.trace("Updating notification type with id  = ", notificationType.getId());
        return jdbcDaoSupport.getJdbcTemplate().update(SQL_UPDATE_NOTIFICATION_TYPE, notificationType.getId());
    }

    @Override
    public int deleteNotificationType(NotificationType notificationType) {
        log.trace("Deleting notification type with id  = ", notificationType.getId());
        return jdbcDaoSupport.getJdbcTemplate().update(SQL_DELETE_NOTIFICATION_TYPE, notificationType.getId());
    }

    @Override
    public Set<NotificationType> getAll() {
        log.trace("Getting All Notification Types");
        return jdbcDaoSupport.getJdbcTemplate().queryForSet(SQL_GET, extractor);
    }

}
