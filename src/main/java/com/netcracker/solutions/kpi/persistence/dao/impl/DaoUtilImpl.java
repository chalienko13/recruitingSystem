package com.netcracker.solutions.kpi.persistence.dao.impl;

import com.netcracker.solutions.kpi.persistence.dao.DaoUtil;
import com.netcracker.solutions.kpi.persistence.util.JdbcTemplate;
import com.netcracker.solutions.kpi.persistence.util.ResultSetExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class DaoUtilImpl implements DaoUtil {

    @Autowired
    private JdbcDaoSupport jdbcDaoSupport;

    private static final String SQL_CONNECTION_TEST = "SELECT VERSION()";

    /*public DaoUtilImpl(DataSource dataSource) {
        jdbcDaoSupport = new JdbcDaoSupport();
        jdbcDaoSupport.setJdbcTemplate(new JdbcTemplate(dataSource));
    }*/

    private ResultSetExtractor<String> extractor = resultSet -> resultSet.getString("version");

    @Override
    public boolean checkConnection() {
        return null != jdbcDaoSupport.getJdbcTemplate().queryWithParameters(SQL_CONNECTION_TEST, extractor);
    }
}
