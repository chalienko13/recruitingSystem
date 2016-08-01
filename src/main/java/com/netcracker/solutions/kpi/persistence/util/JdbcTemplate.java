package com.netcracker.solutions.kpi.persistence.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

@Deprecated
@Component
public class JdbcTemplate {
    private static Logger log = LoggerFactory.getLogger(JdbcTemplate.class.getName());

    private DataSource dataSource;

    @Autowired
    public JdbcTemplate(DataSource dataSource) {
        this.dataSource = dataSource;
    }

   /* public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }*/

    public int update(String sql, Object... objects) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            int rowNum = 1;
            for (Object object : objects) {
                statement.setObject(rowNum++, object);
            }
            return statement.executeUpdate();
        } catch (SQLException e) {
            log.error("Cannot update objects ", e);
            return 0;
        }
    }

    public int update(String sql, Connection connection, Object... objects) {
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            int rowNum = 1;
            for (Object object : objects) {
                statement.setObject(rowNum++, object);
            }
            return statement.executeUpdate();
        } catch (SQLException e) {
            log.error("Cannot update objects ", e);
            return 0;
        }
    }

    public Long insert(String sql, Object... objects) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            return insert(statement, objects);
        } catch (SQLException e) {
            log.error("Cannot insert objects ", e);
            return 0L;
        }
    }

    public Long insert(String sql, Connection connection, Object... objects) {
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            return insert(statement, objects);
        } catch (SQLException e) {
            log.error("Cannot insert objects ", e);
            return 0L;
        }
    }

    public <T> List<T> queryForList(String sql, ResultSetExtractor<T> resultSetExtractor, Object... objects) {
        return (List<T>) queryForCollection(new ArrayList<>(), sql, resultSetExtractor, (Object[]) objects);
    }

    private <T> Collection<T> queryForCollection(Collection<T> collection, String sql,
                                                 ResultSetExtractor<T> resultSetExtractor, Object... objects) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            int rowNum = 1;
            for (Object object : objects) {
                statement.setObject(rowNum++, object);
            }
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                collection.add(resultSetExtractor.extractData(resultSet));
            }
            return collection;
        } catch (SQLException e) {
            log.error("Cannot read objects ", e);
        }
        return collection;
    }

    public <T> Set<T> queryForSet(String sql, ResultSetExtractor<T> resultSetExtractor, Object... objects) {
        return (Set<T>) queryForCollection(new HashSet<>(), sql, resultSetExtractor, (Object[]) objects);
    }

    private Long insert(PreparedStatement statement, Object... objects) {
        int rowNum = 1;
        try {
            for (Object object : objects) {
                statement.setObject(rowNum++, object);
            }
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getLong(1);
            }
        } catch (SQLException e) {
            log.error("Cannot insert object ", e);
        }
        return 0L;
    }

    public int[] batchUpdate(String sql, Object[][] objects) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
           int rowNum = 1;
            for (Object[] objectsForSql : objects) {
                for (Object object : objectsForSql) {
                    statement.setObject(rowNum++, object);
                }
                statement.addBatch();
                rowNum = 1;
            }
            return statement.executeBatch();
        } catch (SQLException e) {
            log.error("Cannot make bunch update {}", e);
            return new int[]{};
        }
    }

    public <T> T queryWithParameters(String sql, ResultSetExtractor<T> resultSetExtractor, Object... objects) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            int rowNum = 1;
            for (Object object : objects) {
                statement.setObject(rowNum++, object);
            }
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSetExtractor.extractData(resultSet);
            }
        } catch (SQLException e) {
            log.error("Cannot read object ", e);
        }
        return null;
    }
}
