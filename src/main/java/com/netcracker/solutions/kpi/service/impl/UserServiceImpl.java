package com.netcracker.solutions.kpi.service.impl;

import com.google.common.collect.Sets;
import com.netcracker.solutions.kpi.controller.auth.PasswordEncoderGeneratorService;
import com.netcracker.solutions.kpi.persistence.dao.UserDao;
import com.netcracker.solutions.kpi.persistence.model.Role;
import com.netcracker.solutions.kpi.persistence.model.ScheduleTimePoint;
import com.netcracker.solutions.kpi.persistence.model.User;
import com.netcracker.solutions.kpi.persistence.repository.UserRepository;
import com.netcracker.solutions.kpi.service.EmailService;
import com.netcracker.solutions.kpi.service.UserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoderGeneratorService passwordEncoderGeneratorService;

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
    public User createUser(String email, String firstname, String secondname, String lastname, String password, Iterable<Role> roles, boolean isActive) {
        password = password == null ? RandomStringUtils.randomAlphabetic(10) : password;
        String token = RandomStringUtils.randomAlphabetic(50);
        User user = new User(email,
                firstname,
                secondname,
                lastname,
                passwordEncoderGeneratorService.encode(password),
                isActive,
                new Timestamp(System.currentTimeMillis()),
                token);
        user.setRoles(Sets.newHashSet(roles));

        userRepository.save(user);

        try{
            if (!user.isActive())
                emailService.sendRegistrationConfirmation(user.getEmail());
            else
                emailService.sendCreationNotification(user.getEmail());
        } catch (Exception e) {
            //TODO: Error Handling?
            return null;
        }
        return user;
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
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User getAuthorizedUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getDetails();
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

    // TODO: 04.08.2016
    @Override
    public List<User> getEmployeesByNameFromToRows(String name) {
        Long id = null;
        try {
            id = Long.parseLong(name);
        } catch (NumberFormatException e) {
            log.info("Search. Search field don`t equals id");
        }
        return userRepository.getEmployeesByNameFromToRows("%" + name + "%", id);
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

    @Override
    public List<User> getUserByTimeAndRole(Long scheduleTimePointId, Long roleId) {
        return userDao.getUserByTimeAndRole(scheduleTimePointId, roleId);
    }

    @Override
    public List<User> getUsersWithoutInterview(Long roleId) {
        return userDao.getUsersWithoutInterview(roleId);
    }

    @Override
    public User confirmByToken(String token) {
        User user = userRepository.getByConfirmToken(token);
        if (user == null) return null;

        user.setActive(true);
        user.setConfirmToken(null);

        return userRepository.save(user);
    }

    @Override
    public List<Integer> getCountUsersOnInterviewDaysForRole(Role role) {
        return userDao.getCountUsersOnInterviewDaysForRole(role);
    }

    @Override
    public Long insertFinalTimePoint(User user, ScheduleTimePoint scheduleTimePoint) {
        return userDao.insertFinalTimePoint(user, scheduleTimePoint);
    }
}

