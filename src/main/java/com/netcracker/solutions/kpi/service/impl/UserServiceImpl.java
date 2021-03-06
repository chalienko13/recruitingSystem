package com.netcracker.solutions.kpi.service.impl;

import com.google.common.collect.Sets;
import com.netcracker.solutions.kpi.config.ApplicationConfiguration;
import com.netcracker.solutions.kpi.persistence.dao.UserDao;
import com.netcracker.solutions.kpi.persistence.dto.UserDto;
import com.netcracker.solutions.kpi.persistence.model.ApplicationForm;
import com.netcracker.solutions.kpi.persistence.model.Recruitment;
import com.netcracker.solutions.kpi.persistence.model.Role;
import com.netcracker.solutions.kpi.persistence.model.User;
import com.netcracker.solutions.kpi.persistence.model.enums.StatusEnum;
import com.netcracker.solutions.kpi.persistence.repository.RoleRepository;
import com.netcracker.solutions.kpi.persistence.repository.UserRepository;
import com.netcracker.solutions.kpi.service.ApplicationFormService;
import com.netcracker.solutions.kpi.service.EmailService;
import com.netcracker.solutions.kpi.service.RecruitmentService;
import com.netcracker.solutions.kpi.service.UserService;
import com.netcracker.solutions.kpi.util.TokenUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RecruitmentService recruitmentService;
    @Autowired
    private ApplicationFormService applicationFormService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoderGeneratorService;

    @Autowired
    TokenUtil tokenUtil;

    @Autowired
    ApplicationConfiguration configuration;

    @Override
    public User getUserByUsername(String userName) {
        User user = userRepository.getByEmail(userName);
        if(user == null) {
            throw new UsernameNotFoundException(MessageFormat.format("User with username=[{0}] not found", userName));
        }
        return user;
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
    @Transactional
    public User createUser(String email, String firstname, String secondname, String lastname, String password, Iterable<Role> roles, boolean isActive, boolean isStudent) {

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

        Set<Role> dbRoles = Sets.newHashSet();
        for (Role r : roles) {
            dbRoles.add(roleRepository.getByRoleName(r.getRoleName()));
        }
        user.setRoles(dbRoles);
        User saveUser = userRepository.save(user);

        if (isStudent){
            Recruitment recruitment = recruitmentService.getCurrentRecruitmnet();
            ApplicationForm applicationForm = new ApplicationForm();
            applicationForm.setActive(true);
            applicationForm.setUser(saveUser);
            applicationForm.setDateCreate(new Timestamp(System.currentTimeMillis()));
            applicationForm.setRecruitment(recruitment);
            applicationForm.setStatus(StatusEnum.REGISTERED.getStatus());
            applicationFormService.insertApplicationForm(applicationForm);
        }



//        if (!user.isActive())
//        {
//            user.setConfirmToken(tokenUtil.generateToken(email, configuration.tokenExpireTime));
//            userRepository.save(user);
//            emailService.sendRegistrationConfirmation(user.getEmail()); }
//        else
//        { emailService.sendCreationNotification(user.getEmail()); }
        return user;
    }

    @Override
    public void updateUser(User user) {
        userRepository.save(user);
    }

    @Override
    public boolean updateUser(UserDto userDto) {
        User user = getUserByID(userDto.getId());
        if (user != null) {
            user.setFirstName(userDto.getFirstName());
            user.setSecondName(userDto.getSecondName());
            user.setLastName(userDto.getLastName());
            user.setEmail(userDto.getEmail());
            Set<Role> userRoles = new HashSet<>();
            for (Role role : userDto.getRoleList()) {
                userRoles.add(roleRepository.getByRoleName("ROLE_" + role.getRoleName()));
            }
            user.setRoles(userRoles);
            updateUser(user);
            return true;
        } else {
            return false;
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

    //TODO rewrite (Olesia)
    /*@Override
    public Long insertFinalTimePoint(User user, ScheduleTimePoint scheduleTimePoint) {
        return userDao.insertFinalTimePoint(user, scheduleTimePoint);
    }*/

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User getAuthorizedUser() {
        org.springframework.security.core.userdetails.User login = (org.springframework.security.core.userdetails.User)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("User login: {}",login.getUsername());
        return userRepository.getByEmail(login.getUsername());
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
        return userRepository.getEmployeesByNameFromToRows(id, "'%" + name + "%'");
    }

    // TODO: 04.08.2016
    @Override
    public List<User> getEmployeesFromToRows(Long fromRows, Long rowsNum, Long sortingCol, boolean increase) {
        return userRepository.getEmployeesFromToRows(/*fromRows, rowsNum, sortingCol, increase*/);
    }

    // TODO: 04.08.2016
    @Override
    public Long getAllEmployeeCountFiltered(Long fromRows, Long rowsNum, Long sortingCol, boolean increase, Long idStart,
                                            Long idFinish, List<Role> roles, boolean interviewer, boolean notIntrviewer,
                                            boolean notEvaluated) {
        return userRepository.getEmployeeCountFiltered(/*fromRows, rowsNum, sortingCol, increase, idStart, idFinish, roles,
                interviewer, notIntrviewer, notEvaluated*/);
    }

    // TODO: 04.08.2016
    @Override
    public List<User> getFilteredEmployees(Long fromRows, Long rowsNum, Long sortingCol, boolean increase, Long idStart,
                                           Long idFinish, List<Role> roles, boolean interviewer, boolean notIntrviewer,
                                           boolean notEvaluated) {
        return userRepository.getFilteredEmployees(/*fromRows, rowsNum, sortingCol, increase, idStart, idFinish, roles, interviewer,
                notIntrviewer, notEvaluated*/);
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
        log.debug("User [{}] by confirm token: [{}]", user, token);
        if (user == null) return null;

        user.setActive(true);
        user.setConfirmToken(null);

        return userRepository.save(user);
    }

    @Override
    public List<Integer> getCountUsersOnInterviewDaysForRole(Role role) {
        return userDao.getCountUsersOnInterviewDaysForRole(role);
    }

}

