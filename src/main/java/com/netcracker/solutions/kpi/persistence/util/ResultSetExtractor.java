package com.netcracker.solutions.kpi.persistence.util;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Chalienko on 20.04.2016.
 */
@Deprecated
public interface ResultSetExtractor<T> {
    T extractData(ResultSet resultSet) throws SQLException;
}

