package com.netcracker.solutions.kpi.persistence.dao.impl;

import com.netcracker.solutions.kpi.persistence.dao.DecisionDao;
import com.netcracker.solutions.kpi.persistence.model.Decision;
import com.netcracker.solutions.kpi.persistence.util.JdbcTemplate;
import com.netcracker.solutions.kpi.persistence.util.ResultSetExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class DecisionDaoImpl implements DecisionDao {
    private static Logger log = LoggerFactory.getLogger(DecisionDaoImpl.class.getName());

    @Autowired
    private JdbcDaoSupport jdbcDaoSupport;

   /* public DecisionDaoImpl(DataSource dataSource) {
        this.jdbcDaoSupport = new JdbcDaoSupport();
        jdbcDaoSupport.setJdbcTemplate(new JdbcTemplate(dataSource));
    }*/

    static final String TABLE_NAME = "decision_matrix";
    static final String SOFT_MARK_COL = "soft_mark";
    static final String TECH_MARK_COL = "tech_mark";
    static final String FINAL_MARK_COL = "final_mark";
    static final String SCALE_COL = "scale";

    private ResultSetExtractor<Decision> extractor = resultSet -> {
        Decision decision = new Decision();
        decision.setSoftMark(resultSet.getInt(SOFT_MARK_COL));
        decision.setTechMark(resultSet.getInt(TECH_MARK_COL));
        decision.setFinalMark(resultSet.getInt(FINAL_MARK_COL));
        return decision;
    };

    private static final String SQL_GET = "SELECT " + SOFT_MARK_COL + ", " + TECH_MARK_COL + ", " + FINAL_MARK_COL + ", " +
            SCALE_COL + " FROM " + TABLE_NAME;

    private static final String SQL_GET_ALL = SQL_GET + " ORDER BY " + TECH_MARK_COL + ", " + SOFT_MARK_COL;

    private static final String SQL_GET_BY_IDS = SQL_GET + " WHERE " + SOFT_MARK_COL + " = ? and " + TECH_MARK_COL
            + " = ?;";

    private static final String SQL_INSERT = "INSERT INTO " + TABLE_NAME + " (" + SOFT_MARK_COL + ", " + TECH_MARK_COL
            + ", " + FINAL_MARK_COL + ", " + SCALE_COL + ") VALUES (?,?,?,?)";

    private static final String SQL_UPDATE = "UPDATE " + TABLE_NAME + " SET " + FINAL_MARK_COL + " = ? WHERE "
            + SOFT_MARK_COL + " = ? AND " + TECH_MARK_COL + " = ?";

    private static final String SQL_DELETE = "DELETE FROM " + TABLE_NAME + " WHERE " + SOFT_MARK_COL + " = ? AND "
            + TECH_MARK_COL + " = ? AND " + FINAL_MARK_COL + " = ?;";

    private static final String SQL_TRUNCATE = "TRUNCATE TABLE " + TABLE_NAME;

    @Override
    public Decision getByMarks(int softMark, int techMark) {
        if (log.isInfoEnabled()) {
            log.info("Looking for decision with soft_mark = " + softMark + " and tech_mark = " + techMark);
        }
        return jdbcDaoSupport.getJdbcTemplate().queryWithParameters(SQL_GET_BY_IDS, extractor, softMark, techMark);
    }

    @Override
    public Long insertDecision(Decision decision) {
        if (log.isInfoEnabled()) {
            log.info("Insert decision with soft_mark = " + decision.getSoftMark() + " tech_mark = "
                    + decision.getTechMark() + " and final_mark = " + decision.getFinalMark());
        }
        return jdbcDaoSupport.getJdbcTemplate().insert(SQL_INSERT, decision.getSoftMark(), decision.getTechMark(),
                decision.getFinalMark());
    }

    @Override
    public int updateDecision(Decision decision) {
        if (log.isInfoEnabled()) {
            log.info("Update decision with soft_mark = " + decision.getSoftMark() + " and tech_mark = "
                    + decision.getTechMark());
        }
        return jdbcDaoSupport.getJdbcTemplate().update(SQL_UPDATE, decision.getFinalMark(), decision.getSoftMark(),
                decision.getTechMark());
    }

    @Override
    public int deleteDecision(Decision decision) {
        if (log.isInfoEnabled()) {
            log.info("Delete decision with soft_mark = " + decision.getSoftMark() + " tech_mark = "
                    + decision.getTechMark() + " and final_mark = " + decision.getFinalMark());
        }
        return jdbcDaoSupport.getJdbcTemplate().update(SQL_DELETE, decision.getSoftMark(), decision.getTechMark(),
                decision.getFinalMark());
    }

    @Override
    public List<Decision> getAll() {
        if (log.isInfoEnabled()) {
            log.info("Getting all decisions");
        }
        return jdbcDaoSupport.getJdbcTemplate().queryForList(SQL_GET_ALL, extractor);
    }

    @Override
    public int[] updateDecisionMatrix(List<Decision> decisionMatrix) {
        log.trace("Updating decision matrix.");
        Object[][] paramObjects = new Object[decisionMatrix.size()][3];
        int i = 0;
        for (Decision decision : decisionMatrix) {
            paramObjects[i][0] = decision.getFinalMark();
            paramObjects[i][1] = decision.getSoftMark();
            paramObjects[i++][2] = decision.getTechMark();
        }
        return jdbcDaoSupport.getJdbcTemplate().batchUpdate(SQL_UPDATE, paramObjects);
    }
}
