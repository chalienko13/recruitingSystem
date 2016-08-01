package com.netcracker.solutions.kpi.persistence.dao.impl;

import com.netcracker.solutions.kpi.persistence.dao.SchedulingSettingsDao;
import com.netcracker.solutions.kpi.persistence.model.SchedulingSettings;
import com.netcracker.solutions.kpi.persistence.util.JdbcTemplate;
import com.netcracker.solutions.kpi.persistence.util.ResultSetExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class SchedulingSettingsDaoImpl implements SchedulingSettingsDao {
    private static Logger log = LoggerFactory.getLogger(SchedulingSettingsDaoImpl.class.getName());

    @Autowired
    private JdbcDaoSupport jdbcDaoSupport;

    private ResultSetExtractor<SchedulingSettings> extractor = resultSet -> {
        SchedulingSettings schedulingSettings = new SchedulingSettings();
        schedulingSettings.setId(resultSet.getLong("id"));
        schedulingSettings.setStartDate(resultSet.getTimestamp("start_time"));
        schedulingSettings.setEndDate(resultSet.getTimestamp("end_time"));
        return schedulingSettings;
    };

  /*  public SchedulingSettingsDaoImpl(DataSource dataSource) {
        this.jdbcDaoSupport = new JdbcDaoSupport();
        jdbcDaoSupport.setJdbcTemplate(new JdbcTemplate(dataSource));
    }*/

    private static final String GET_BY_ID = "select ss.id, ss.start_time, ss.end_time from scheduling_settings ss where id = ?;";
    private static final String INSERT_TIME_RANGE = "INSERT INTO scheduling_settings (start_time, end_time) VALUES (?,?);";
    private static final String UPDATE_TIME_RANGE = "UPDATE scheduling_settings set start_time = ?, end_time = ? WHERE id = ?;";
    private static final String DELETE_TIME_RANGE = "DELETE FROM scheduling_settings WHERE id = ?;";
    private static final String GET_ALL = "SELECT  ss.id, ss.start_time, ss.end_time FROM scheduling_settings ss ORDER BY start_time;";
    private static final String DELETE_ALL = "DELETE FROM scheduling_settings";


    @Override
    public SchedulingSettings getById(Long id) {
        log.info("Looking for Scheduling Setting with id = {} ", id);
        return jdbcDaoSupport.getJdbcTemplate().queryWithParameters(GET_BY_ID, extractor, id);
    }

    @Override
    public int deleteAll() {
        log.info("Delete all rows from scheduling settings");
        return jdbcDaoSupport.getJdbcTemplate().update(DELETE_ALL);
    }

    @Override
    public Long insertTimeRange(SchedulingSettings schedulingSettings) {
        log.info("Inserting Scheduling Setting with startDate = {} ", schedulingSettings.getStartDate());
        return jdbcDaoSupport.getJdbcTemplate().insert(INSERT_TIME_RANGE, schedulingSettings.getStartDate(),
                schedulingSettings.getEndDate());
    }

    @Override
    public int updateTimeRange(SchedulingSettings schedulingSettings) {
        log.info("Updating Scheduling Setting with id = {} ", schedulingSettings.getId());
        return jdbcDaoSupport.getJdbcTemplate().update(UPDATE_TIME_RANGE, schedulingSettings.getStartDate(), schedulingSettings.getEndDate(), schedulingSettings.getId());
    }

    @Override
    public int deleteTimeRange(Long id) {
        log.info("Delete Scheduling Setting with id = {}", id);
        return jdbcDaoSupport.getJdbcTemplate().update(DELETE_TIME_RANGE, id);
    }

    @Override
    public List<SchedulingSettings> getAll() {
        log.info("Getting All of Scheduling Settings ");
        return jdbcDaoSupport.getJdbcTemplate().queryForList(GET_ALL, extractor);
    }
}
