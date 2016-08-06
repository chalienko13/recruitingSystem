package com.netcracker.solutions.kpi.persistence.dao;

import com.netcracker.solutions.kpi.persistence.dto.UserTimePriorityDto;
import com.netcracker.solutions.kpi.persistence.model.ScheduleTimePoint;
import com.netcracker.solutions.kpi.persistence.model.User;
import com.netcracker.solutions.kpi.persistence.model.UserTimePriority;

import java.util.List;

/**
 * @author Korzh
 */
public interface UserTimePriorityDao {

    UserTimePriority getByUserTime(User user, ScheduleTimePoint scheduleTimePoint);

    Long insertUserPriority(UserTimePriority userTimePriority);

    int updateUserPriority(UserTimePriority userTimePriority);

    int deleteUserPriority(UserTimePriority userTimePriority);

    int[] batchUpdateUserPriority(List<UserTimePriority> userTimePriorities);

    int[] batchCreateUserPriority(List<UserTimePriority> userTimePriorities);

    List<UserTimePriority> getAllUserTimePriorities(Long userId);

    boolean isSchedulePrioritiesExistStudent();

    boolean isSchedulePrioritiesExistStaff();

    List<UserTimePriorityDto> getAllTimePriorityForUserById(Long userId);
}
