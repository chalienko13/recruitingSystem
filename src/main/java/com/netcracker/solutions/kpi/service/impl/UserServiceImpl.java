package com.netcracker.solutions.kpi.service.impl;

import com.netcracker.solutions.kpi.persistence.dao.UserDao;
import com.netcracker.solutions.kpi.persistence.model.Role;
import com.netcracker.solutions.kpi.persistence.model.ScheduleTimePoint;
import com.netcracker.solutions.kpi.persistence.model.User;
import com.netcracker.solutions.kpi.persistence.repository.UserRepository;
import com.netcracker.solutions.kpi.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserRepository userRepository;

    ///REFACTORED TO REPOSITORY - START
    ///--------------------------------

    @Override
    public User getUserByUsername(String userName) {
        return userRepository.getByEmail(userName);
    }

    @Override
    public User getUserByID(Long id) {
        return userRepository.getOne(id);
    }

    @Override
    public boolean isExist(String username) {
        return userRepository.isExistByEmail(username);
    }

    @Override
    public void createUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void updateUser(User user) {
        userRepository.save(user);
    }

    //// TODO: 03.08.2016
    @Override
    public boolean updateUserWithRole(User user) {
        updateUser(user);
        return true;
    }

    @Override
    public void addRole(User user, Role role) {
        if(role != null) {
            Set<Role> currentRoles = user.getRoles();
            currentRoles.add(role);
            updateUser(user);
        }
    }

    @Override
    public Long getUserCount() {
        return userRepository.count();
    }

    @Override
    public List<User> getAllNotScheduleStudents() {
        return userRepository.getAllNotScheduleStudents();
    }

    @Override
    public List<User> getActiveStaffByRole(Role role) {
        if (role != null) {
            return userRepository.getActiveStaffByRole(role.getId());
        } else {
            return Collections.EMPTY_LIST;
        }
    }

    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    @Override
    public User getUserByToken(String token) {
        return userRepository.getByConfirmToken(token);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User getAuthorizedUser() {
        return ((User) SecurityContextHolder.getContext().getAuthentication().getDetails());
    }

    @Override
    public Long getAllStudentCount() {
        return userRepository.getStudentsCount();
    }

    @Override
    public Long getAllEmployeeCount() {
        return userRepository.getEmployeeCount();
    }

    @Override
    public int deleteToken(Long id) {
        return userRepository.deleteToken(id);
    }

    @Override
    public int disableAllStaff() {
        return userRepository.disableAllStaff();
    }

    @Override
    public List<String> getNotMarkedInterviwers() {
        return userRepository.getNotMarkedInterviwers();
    }

    @Override
    public Long getCountActiveDoubleRoleEmployee() {
        return userRepository.getCountActiveDoubleRoleEmployee();
    }

    @Override
    public Long getCountActiveEmployees(Long idRole0, Long idRole1) {
        return userRepository.getActiveEmployees(idRole0, idRole1);
    }

    @Override
    public List<User> getStudentsWithNotconnectedForms() {
        return userRepository.getStudentsWithNotconnectedForms();
    }

    // TODO: 04.08.2016
    @Override
    public List<User> getEmployeesByNameFromToRows(String name) {
        Long id = null;
        try {
            id = Long.parseLong(name);
        } catch (NumberFormatException e) {
            log.info("Search. Search field don`t equals id");
        }
        return userRepository.getEmployeesByNameFromToRows("%" + name + "%",id);
    }

    @Override
    public List<User> batchUpdate(List<User> users) {
        return userRepository.save(users);
    }

    // TODO: 04.08.2016
    @Override
    public List<User> getEmployeesFromToRows(Long fromRows, Long rowsNum, Long sortingCol, boolean increase) {
        return userRepository.getEmployeesFromToRows(fromRows, rowsNum, sortingCol, increase);
    }

    // TODO: 04.08.2016
    @Override
    public Long getAllEmployeeCountFiltered(Long fromRows, Long rowsNum, Long sortingCol, boolean increase, Long idStart,
                                            Long idFinish, List<Role> roles, boolean interviewer, boolean notIntrviewer,
                                            boolean notEvaluated) {
        return userRepository.getEmployeeCountFiltered(fromRows, rowsNum, sortingCol, increase, idStart, idFinish, roles,
                interviewer, notIntrviewer, notEvaluated);
    }

    // TODO: 04.08.2016
    @Override
    public List<User> getFilteredEmployees(Long fromRows, Long rowsNum, Long sortingCol, boolean increase, Long idStart,
                                           Long idFinish, List<Role> roles, boolean interviewer, boolean notIntrviewer,
                                           boolean notEvaluated) {
        return userRepository.getFilteredEmployees(fromRows, rowsNum, sortingCol, increase, idStart, idFinish, roles, interviewer,
                notIntrviewer, notEvaluated);
    }

    ///REFACTORED TO REPOSITORY - END
    ///--------------------------------------------------

    //SCHEDULING - not necessary to refactor now as scheduling will be changed

    @Override
    public List<User> getUserByTimeAndRole(Long scheduleTimePointId, Long roleId) {
        return userDao.getUserByTimeAndRole(scheduleTimePointId, roleId);
    }

    @Override
    public List<User> getUsersWithoutInterview(Long roleId) {
        return userDao.getUsersWithoutInterview(roleId);
    }

    @Override
    public List<User> getUserWithFinalTimePoint() {
        return userDao.getUserWithFinalTimePoint();
    }

    @Override
    public List<Integer> getCountUsersOnInterviewDaysForRole(Role role) {
        return userDao.getCountUsersOnInterviewDaysForRole(role);
    }

    @Override
    public Long insertFinalTimePoint(User user, ScheduleTimePoint scheduleTimePoint) {
        return userDao.insertFinalTimePoint(user, scheduleTimePoint);
    }

    @Override
    public int deleteFinalTimePoint(User user, ScheduleTimePoint scheduleTimePoint) {
        return userDao.deleteFinalTimePoint(user, scheduleTimePoint);
    }
    /////---------------------------------------------------------------------------


    //NOT USED
   /* @Override
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
*/

    /*// TODO: 03.08.2016 - Seems as NOT USED
  @Override
  public int deleteRole(User user, Role role) {
      return userDao.deleteRole(user, role);
  }*/

    //NOT USED
   /* @Override
    public Set<User> getAllEmploees() {
        return userDao.getAllEmploees();
    }
*/

    //NOT USED
   /* @Override
    public List<User> getStudentsByNameFromToRows(String lastName, Long fromRows, Long rowsNum) {
        return userDao.getStudentsByNameFromToRows(lastName, fromRows, rowsNum);
    }*/
}

