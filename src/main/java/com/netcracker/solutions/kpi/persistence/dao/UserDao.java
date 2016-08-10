package com.netcracker.solutions.kpi.persistence.dao;

import com.netcracker.solutions.kpi.persistence.model.Role;
import com.netcracker.solutions.kpi.persistence.model.User;

import java.util.List;
import java.util.Set;

public interface UserDao  {
    /*Long insertFinalTimePoint(User user, ScheduleTimePoint scheduleTimePoint);*/

    Set<User> getAllEmploees();

    List<Integer> getCountUsersOnInterviewDaysForRole(Role role);

    List<User> getUserByTimeAndRole(Long scheduleTimePointId, Long roleId);

    List<User> getUsersWithoutInterview(Long roleId);

}
