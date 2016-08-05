package com.netcracker.solutions.kpi.service;

import com.netcracker.solutions.kpi.persistence.model.Role;
import com.netcracker.solutions.kpi.persistence.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Chalienko on 13.04.2016.
 */
@Service
public interface UserService {

    User getUserByUsername(String username);

    User getUserByID(Long id);

    boolean isExist(String username);

    void createUser(User user);

    void updateUser(User user);
    // // TODO: 03.08.2016
    boolean updateUserWithRole(User user);

    void addRole(User user, Role role);

    //Seems as NOT USED
    //int deleteRole(User user, Role role);

    List<User> getAllNotScheduleStudents();

    List getActiveStaffByRole(Role role);

    void deleteUser(User user);

    //TODO rewrire (Olesia)
    /*Long insertFinalTimePoint(User user, ScheduleTimePoint scheduleTimePoint);

    int deleteFinalTimePoint(User user, ScheduleTimePoint scheduleTimePoint);*/

    User getUserByToken(String token);

    //NOT USED
    //Set<User> getAssignedStudents(Long id);

    //NOT USED
    //Set<User> getAllStudents();

    //NOT USED
    //List<User> getStudentsFromToRows(Long fromRows, Long rowsNum, Long sortingCol, boolean increase);

    List<User> getEmployeesFromToRows(Long fromRows, Long rowsNum, Long sortingCol, boolean increase);

    //NOT USED
    //Set<User> getAllEmploees();

    List<User> getAll();

    List<Integer> getCountUsersOnInterviewDaysForRole(Role role);

    User getAuthorizedUser();

    Long getAllStudentCount();

    Long getAllEmployeeCount();

    Long getAllEmployeeCountFiltered(Long fromRows, Long rowsNum, Long sortingCol, boolean increase, Long idStart, Long idFinish, List<Role> roles, boolean interviewer, boolean notIntrviewer, boolean notEvaluated);

    int deleteToken(Long id);

    List<User> getEmployeesByNameFromToRows(String name);

    ////NOT USED
    //List<User> getStudentsByNameFromToRows(String lastName, Long fromRows, Long rowsNum);

    List<User> batchUpdate(List<User> users);

    List<User> getFilteredEmployees(Long fromRows, Long rowsNum, Long sortingCol, boolean increase, Long idStart, Long idFinish, List<Role> roles, boolean interviewer, boolean notIntrviewer, boolean notEvaluated);

    Long getUserCount();

    Long getCountActiveEmployees(Long idRole0, Long idRole1);

    Long getCountActiveDoubleRoleEmployee ();

    int disableAllStaff();
    
    List<User> getStudentsWithNotconnectedForms();

    List<User> getUserByTimeAndRole(Long scheduleTimePointId, Long roleId);

    List<String> getNotMarkedInterviwers();

    List<User> getUsersWithoutInterview(Long roleId);

    List<User> getUserWithFinalTimePoint();
}