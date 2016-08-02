package com.netcracker.solutions.kpi.persistence.dao.impl;

import com.netcracker.solutions.kpi.persistence.dao.ScheduleTimePointDao;
import com.netcracker.solutions.kpi.persistence.model.ScheduleTimePoint;
import com.netcracker.solutions.kpi.persistence.model.TimePriorityType;
import com.netcracker.solutions.kpi.persistence.model.User;
import com.netcracker.solutions.kpi.persistence.model.UserTimePriority;
import com.netcracker.solutions.kpi.persistence.util.ResultSetExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.*;

@Repository
public class ScheduleTimePointDaoImpl implements ScheduleTimePointDao {
    private static Logger log = LoggerFactory.getLogger(ScheduleTimePointDaoImpl.class.getName());

    @Autowired
    private JdbcDaoSupport jdbcDaoSupport;

    private ResultSetExtractor<ScheduleTimePoint> extractor = resultSet -> {
        ScheduleTimePoint scheduleTimePoint = new ScheduleTimePoint();
        scheduleTimePoint.setId(resultSet.getLong("id"));
        scheduleTimePoint.setTimePoint(resultSet.getTimestamp("tp"));
        scheduleTimePoint.setUserTimePriorities(getUserTimePriority(resultSet.getLong("id"), scheduleTimePoint));
        scheduleTimePoint.setUsers(getUsersFinalInTimePoint(resultSet.getLong("id")));
        return scheduleTimePoint;
    };

 /*   public ScheduleTimePointDaoImpl(DataSource dataSource) {
        this.jdbcDaoSupport = new JdbcDaoSupport();
        jdbcDaoSupport.setJdbcTemplate(new JdbcTemplate(dataSource));
    }*/

    private static final String GET_BY_ID = "SELECT s.id, s.time_point tp FROM public.schedule_time_point s WHERE s.id = ?;";
    private static final String GET_BY_TIMEPOINT = "SELECT s.id, s.time_point tp FROM public.schedule_time_point s WHERE s.time_point = ?;";
    private static final String GET_ALL = "SELECT s.id, s.time_point tp FROM public.schedule_time_point s ORDER BY s.time_point";
    private static final String USERS_FINAL_TIME_QUERY = "SELECT u.id, u.email, u.first_name,u.last_name,u.second_name,"
            + " u.password, u.confirm_token, u.is_active, u.registration_date "
            + "FROM public.user u join public.user_time_final f on u.id=f.id_user join public.schedule_time_point s on  f.id_time_point= s.id Where s.id=?;";

    private static final String USER_TIME_PRIORITY = "select  utp.id_user, utp.id_time_point, utp.id_priority_type, "
            + "tpt.choice  from user_time_priority utp inner join time_priority_type tpt on tpt.id = utp.id_priority_type "
            + "where utp.id_time_point = ?;";

    private static final String FINAL_TIME_POINT_BY_USER_ID = "SELECT s.id, s.time_point tp FROM public.user u "
            + "join public.user_time_final f on u.id=f.id_user join public.schedule_time_point s on  f.id_time_point= s.id Where u.id=?;";

    private static final String INSERT_SCHEDULE_TIME_POINT = "INSERT INTO schedule_time_point ( time_point) VALUES (?);";

    private static final String UPDATE_SCHEDULE_TIME_POINT = "UPDATE schedule_time_point set time_point = ? WHERE id = ?;";

    private static final String DELETE_SCHEDULE_TIME_POINT = "DELETE FROM schedule_time_point WHERE id = ?";

    private static final String SQL_IS_SCHEDULE_DATES_EXIST = "SELECT EXISTS (SELECT 1 FROM schedule_time_point stp)";

    private static final String SQL_IS_SCHEDULE_EXISTS = "SELECT EXISTS (SELECT 1 FROM user_time_final utf)";

    private static final String USERS_COUNT_IN_FINAL_TIME = "SELECT sel.num as number, sel.role FROM ( SELECT ur.id_role as role, count(ur.id_role) as num FROM \"user\" u INNER JOIN user_role ur\n" +
            "    on ur.id_user=u.id INNER JOIN user_time_final f on u.id=f.id_user INNER JOIN schedule_time_point s \n" +
            "    on s.id = f.id_time_point WHERE s.time_point = ? GROUP BY ur.id_role) sel;";

    private static final String DELETE_USER_TIME_FINAL = "DELETE FROM user_time_final where id_user = ? and id_time_point=?";

    private static final String INSERT_USER_TIME_FINAL = "INSERT INTO \"user_time_final\"(id_user, id_time_point) VALUES (?,?)";

    private static final String DELETE_ALL = "DELETE FROM schedule_time_point";

    @Override
    public int[] batchInsert(List<Timestamp> timestaps) {
        log.info("Insert schedule time points");
        Object[][] objects = new Object[timestaps.size()][];
        int count = 0;
        for (Timestamp scheduleTimePoint : timestaps) {
            objects[count] = new Object[]{scheduleTimePoint};
            count++;
        }
        return jdbcDaoSupport.getJdbcTemplate().batchUpdate(INSERT_SCHEDULE_TIME_POINT, objects);
    }

    @Override
    public int deleteAll() {
        log.info("Delete all rows from schedule time point");
        return jdbcDaoSupport.getJdbcTemplate().update(DELETE_ALL);
    }

    @Override
    public ScheduleTimePoint getFinalTimePointById(Long id) {
        log.info("Looking for Schedule time Point with id = ", id);
        return jdbcDaoSupport.getJdbcTemplate().queryWithParameters(GET_BY_ID, extractor, id);
    }

    @Override
    public List<ScheduleTimePoint> getFinalTimePointByUserId(Long id) {
        log.info("Looking for Schedule time Point with User_id = ", id);
        return jdbcDaoSupport.getJdbcTemplate().queryForList(FINAL_TIME_POINT_BY_USER_ID, extractor, id);
    }

    @Override
    public Long insertScheduleTimePoint(ScheduleTimePoint scheduleTimePoint) {
        log.info("Inserting Schedule time Point with id = ", scheduleTimePoint.getId());
        return jdbcDaoSupport.getJdbcTemplate().insert(INSERT_SCHEDULE_TIME_POINT, scheduleTimePoint.getTimePoint());
    }

    @Override
    public int updateScheduleTimePoint(ScheduleTimePoint scheduleTimePoint) {
        log.info("Updating Schedule time Point with id = ", scheduleTimePoint.getId());
        return jdbcDaoSupport.getJdbcTemplate().update(UPDATE_SCHEDULE_TIME_POINT, scheduleTimePoint.getTimePoint(),
                scheduleTimePoint.getId());
    }

    @Override
    public int deleteScheduleTimePoint(ScheduleTimePoint scheduleTimePoint) {
        log.info("Deleting Schedule time Point with id = ", scheduleTimePoint.getId());
        return jdbcDaoSupport.getJdbcTemplate().update(DELETE_SCHEDULE_TIME_POINT, scheduleTimePoint.getId());
    }

    @Override
    public int deleteUserTimeFinal(User user, ScheduleTimePoint scheduleTimePoint) {
        log.info("Deleting user_time_final with user id = {}", user.getId());
        return jdbcDaoSupport.getJdbcTemplate().update(DELETE_USER_TIME_FINAL, user.getId(), scheduleTimePoint.getId());
    }

    private Set<User> getUsersFinalInTimePoint(Long timeID) {
        return jdbcDaoSupport.getJdbcTemplate().queryWithParameters(USERS_FINAL_TIME_QUERY, resultSet -> {
            Set<User> set = new HashSet<>();
            do {
                set.add(new User(resultSet.getLong("id")));
            } while (resultSet.next());
            return set;
        }, timeID);
    }

    private Set<UserTimePriority> getUserTimePriority(Long timeID, ScheduleTimePoint scheduleTimePoint) {
        return jdbcDaoSupport.getJdbcTemplate().queryWithParameters(USER_TIME_PRIORITY, resultSet -> {
            Set<UserTimePriority> set = new HashSet<>();
            do {
                set.add(new UserTimePriority(new User(resultSet.getLong("id_user")), scheduleTimePoint,
                        new TimePriorityType(resultSet.getLong("id_priority_type"), resultSet.getString("choice"))));
            } while (resultSet.next());
            return set;
        }, timeID);
    }

    @Override
    public ScheduleTimePoint getScheduleTimePointByTimepoint(Timestamp timestamp) {
        log.info("Looking for Schedule time Point with timestamp = ", timestamp);
        return jdbcDaoSupport.getJdbcTemplate().queryWithParameters(GET_BY_TIMEPOINT, extractor, timestamp);
    }

    @Override
    public List<ScheduleTimePoint> getAll() {
        log.info("Looking for all Schedule time Points");
        return jdbcDaoSupport.getJdbcTemplate().queryForList(GET_ALL, extractor);
    }

    @Override
    public boolean isScheduleExists() {
        log.info("Checking the the existence of schedule final time points.");
        return jdbcDaoSupport.getJdbcTemplate().queryWithParameters(SQL_IS_SCHEDULE_EXISTS, resultSet -> resultSet.getBoolean(1));
    }

    @Override
    public boolean isScheduleDatesExists() {
        log.info("Checking the the existence of schedule dates.");
        return jdbcDaoSupport.getJdbcTemplate().queryWithParameters(SQL_IS_SCHEDULE_DATES_EXIST, resultSet -> resultSet.getBoolean(1));
    }

    @Override
    public Map<Long, Long> getUsersNumberInFinalTimePoint(Timestamp timePoint) {
        log.info("Getting number of users in time point");
        return jdbcDaoSupport.getJdbcTemplate().queryWithParameters(USERS_COUNT_IN_FINAL_TIME, resultSet -> {
            Map<Long, Long> set = new HashMap<Long, Long>();
            do set.put(resultSet.getLong(2), resultSet.getLong(1)); while (resultSet.next());
            return set;
        }, timePoint);
    }

    @Override
    public Long addUserToTimepoint(User user, ScheduleTimePoint timePoint) {
        log.info("Adding user to final timepoint");
        return jdbcDaoSupport.getJdbcTemplate().insert(INSERT_USER_TIME_FINAL, user.getId(), timePoint.getId());
    }
}
