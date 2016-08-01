package com.netcracker.solutions.kpi.persistence.dao.impl;

import com.netcracker.solutions.kpi.persistence.dao.SocialNetworkDao;
import com.netcracker.solutions.kpi.persistence.model.SocialNetwork;
import com.netcracker.solutions.kpi.persistence.util.JdbcTemplate;
import com.netcracker.solutions.kpi.persistence.util.ResultSetExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class SocialNetworkDaoImpl implements SocialNetworkDao {
    private static Logger log = LoggerFactory.getLogger(SocialNetworkDaoImpl.class.getName());



    @Autowired
    private JdbcDaoSupport jdbcDaoSupport;

    private ResultSetExtractor<SocialNetwork> extractor = resultSet -> {
        SocialNetwork socialNetwork;
        socialNetwork = new SocialNetwork(resultSet.getLong("id"), resultSet.getString("title"));
        return socialNetwork;
    };

    private static final String SQL_GET_BY_ID = "SELECT s.id, s.title" + "FROM \"social_network\" s\n"
            + "WHERE s.id = ?";

    public SocialNetworkDaoImpl() {

    }

    public SocialNetworkDaoImpl(DataSource dataSource) {
        this.jdbcDaoSupport = new JdbcDaoSupport();
        jdbcDaoSupport.setJdbcTemplate(new JdbcTemplate(dataSource));
    }

    @Override
    public SocialNetwork getByID(Long id) {
        log.info("Looking for social network with id = {}", id);
        return jdbcDaoSupport.getJdbcTemplate().queryWithParameters(SQL_GET_BY_ID, extractor, id);
    }

}
