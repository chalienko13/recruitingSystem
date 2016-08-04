package com.netcracker.solutions.kpi.service.impl;

import com.netcracker.solutions.kpi.persistence.dao.DataSourceSingleton;
import com.netcracker.solutions.kpi.persistence.dao.UserDao;
import com.netcracker.solutions.kpi.persistence.model.Role;
import com.netcracker.solutions.kpi.persistence.model.User;
import com.netcracker.solutions.kpi.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    /*public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }*/

    @Override
    public User getUserByUsername(String username) {
        return userDao.getByUsername(username);
    }

    @Override
    public User getUserByID(Long id) {
        return userDao.getByID(id);
    }

    @Override
    public boolean isExist(String username) {
        return userDao.isExist(username);
    }

    @Override
    public boolean insertUser(User user, List<Role> roles) {
        try (Connection connection = DataSourceSingleton.getInstance().getConnection()) {
            connection.setAutoCommit(false);
            Long generatedUserId = userDao.insertUser(user, connection);
            user.setId(generatedUserId);
            for (Role role : roles) {
                userDao.addRole(user, role, connection);
            }
            connection.commit();
        } catch (SQLException e) {
            log.error("Cannot insert user {}", e);
            return false;
        }
        return true;
    }

    @Override
    public int updateUser(User user) {
        return userDao.updateUser(user);
    }

    @Override
    public boolean updateUserWithRole(User user) {
        try (Connection connection = DataSourceSingleton.getInstance().getConnection()) {
            connection.setAutoCommit(false);
            userDao.updateUser(user, connection);
            userDao.deleteAllRoles(user, connection);
            for (Role role : user.getRoles())
                userDao.addRole(user, role, connection);
            connection.commit();
        } catch (SQLException e) {
            log.error("Cannot update user {}", e);
            return false;
        }
        return true;
    }


    @Override
    public boolean addRole(User user, Role role) {
        return userDao.addRole(user, role);
    }

    @Override
    public int deleteRole(User user, Role role) {
        return userDao.deleteRole(user, role);
    }

    @Override
    public List<User> getAllNotScheduleStudents() {
        return userDao.getAllNotScheduleStudents();
    }

    @Override
    public List<User> getActiveStaffByRole(Role role) {
        return userDao.getActiveStaffByRole(role);
    }

    @Override
    public int deleteUser(User user) {
        return userDao.deleteUser(user);
    }

    //TODO rewrite (Olesia)
    /*@Override
    public Long insertFinalTimePoint(User user, ScheduleTimePoint scheduleTimePoint) {
        return userDao.insertFinalTimePoint(user, scheduleTimePoint);
    }*/

    @Override
    public User getUserByToken(String token) {
        return userDao.getUserByToken(token);
    }

    @Override
    public Set<User> getAssignedStudents(Long id) {
        return userDao.getAssignedStudents(id);
    }

    @Override
    public Set<User> getAllStudents() {
        return userDao.getAllStudents();
    }

    @Override
    public List<User> getStudentsFromToRows(Long fromRows, Long rowsNum, Long sortingCol, boolean increase) {
        return userDao.getStudentsFromToRows(fromRows, rowsNum, sortingCol, increase);
    }

    @Override
    public List<User> getFilteredEmployees(Long fromRows, Long rowsNum, Long sortingCol, boolean increase, Long idStart,
                                           Long idFinish, List<Role> roles, boolean interviewer, boolean notIntrviewer,
                                           boolean notEvaluated) {
        return userDao.getFilteredEmployees(fromRows, rowsNum, sortingCol, increase, idStart, idFinish, roles, interviewer,
                notIntrviewer, notEvaluated);
    }

    @Override
    public int[] batchUpdate(List<User> users) {
        return userDao.batchUpdate(users);
    }

    @Override
    public List<User> getEmployeesFromToRows(Long fromRows, Long rowsNum, Long sortingCol, boolean increase) {
        return userDao.getEmployeesFromToRows(fromRows, rowsNum, sortingCol, increase);
    }

    @Override
    public Set<User> getAllEmploees() {
        return userDao.getAllEmploees();
    }

    @Override
    public Set<User> getAll() {
        return userDao.getAll();
    }

    @Override
    public User getAuthorizedUser() {
        return ((User) SecurityContextHolder.getContext().getAuthentication().getDetails());
    }

    @Override
    public List<Integer> getCountUsersOnInterviewDaysForRole(Role role) {
        return userDao.getCountUsersOnInterviewDaysForRole(role);
    }

    @Override
    public Long getAllStudentCount() {
        return userDao.getStudentCount();
    }

    @Override
    public Long getAllEmployeeCount() {
        return userDao.getEmployeeCount();
    }

    @Override
    public Long getAllEmployeeCountFiltered(Long fromRows, Long rowsNum, Long sortingCol, boolean increase, Long idStart,
                                            Long idFinish, List<Role> roles, boolean interviewer, boolean notIntrviewer,
                                            boolean notEvaluated) {
        return userDao.getEmployeeCountFiltered(fromRows, rowsNum, sortingCol, increase, idStart, idFinish, roles,
                interviewer, notIntrviewer, notEvaluated);
    }

    @Override
    public int deleteToken(Long id) {
        return userDao.deleteToken(id);
    }

    @Override
    public List<User> getEmployeesByNameFromToRows(String name) {
        return userDao.getEmployeesByNameFromToRows(name);
    }

    @Override
    public List<User> getStudentsByNameFromToRows(String lastName, Long fromRows, Long rowsNum) {
        return userDao.getStudentsByNameFromToRows(lastName, fromRows, rowsNum);
    }

    @Override
    public Long getUserCount() {
        return userDao.getUserCount();
    }

    @Override
    public Long getCountActiveEmployees(Long idRole0, Long idRole1) {
        return userDao.getActiveEmployees(idRole0, idRole1);
    }

    @Override
    public Long getCountActiveDoubleRoleEmployee() {
        return userDao.getCountActiveDoubleRoleEmployee();
    }

    @Override
    public int disableAllStaff() {
        return userDao.disableAllStaff();
    }

    @Override
    public List<User> getStudentsWithNotconnectedForms() {
        return userDao.getStudentsWithNotconnectedForms();
    }

    @Override
    public List<User> getUserByTimeAndRole(Long scheduleTimePointId, Long roleId) {
        return userDao.getUserByTimeAndRole(scheduleTimePointId, roleId);
    }

    @Override
    public List<String> getNotMarkedInterviwers() {
        return userDao.getNotMarkedInterviwers();
    }

    @Override
    public List<User> getUsersWithoutInterview(Long roleId) {
        return userDao.getUsersWithoutInterview(roleId);
    }

    @Override
    public List<User> getUserWithFinalTimePoint() {
        return userDao.getUserWithFinalTimePoint();
    }
}

