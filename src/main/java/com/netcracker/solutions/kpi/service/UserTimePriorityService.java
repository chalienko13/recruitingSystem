package com.netcracker.solutions.kpi.service;

import com.netcracker.solutions.kpi.persistence.dto.UserTimePriorityDto;
import com.netcracker.solutions.kpi.persistence.model.ScheduleTimePoint;
import com.netcracker.solutions.kpi.persistence.model.User;
import com.netcracker.solutions.kpi.persistence.model.UserTimePriority;

import java.util.List;
import java.util.Set;

/**
 * Created by Chalienko on 21.04.2016.
 */
public interface UserTimePriorityService {

    UserTimePriority getByUserTime(User user, ScheduleTimePoint scheduleTimePoint);

    Long insertUserPriority(UserTimePriority userTimePriority);

    int updateUserPriority(UserTimePriority userTimePriority);

    int deleteUserPriority(UserTimePriority userTimePriority);

    List<UserTimePriority> getAllUserTimePriorities(Long userId);

    int[] batchUpdateUserPriority(List<UserTimePriority> userTimePriorities);

    int[] batchCreateUserPriority(List<UserTimePriority> userTimePriorities);

    void createStudentTimePriotities(User student);

    public int[] createStaffTimePriorities(Set<User> staffList);

    boolean isSchedulePrioritiesExistStaff();

    boolean isSchedulePrioritiesExistStudent();

    List<UserTimePriorityDto> getAllTimePriorityForUserById(Long userId);

}
