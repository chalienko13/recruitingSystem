package com.netcracker.solutions.kpi.service.impl;

import com.netcracker.solutions.kpi.persistence.dao.UserTimePriorityDao;
import com.netcracker.solutions.kpi.persistence.dto.UserTimePriorityDto;
import com.netcracker.solutions.kpi.persistence.model.ScheduleTimePoint;
import com.netcracker.solutions.kpi.persistence.model.TimePriorityType;
import com.netcracker.solutions.kpi.persistence.model.User;
import com.netcracker.solutions.kpi.persistence.model.UserTimePriority;
import com.netcracker.solutions.kpi.persistence.model.enums.TimePriorityTypeEnum;
import com.netcracker.solutions.kpi.service.ScheduleTimePointService;
import com.netcracker.solutions.kpi.service.TimePriorityTypeService;
import com.netcracker.solutions.kpi.service.UserTimePriorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class UserTimePriorityServiceImpl implements UserTimePriorityService {

    @Autowired
    private UserTimePriorityDao userTimePriorityDao;

    @Autowired
    private ScheduleTimePointService timePointService;// = ServiceFactory.getScheduleTimePointService();

    @Autowired
    private TimePriorityTypeService priorityTypeService;// = ServiceFactory.getTimePriorityTypeService();

/*    private TimePriorityType defaultPriorityType = priorityTypeService.getByID(TimePriorityTypeEnum.CAN.getId());*/

    /*public UserTimePriorityServiceImpl(UserTimePriorityDao userTimePriorityDao) {
        this.userTimePriorityDao = userTimePriorityDao;
    }*/

    @Override
    public UserTimePriority getByUserTime(User user, ScheduleTimePoint scheduleTimePoint) {
        return userTimePriorityDao.getByUserTime(user, scheduleTimePoint);
    }

    @Override
    public Long insertUserPriority(UserTimePriority userTimePriority) {
        return userTimePriorityDao.insertUserPriority(userTimePriority);
    }

    @Override
    public int updateUserPriority(UserTimePriority userTimePriority) {
        return userTimePriorityDao.updateUserPriority(userTimePriority);
    }

    @Override
    public int deleteUserPriority(UserTimePriority userTimePriority) {
        return userTimePriorityDao.deleteUserPriority(userTimePriority);
    }

    @Override
    public List<UserTimePriority> getAllUserTimePriorities(Long userId) {
        return userTimePriorityDao.getAllUserTimePriorities(userId);
    }

    @Override
    public int[] batchUpdateUserPriority(List<UserTimePriority> userTimePriorities) {
        return userTimePriorityDao.batchUpdateUserPriority(userTimePriorities);
    }

    @Override
    public int[] batchCreateUserPriority(List<UserTimePriority> userTimePriorities) {
        return userTimePriorityDao.batchCreateUserPriority(userTimePriorities);
    }

    @Override
    public int[] createStaffTimePriorities(Set<User> staffList) {
        TimePriorityType defaultPriorityType = priorityTypeService.getByID(TimePriorityTypeEnum.CAN.getId());
        List<ScheduleTimePoint> timePoints = timePointService.getAll();
        List<UserTimePriority> staffTimePriorities = new ArrayList<>();
        for (ScheduleTimePoint timePoint : timePoints) {
            for (User staff : staffList) {
                UserTimePriority staffPriority = new UserTimePriority();
                staffPriority.setUser(staff);
                staffPriority.setScheduleTimePoint(timePoint);
                staffPriority.setTimePriorityType(defaultPriorityType);
                staffTimePriorities.add(staffPriority);
            }
        }
        return userTimePriorityDao.batchCreateUserPriority(staffTimePriorities);
    }


    @Override
    public void createStudentTimePriotities(User student) {
        TimePriorityType defaultPriorityType = priorityTypeService.getByID(TimePriorityTypeEnum.CAN.getId());
        List<ScheduleTimePoint> timePoints = timePointService.getAll();
        for (ScheduleTimePoint scheduleTimePoint : timePoints) {
            UserTimePriority userTimePriority = new UserTimePriority(student, scheduleTimePoint, defaultPriorityType);
            insertUserPriority(userTimePriority);
        }
    }

    @Override
    public boolean isSchedulePrioritiesExistStaff() {
        return userTimePriorityDao.isSchedulePrioritiesExistStaff();
    }

    @Override
    public boolean isSchedulePrioritiesExistStudent() {
        return userTimePriorityDao.isSchedulePrioritiesExistStudent();
    }

    @Override
    public List<UserTimePriorityDto> getAllTimePriorityForUserById(Long userId) {
        return userTimePriorityDao.getAllTimePriorityForUserById(userId);
    }
}
