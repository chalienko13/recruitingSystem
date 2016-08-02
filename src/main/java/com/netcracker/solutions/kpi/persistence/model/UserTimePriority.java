package com.netcracker.solutions.kpi.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user_time_priority")
public class UserTimePriority implements Serializable {

	private static final long serialVersionUID = 4644874842084608413L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_user")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_time_point")
	private ScheduleTimePoint scheduleTimePoint;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_priority_type")
	private TimePriorityType timePriorityType;

	public UserTimePriority() {
	}

	public UserTimePriority(User user, ScheduleTimePoint scheduleTimePoint, TimePriorityType timePriorityType) {
		this.user = user;
		this.scheduleTimePoint = scheduleTimePoint;
		this.timePriorityType = timePriorityType;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@JsonIgnore
	public ScheduleTimePoint getScheduleTimePoint() {
		return scheduleTimePoint;
	}

	public void setScheduleTimePoint(ScheduleTimePoint scheduleTimePoint) {
		this.scheduleTimePoint = scheduleTimePoint;
	}

	public TimePriorityType getTimePriorityType() {
		return timePriorityType;
	}

	public void setTimePriorityType(TimePriorityType timePriorityType) {
		this.timePriorityType = timePriorityType;
	}

	@Override
	public String toString() {
		return "UserTimePriority [user=" + user + ", scheduleTimePoint=" + scheduleTimePoint + ", timePriorityType="
				+ timePriorityType + "]";
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(scheduleTimePoint).append(user).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserTimePriority other = (UserTimePriority) obj;
		return new EqualsBuilder().append(scheduleTimePoint, other.scheduleTimePoint).append(user, other.user)
				.isEquals();
	}

}
