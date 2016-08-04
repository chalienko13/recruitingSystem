package com.netcracker.solutions.kpi.persistence.repository;

import com.netcracker.solutions.kpi.persistence.model.Role;
import com.netcracker.solutions.kpi.persistence.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User getByEmail(String email);

    User getByConfirmToken(String confirmToken);

    @Query(value = "select count(u.email) > 0 from \"user\" u where u.email = ?1", nativeQuery = true)
    Boolean isExistByEmail(String username);

    @Query(value = "SELECT * FROM \"user\" u \n" +
            "INNER JOIN user_role ur ON u.id = ur.id_user \n" +
            "WHERE ur.id_role = ?1 AND u.is_active = TRUE;", nativeQuery = true)
    List<User> getActiveStaffByRole(Long idRole);

    @Query("SELECT u FROM User u " +
            "JOIN ApplicationForm af " +
                "ON u.id = af.user.id " +
            "JOIN Recruitment r " +
                "ON af.recruitment.id = r.id " +
            "JOIN u.roles ur " +
            "WHERE r.endDate > current_date " +
            "AND af.status = 3 AND ur.id = 3")
    List<User> getAllNotScheduleStudents();


    @Query(value = "Select count(*) FROM application_form af\n" +
            "  JOIN recruitment r on af.id_recruitment= r.id where r.end_date > current_date;", nativeQuery = true)
    Long getStudentsCount();

    @Query(value = "SELECT count(*) FROM (SELECT DISTINCT u.id, u.email, u.first_name,u.last_name,\n" +
            "  u.second_name, u.password,u.confirm_token, u.is_active, u.registration_date\n" +
            "FROM \"user\" u  INNER JOIN user_role ur ON u.id = ur.id_user\n" +
            "WHERE ur.id_role <>3) AS staff", nativeQuery = true)
    Long getEmployeeCount();

    @Query(value = "Update \"user\" SET confirm_token = NULL  where id = ?1", nativeQuery = true)
    int deleteToken(Long id);

    @Query(value = "UPDATE \"user\" u   SET is_active = FALSE  FROM \"user_role\" ur \n" +
            "WHERE u.id=ur.id_user and ((ur.id_role = 2 or ur.id_role = 5) and (select  not Exists (select ur.id_user\n" +
            " FROM \"user_role\" ur, \"user\" us WHERE us.id=ur.id_user and  ur.id_role = 1 and us.id=u.id) ))", nativeQuery = true)
    int disableAllStaff();

    @Query(value = "SELECT DISTINCT u.email \n" +
            "FROM \"user\" u JOIN user_role ur ON ur.id_user = u.id \n" +
            "  JOIN interview ON u.id = interview.id_interviewer \n" +
            "  JOIN application_form af ON af.id = interview.id_application_form \n" +
            "WHERE ur.id_role <> 3 AND u.is_active = TRUE and af.is_active = true and interview.mark is null;", nativeQuery = true)
    List<String> getNotMarkedInterviwers();

    @Query(value = "Select count(*)  FROM  \"user\" u JOIN user_role ur on ur.id_user = u.id where ur.id_role = 2 and\n" +
            "  ur.id_role = 5 AND u.is_active=true;", nativeQuery = true)
    Long getCountActiveDoubleRoleEmployee();

    @Query(value = "Select count(*)  FROM  \"user\" u JOIN user_role ur on ur.id_user = u.id where ur.id_role =?1 and\n" +
            "  ur.id_role <> ?2 AND u.is_active=true;", nativeQuery = true)
    Long getActiveEmployees(Long idRole0, Long idRole1);

    @Query(value = "SELECT u.id, u.email, u.first_name,u.last_name, u.second_name, \n" +
            "u.password, u.confirm_token, u.is_active, u.registration_date\n" +
            "FROM \"user\" u INNER JOIN application_form a ON a.id_user = u.id  WHERE a.id_recruitment IS NULL",
            nativeQuery = true)
    List<User> getStudentsWithNotconnectedForms();

    @Query(value = "Select * from (Select DISTINCT u.id, u.email, u.first_name, u.last_name, u.second_name, u.password," +
            "u.confirm_token, u.is_active, u.registration_date from \"user\" u  INNER JOIN user_role ur ON u.id = ur.id_user\n" +
            "WHERE (ur.id_role = 2 OR ur.id_role = 5 OR ur.id_role = 1) AND ((u.id = ?2) OR (u.last_name LIKE ?1))) as temp\n" +
            " ORDER BY 2 OFFSET 0 LIMIT 10", nativeQuery = true)
    List<User> getEmployeesByNameFromToRows(String lastName, Long id);

    // TODO: 04.08.2016
    @Query(value = "select * from \"user\"", nativeQuery = true)
    List<User> getEmployeesFromToRows(Long fromRows, Long rowsNum, Long sortingCol, boolean increase);

    // TODO: 04.08.2016
    @Query(value = "select count(*) from \"user\"", nativeQuery = true)
    Long getEmployeeCountFiltered(Long fromRows, Long rowsNum, Long sortingCol, boolean increase, Long idStart,
                                  Long idFinish, List<Role> roles, boolean interviewer, boolean notIntrviewer, boolean notEvaluated);

    // TODO: 04.08.2016
    @Query(value = "select * from \"user\"", nativeQuery = true)
    List<User> getFilteredEmployees(Long fromRows, Long rowsNum, Long sortingCol, boolean increase, Long idStart,
                                    Long idFinish, List<Role> roles, boolean interviewer, boolean notIntrviewer, boolean notEvaluated);
}
