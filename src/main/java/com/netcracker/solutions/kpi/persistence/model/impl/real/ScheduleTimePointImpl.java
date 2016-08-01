package com.netcracker.solutions.kpi.persistence.model.impl.real;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.netcracker.solutions.kpi.persistence.model.ScheduleTimePoint;
import com.netcracker.solutions.kpi.persistence.model.User;
import com.netcracker.solutions.kpi.persistence.model.UserTimePriority;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.sql.Timestamp;
import java.util.Set;

public class ScheduleTimePointImpl implements ScheduleTimePoint {

	private static final long serialVersionUID = -8906473195615020302L;

	private Long id;

	private Timestamp timePoint;

	private Set<User> users;

	private Set<UserTimePriority> userTimePriorities;

	public ScheduleTimePointImpl() {
	}

	public ScheduleTimePointImpl(Timestamp timePoint) {
		this.timePoint = timePoint;
	}

	public ScheduleTimePointImpl(Timestamp timePoint, Set<User> users, Set<UserTimePriority> userTimePriorities) {
		this.timePoint = timePoint;
		this.users = users;
		this.userTimePriorities = userTimePriorities;
	}

	public ScheduleTimePointImpl(Long id, Timestamp timePoint, Set<User> users) {
		this.id = id;
		this.timePoint = timePoint;
		this.users = users;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public Timestamp getTimePoint() {
		return timePoint;
	}

	@Override
	public void setTimePoint(Timestamp timePoint) {
		this.timePoint = timePoint;
	}

	@Override
	public Set<User> getUsers() {
		return users;
	}

	@Override
	public void setUsers(Set<User> users) {
		this.users = users;
	}

	@JsonIgnore
	@Override
	public Set<UserTimePriority> getUserTimePriorities() {
		return userTimePriorities;
	}

	@Override
	public void setUserTimePriorities(Set<UserTimePriority> priorities) {
		this.userTimePriorities = priorities;
	}

	@Override
	public String toString() {
		return "ScheduleTimePointImpl [id=" + id + ", timePoint=" + timePoint + ", users=" + users + "]";
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(id).append(timePoint).append(users).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ScheduleTimePointImpl other = (ScheduleTimePointImpl) obj;
		return new EqualsBuilder().append(id, other.id).append(timePoint, other.timePoint).append(users, other.users)
				.isEquals();
	}

}
