package com.netcracker.solutions.kpi.persistence.dao.impl;

import com.netcracker.solutions.kpi.persistence.dao.ScheduleDayPointDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by olil0716 on 8/4/2016.
 */
@Repository
public class ScheduleDayPointDaoImpl implements ScheduleDayPointDao {
    private static Logger log = LoggerFactory.getLogger(ScheduleDayPointDaoImpl.class.getName());

    @Autowired
    private JdbcDaoSupport jdbcDaoSupport;

    private static final String DELETE_ALL = "DELETE FROM schedule_day_point";

    @Override
    public int deleteAll() {
        log.info("Delete all rows from schedule time point");
        return jdbcDaoSupport.getJdbcTemplate().update(DELETE_ALL);
    }
}
