package com.netcracker.solutions.kpi.persistence.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

public interface ScheduleTimePoint extends Serializable {

	Long getId();

	void setId(Long id);

	Timestamp getTimePoint();

	void setTimePoint(Timestamp timePoint);

	Set<User> getUsers();

	void setUsers(Set<User> users);

	Set<UserTimePriority> getUserTimePriorities();

	void setUserTimePriorities(Set<UserTimePriority> priorities);


}
