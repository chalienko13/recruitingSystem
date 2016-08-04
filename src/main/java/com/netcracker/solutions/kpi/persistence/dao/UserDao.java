package com.netcracker.solutions.kpi.persistence.dao;

import com.netcracker.solutions.kpi.persistence.model.Role;
import com.netcracker.solutions.kpi.persistence.model.User;

import java.sql.Connection;
import java.util.List;
import java.util.Set;

/**
 * Created by Chalienko on 13.04.2016.
 */
public interface UserDao {

    User getByUsername(String username);

    User getByID(Long id);

    boolean isExist(String username);

    int deleteUser(User user);

    Long insertUser(User user, Connection connection);

    List<User> getAllNotScheduleStudents();

    int[] batchUpdate(List<User> users);

    int updateUser(User user);

    int updateUser(User user, Connection connection);

    boolean addRole(User user, Role role);

    boolean addRole(User user, Role role, Connection connection);

    int deleteRole(User user, Role role);

    int deleteAllRoles(User user, Connection connection);

    //TODO rewrite (Olesia)

    /*Long insertFinalTimePoint(User user, ScheduleTimePoint scheduleTimePoint);

    int deleteFinalTimePoint(User user, ScheduleTimePoint scheduleTimePoint);*/

    User getUserByToken(String token);

    Set<User> getAssignedStudents(Long id);

    Set<User> getAllStudents();

    List<User> getStudentsFromToRows(Long fromRows, Long rowsNum, Long sortingCol, boolean increase);

    List<User> getFilteredEmployees(Long fromRows, Long rowsNum, Long sortingCol, boolean increase, Long idStart, Long idFinish, List<Role> roles, boolean interviewer, boolean notIntrviewer, boolean notEvaluated);

    List<User> getEmployeesFromToRows(Long fromRows, Long rowsNum, Long sortingCol, boolean increase);

    Set<User> getAllEmploees();

    List<Integer> getCountUsersOnInterviewDaysForRole(Role role);

    List<User> getActiveStaffByRole(Role role);

    Set<User> getAll();

    Long getEmployeeCount();

    Long getStudentCount();

    int deleteToken(Long id);

    //List<ScheduleTimePoint> getFinalTimePoints(Long id);

    List<User> getEmployeesByNameFromToRows(String name);

    List<User> getStudentsByNameFromToRows(String lastName, Long fromRows, Long rowsNum);

    Long getUserCount();

    Long getActiveEmployees(Long idParam0,Long idParam1);

    Long getCountActiveDoubleRoleEmployee();

    Long getEmployeeCountFiltered(Long fromRows, Long rowsNum, Long sortingCol, boolean increase, Long idStart, Long idFinish, List<Role> roles, boolean interviewer, boolean notIntrviewer, boolean notEvaluated);

    int disableAllStaff();

	List<User> getStudentsWithNotconnectedForms();

    List<User> getUserByTimeAndRole(Long scheduleTimePointId, Long roleId);

    public List<String> getNotMarkedInterviwers();

    List<User> getUsersWithoutInterview(Long roleId);

    List<User> getUserWithFinalTimePoint();
}
