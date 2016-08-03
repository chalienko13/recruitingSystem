package com.netcracker.solutions.kpi.persistence.dao;

import com.netcracker.solutions.kpi.persistence.model.Role;
import com.netcracker.solutions.kpi.persistence.model.ScheduleTimePoint;
import com.netcracker.solutions.kpi.persistence.model.User;

import java.sql.Connection;
import java.util.List;
import java.util.Set;

public interface UserDao extends GenericDAO <User, Long> {

//    User getByUsername(String username);
//
//    boolean isExist(String username);

//    List<User> getAllNotScheduleStudents();

    int[] batchUpdate(List<User> users);

    //boolean addRole(User user, Role role);

    //Seems as NOT USED
    //int deleteRole(User user, Role role);

    Long insertFinalTimePoint(User user, ScheduleTimePoint scheduleTimePoint);

    int deleteFinalTimePoint(User user, ScheduleTimePoint scheduleTimePoint);

    //User getUserByToken(String token);

    Set<User> getAssignedStudents(Long id);

    Set<User> getAllStudents();

    List<User> getStudentsFromToRows(Long fromRows, Long rowsNum, Long sortingCol, boolean increase);

    List<User> getFilteredEmployees(Long fromRows, Long rowsNum, Long sortingCol, boolean increase, Long idStart, Long idFinish, List<Role> roles, boolean interviewer, boolean notIntrviewer, boolean notEvaluated);

    List<User> getEmployeesFromToRows(Long fromRows, Long rowsNum, Long sortingCol, boolean increase);

    Set<User> getAllEmploees();

    List<Integer> getCountUsersOnInterviewDaysForRole(Role role);

    List<User> getActiveStaffByRole(Role role);

    Long getEmployeeCount();

    Long getStudentCount();

    int deleteToken(Long id);

    List<ScheduleTimePoint> getFinalTimePoints(Long id);

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
