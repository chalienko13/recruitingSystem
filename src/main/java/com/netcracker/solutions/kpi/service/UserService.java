package com.netcracker.solutions.kpi.service;

import com.netcracker.solutions.kpi.persistence.dto.UserDto;
import com.netcracker.solutions.kpi.persistence.model.Role;
import com.netcracker.solutions.kpi.persistence.model.ScheduleTimePoint;
import com.netcracker.solutions.kpi.persistence.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    User getUserByUsername(String username);

    User getUserByID(Long id);

    boolean isExist(String username);

    User createUser(String email, String firstname, String secondname, String lastname, String password, Iterable<Role> roles, boolean isActive);

    void updateUser(User user);

    boolean updateUser(UserDto userDto);

    List<User> getAllNotScheduleStudents();

    List getActiveStaffByRole(Role role);

    void deleteUser(User user);

    Long insertFinalTimePoint(User user, ScheduleTimePoint scheduleTimePoint);

    List<User> getEmployeesFromToRows(Long fromRows, Long rowsNum, Long sortingCol, boolean increase);

    List<User> getAll();

    List<Integer> getCountUsersOnInterviewDaysForRole(Role role);

    User getAuthorizedUser();

    Long getAllStudentCount();

    Long getAllEmployeeCount();

    Long getAllEmployeeCountFiltered(Long fromRows, Long rowsNum, Long sortingCol, boolean increase, Long idStart, Long idFinish, List<Role> roles, boolean interviewer, boolean notIntrviewer, boolean notEvaluated);

    List<User> getEmployeesByNameFromToRows(String name);

    List<User> getFilteredEmployees(Long fromRows, Long rowsNum, Long sortingCol, boolean increase, Long idStart, Long idFinish, List<Role> roles, boolean interviewer, boolean notIntrviewer, boolean notEvaluated);

    Long getUserCount();

    Long getCountActiveEmployees(Long idRole0, Long idRole1);

    Long getCountActiveDoubleRoleEmployee();

    int disableAllStaff();

    List<User> getUserByTimeAndRole(Long scheduleTimePointId, Long roleId);

    List<String> getNotMarkedInterviwers();

    List<User> getUsersWithoutInterview(Long roleId);

    User confirmByToken(String token);
}