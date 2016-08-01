package com.netcracker.solutions.kpi.persistence.dao.impl;

import com.netcracker.solutions.kpi.persistence.dao.TimePriorityTypeDao;
import com.netcracker.solutions.kpi.persistence.model.TimePriorityType;
import com.netcracker.solutions.kpi.persistence.util.JdbcTemplate;
import com.netcracker.solutions.kpi.persistence.util.ResultSetExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class TimePriorityTypeDaoImpl implements TimePriorityTypeDao {

    @Autowired
    private JdbcDaoSupport jdbcDaoSupport;

    private static Logger log = LoggerFactory.getLogger(TimePriorityTypeDaoImpl.class.getName());
    private static final String GET_BY_ID = "SELECT t.id, t.choice FROM public.time_priority_type t WHERE t.id = ?;";
    private static final String GET_ALL = "SELECT t.id, t.choice FROM public.time_priority_type t";
    private static final String GET_BY_PRIORITY = "SELECT t.id, t.choice FROM public.time_priority_type t WHERE t.choice = ?;";

    private ResultSetExtractor<TimePriorityType> extractor = resultSet -> {
        TimePriorityType timePriorityType = new TimePriorityType();
        timePriorityType.setId(resultSet.getLong("id"));
        timePriorityType.setPriority(resultSet.getString("choice"));
        return timePriorityType;
    };

  /*  public TimePriorityTypeDaoImpl(DataSource dataSource) {
        this.jdbcDaoSupport = new JdbcDaoSupport();
        jdbcDaoSupport.setJdbcTemplate(new JdbcTemplate(dataSource));
    }*/

    @Override
    public TimePriorityType getByID(Long id) {
        log.info("Looking for form Time Priority type with id  = {}", id);
        return jdbcDaoSupport.getJdbcTemplate().queryWithParameters(GET_BY_ID, extractor, id);
    }

    @Override
    public TimePriorityType getByPriority(String choice) {
        log.info("Looking for form Time Priority type with priority  = {}", choice);
        return jdbcDaoSupport.getJdbcTemplate().queryWithParameters(GET_BY_PRIORITY, extractor, choice);
    }

    @Override
    public List<TimePriorityType> getAll() {
        log.info("Get all time priorities");
        return jdbcDaoSupport.getJdbcTemplate().queryForList(GET_ALL, extractor);
    }
}
