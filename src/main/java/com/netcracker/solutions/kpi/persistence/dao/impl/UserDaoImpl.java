package com.netcracker.solutions.kpi.persistence.dao.impl;

import com.netcracker.solutions.kpi.persistence.dao.UserDao;
import com.netcracker.solutions.kpi.persistence.model.Role;
import com.netcracker.solutions.kpi.persistence.model.ScheduleTimePoint;
import com.netcracker.solutions.kpi.persistence.model.User;
import com.netcracker.solutions.kpi.persistence.util.ResultSetExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class UserDaoImpl implements UserDao {
    private static final int ROLE_STUDENT = 3;
    private static final int APPROVED_STATUS = 3;
    private static final String SQL_GET_ALL_NOT_SCHEDULE_STUDENTS = "SELECT u.id, u.email, u.first_name,u.last_name, u.second_name,\n" +
            "u.password, u.confirm_token, u.is_active, u.registration_date FROM \"user\" u\n" +
            "  INNER JOIN application_form af ON u.id = af.id_user\n" +
            "  INNER JOIN recruitment r ON af.id_recruitment = r.id\n" +
            "  INNER JOIN user_role ur ON u.id = ur.id_user\n" +
            "WHERE r.end_date > current_date AND af.id_status = " + APPROVED_STATUS + " AND ur.id_role = " + ROLE_STUDENT;
    private static final String SQL_GET_BY_EMAIL = "SELECT u.id, u.email, u.first_name,u.last_name,u.second_name," +
            " u.password,u.confirm_token, u.is_active, u.registration_date\n" +
            "FROM \"user\" u\n" +
            " WHERE u.email = ?;";
    private static final String SQL_EXIST = "select exists(SELECT email from \"user\" where email =?) " +
            " AS \"exist\";";

   /* private static final String SQL_GET_BY_ID = "SELECT u.id, u.email, u.first_name,u.last_name, u.second_name, " +
            "u.password, u.confirm_token, u.is_active, u.registration_date\n" +
            "FROM \"user\" u\n" +
            "WHERE u.id = ?;";*/
    private static final String SQL_INSERT = "INSERT INTO \"user\"(email, first_name," +
            " second_name, last_name, password, confirm_token, is_active, registration_date) " +
            "VALUES (?,?,?,?,?,?,?,?);";
    private static final String SQL_UPDATE = "UPDATE \"user\" SET email = ?, first_name  = ?," +
            " second_name = ?, last_name = ?, password = ?, confirm_token = ?, is_active = ?, registration_date = ?" +
            "WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM \"user\" WHERE \"user\".id = ?;";
    private static final String SQL_GET_ALL = "SELECT u.id, u.email, u.first_name,u.last_name,u.second_name," +
            " u.password,u.confirm_token, u.is_active, u.registration_date\n" +
            "FROM \"user\" u\n";
    private static final String INSERT_FINAL_TIME_POINT = "INSERT INTO user_time_final (id_user, id_time_point)" +
            " VALUES (?,?);";
    private static final String DELETE_FINAL_TIME_POINT = "DELETE FROM user_time_final p " +
            "WHERE p.id_user = ? and p.id_time_point = ?;";
    private static final String SQL_GET_FINAL_TIME_POINT = "SELECT sch.id, sch.time_point from schedule_time_point sch\n" +
            "  join user_time_final utf on sch.id = utf.id_time_point JOIN \"user\" on \"user\".id=utf.id_user where \"user\".id = ?";
    private static final String SQL_GET_USERS_BY_TOKEN = "SELECT u.id, u.email, u.first_name, u.last_name," +
            " u.second_name, u.password, u.confirm_token, u.is_active, u.registration_date\n" +
            " FROM \"user\" u  WHERE u.confirm_token = ?;";
    private static final String SQL_GET_ASSIGNED_STUDENTS_BY_EMP_ID = "SELECT u.id, u.email, u.first_name, u.last_name," +
            " u.second_name, u.password, u.confirm_token, u.is_active, u.registration_date\n" +
            " FROM \"user\" u JOIN application_form a on (a.id_user = u.id and a.is_active = 'true')  " +
            "JOIN interview i ON ( i.id_application_form = a.id )\n" +
            "  JOIN \"user\" us on (us.id = i.id_interviewer) WHERE  us.id = ?;";
    private static final String SQL_GET_ALL_STUDENTS = "SELECT u.id,u.email,u.first_name,u.last_name,u.second_name," +
            "u.password,u.confirm_token,u.is_active, u.registration_date\n" +
            "FROM \"user\" u  INNER JOIN user_role ur ON u.id = ur.id_user\n" +
            "WHERE ur.id_role = " + ROLE_STUDENT;
    private static final String SQL_GET_ALL_EMPLOYEES = "SELECT DISTINCT u.id, u.email, u.first_name,u.last_name," +
            "u.second_name, u.password,u.confirm_token, u.is_active, u.registration_date\n" +
            "FROM \"user\" u  INNER JOIN user_role ur ON u.id = ur.id_user\n" +
            "WHERE ur.id_role <>" + ROLE_STUDENT;
    private static final String SQL_GET_ALL_EMPLOYEES_FOR_ROWS = "SELECT * FROM (SELECT DISTINCT u.id, u.email, " +
            "u.first_name, u.last_name, u.second_name, u.password,u.confirm_token, u.is_active, u.registration_date" +
            " FROM \"user\" u INNER JOIN user_role ur ON u.id = ur.id_user WHERE ur.id_role <>" + ROLE_STUDENT + " )" +
            " as temp ORDER BY ";
    private static final String SQL_QUERY_ENDING_ASC = " ASC OFFSET ? LIMIT ?;";
    private static final String SQL_QUERY_ENDING_DESC = " DESC OFFSET ? LIMIT ?;";
    private static final String SQL_GET_ALL_STUDENTS_FOR_ROWS_ASK = "SELECT u.id,u.email,u.first_name,u.last_name,u.second_name," +
            "u.password,u.confirm_token,u.is_active, u.registration_date\n" +
            "FROM \"user\" u  INNER JOIN user_role ur ON u.id = ur.id_user\n" +
            "WHERE ur.id_role = " + ROLE_STUDENT + " ORDER BY ? ASC OFFSET ? LIMIT ?";
    private static final String SQL_GET_ALL_STUDENTS_FOR_ROWS_DESK = "SELECT u.id,u.email,u.first_name,u.last_name,u.second_name," +
            "u.password,u.confirm_token,u.is_active, u.registration_date\n" +
            "FROM \"user\" u  INNER JOIN user_role ur ON u.id = ur.id_user\n" +
            "WHERE ur.id_role = " + ROLE_STUDENT + " ORDER BY ? DESC OFFSET ? LIMIT ?";
    private static final String SQL_DELETE_TOKEN = "Update \"user\" SET confirm_token = NULL  where id=?";
    private static final String SQL_SEARCH_EMPLOYEE_BY_NAME = "Select * from (Select DISTINCT u.id, u.email, u.first_name, u.last_name, u.second_name, u.password,u.confirm_token, u.is_active, u.registration_date from \"user\" u  INNER JOIN user_role ur ON u.id = ur.id_user\n" +
            "WHERE (ur.id_role = 2 OR ur.id_role = 5 OR ur.id_role = 1) AND ((u.id = ?) OR (u.last_name LIKE ?))) as temp" +
            " ORDER BY 2 OFFSET 0 LIMIT 10";
    private static final String SQL_SEARCH_STUDENT_BY_LAST_NAME = "Select * from (Select DISTINCT u.id, u.email, u.first_name, u.last_name, u.second_name, u.password,u.confirm_token, u.is_active, u.registration_date from \"user\" u  INNER JOIN user_role ur ON u.id = ur.id_user\n" +
            "WHERE (ur.id_role = 3) AND  ((u.id = ?) OR (u.last_name LIKE ?))) as temp ORDER BY 2 OFFSET ? LIMIT ?";
    private static final String SQL_GET_FILTERED_EMPLOYEES_FOR_ROWS = "SELECT * FROM (SELECT DISTINCT u.id, u.email, " +
            "u.first_name, u.last_name, u.second_name, u.password, u.confirm_token, u.is_active, u.registration_date" +
            " FROM \"user\" u INNER JOIN user_role ur ON u.id = ur.id_user WHERE ur.id_role <>" + ROLE_STUDENT +
            " AND u.id >= ? AND u.id <= ? AND ur.id_role = ANY (?::int[]) AND u.is_active = ANY (?::boolean[]))" +
            " as temp ORDER BY ";
    private static final String SQL_GET_FILTERED_EMPLOYEES_FOR_ROWS_COUNT = "SELECT count(*) FROM (SELECT DISTINCT u.id, u.email, " +
            "u.first_name, u.last_name, u.second_name, u.password, u.confirm_token, u.is_active, u.registration_date" +
            " FROM \"user\" u INNER JOIN user_role ur ON u.id = ur.id_user WHERE ur.id_role <>" + ROLE_STUDENT +
            " AND u.id >= ? AND u.id <= ? AND ur.id_role = ANY(?::int[]) AND u.is_active = ANY (?::boolean[]))" +
            " as temp ORDER BY ? ASC OFFSET ? LIMIT ?;";
    private static final String SQL_GET_MAX_ID = "SELECT MAX(u.id) FROM \"user\" u;";
    private static final String SQL_GET_STUDENT_COUNT = "Select count(*) FROM application_form af\n" +
            "  JOIN recruitment r on af.id_recruitment= r.id where r.end_date > current_date;";
    private static final String SQL_GET_COUNT_ACTIVE_EMPLOYEES = "Select count(*)  FROM  \"user\" u JOIN user_role ur on ur.id_user = u.id where ur.id_role =? and\n" +
            "  ur.id_role <>? AND u.is_active=true;";
    private static final String SQL_GET_COUNT_ACTIVE_DOUBLE_ROLE_EMPLOYEES = "Select count(*)  FROM  \"user\" u JOIN user_role ur on ur.id_user = u.id where ur.id_role = 2 and\n" +
            "  ur.id_role = 5 AND u.is_active=true;";
    private static final String SQL_GET_COUNT_USERS_ON_INTERVIEW_DAYS_FOR_ROLE = "SELECT count(DISTINCT u.id), date_trunc('day', stp.time_point) AS day From \"user\" u\n" +
            "  INNER JOIN user_role ur ON u.id = ur.id_user\n" +
            "  INNER JOIN user_time_priority utf ON u.id = utf.id_user\n" +
            "  INNER JOIN  schedule_time_point stp ON utf.id_time_point = stp.id\n" +
            "WHERE ur.id_role = ?\n" +
            "GROUP BY day ORDER BY day";
    private static final String SQL_GET_INTERVIEWS = "SELECT * FROM \"user\" u\n" +
            "INNER JOIN user_role ur ON u.id = ur.id_user\n" +
            "WHERE ur.id_role = ? AND u.is_active = TRUE;";
    private static final String SQL_DISABLE_STAFF = "UPDATE \"user\" u   SET is_active = FALSE  FROM \"user_role\" ur " +
            "WHERE u.id=ur.id_user and ((ur.id_role = 2 or ur.id_role = 5) and (select  not Exists (select ur.id_user" +
            " FROM \"user_role\" ur, \"user\" us WHERE us.id=ur.id_user and  ur.id_role = 1 and us.id=u.id) ))";
    private static final String SQL_UNCONNECTED_FORMS = "SELECT u.id, u.email, u.first_name,u.last_name, u.second_name, " +
            "u.password, u.confirm_token, u.is_active, u.registration_date\n" +
            "FROM \"user\" u INNER JOIN application_form a ON a.id_user = u.id  WHERE a.id_recruitment IS NULL";
    private static final String SQL_GET_ALL_USERS_BY_TIME_POINT_ROLE = "SELECT u.id, u.email, u.first_name,u.last_name, u.second_name, u.password, u.confirm_token, u.is_active, u.registration_date FROM public.user u JOIN public.user_time_final f ON u.id = f.id_user JOIN public.user_role ur ON u.id = ur.id_user WHERE ur.id_role = ? AND f.id_time_point = ?";
    private static final String SQL_GET_WITHOUT_INTERVIEW = "SELECT * FROM \"user\" u INNER JOIN user_role ur " +
            "on ur.id_user=u.id WHERE ur.id_role = ? \n" +
            " and NOT EXISTS(SELECT 1 FROM user_time_final utf WHERE utf.id_user = u.id ) AND " +
            "(u.is_active='true' OR EXISTS(SELECT * FROM application_form af WHERE af.id_user=u.id AND af.id_status=3));\n";
    private static final String SQL_GET_NOT_MARKED_INTERVIEWERS = "SELECT DISTINCT u.email " +
            "FROM \"user\" u JOIN user_role ur ON ur.id_user = u.id " +
            "  JOIN interview ON u.id = interview.id_interviewer " +
            "  JOIN application_form af ON af.id = interview.id_application_form " +
            "WHERE ur.id_role <> 3 AND u.is_active = TRUE and af.is_active = true and interview.mark is null;";
    private static final String SQL_GET_USERS_WITH_FINAL_TIME_POINT = "Select u.id, u.email, u.first_name,u.last_name, u.second_name,\n" +
            "u.password, u.confirm_token, u.is_active, u.registration_date\n" +
            "from public.user u JOIN user_time_final uf on uf.id_user=u.id and uf.id_time_point IS NOT NULL";
    private static Logger log = LoggerFactory.getLogger(UserDaoImpl.class.getName());
    @Autowired
    private JdbcDaoSupport jdbcDaoSupport;
    private ResultSetExtractor<com.netcracker.solutions.kpi.persistence.model.User> extractor = resultSet -> {
        com.netcracker.solutions.kpi.persistence.model.User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setEmail(resultSet.getString("email"));
        user.setFirstName(resultSet.getString("first_name"));
        user.setLastName(resultSet.getString("last_name"));
        user.setSecondName(resultSet.getString("second_name"));
        user.setPassword(resultSet.getString("password"));
        user.setConfirmToken(resultSet.getString("confirm_token"));
        user.setActive(resultSet.getBoolean("is_active"));
        user.setRegistrationDate(resultSet.getTimestamp("registration_date"));
        user.setRoles(getRoles(resultSet.getLong("id")));
        return user;
    };

    @Override
    public List<Integer> getCountUsersOnInterviewDaysForRole(Role role) {
        log.info("Get count users on interview days for role {}", role.getId());
        return jdbcDaoSupport.getJdbcTemplate().queryForList(SQL_GET_COUNT_USERS_ON_INTERVIEW_DAYS_FOR_ROLE,
                resultSet -> resultSet.getInt("count"), role.getId());
    }

    @Override
    public Long insertFinalTimePoint(com.netcracker.solutions.kpi.persistence.model.User user, ScheduleTimePoint scheduleTimePoint) {
        log.info("Insert Final Time Point");
        return jdbcDaoSupport.getJdbcTemplate().insert(INSERT_FINAL_TIME_POINT, user.getId(), scheduleTimePoint.getId());
    }

    @Override
    public Set<com.netcracker.solutions.kpi.persistence.model.User> getAllEmploees() {
        log.info("Get all Employees");
        return jdbcDaoSupport.getJdbcTemplate().queryForSet(SQL_GET_ALL_EMPLOYEES, extractor);
    }

    private Set<Role> getRoles(Long userID) {
        return jdbcDaoSupport.getJdbcTemplate().queryWithParameters("SELECT ur.id_role, r.role \n" +
                "FROM user_role ur JOIN role r ON ur.id_role = r.id\n" +
                "WHERE ur.id_user = ?;", resultSet -> {
            Set<Role> roles = new HashSet<>();
            do {
                Role role = new Role();
                role.setId(resultSet.getLong("id_role"));
                role.setRoleName(resultSet.getString("role"));
                roles.add(role);
            } while (resultSet.next());
            return roles;
        }, userID);
    }

    @Override
    public List<com.netcracker.solutions.kpi.persistence.model.User> getUsersWithoutInterview(Long roleId) {
        return jdbcDaoSupport.getJdbcTemplate().queryForList(SQL_GET_WITHOUT_INTERVIEW, extractor, roleId);
    }

    @Override
    public List<com.netcracker.solutions.kpi.persistence.model.User> getUserByTimeAndRole(Long scheduleTimePointId, Long roleId) {
        return jdbcDaoSupport.getJdbcTemplate().queryForList(SQL_GET_ALL_USERS_BY_TIME_POINT_ROLE, extractor, scheduleTimePointId, roleId);
    }
}
