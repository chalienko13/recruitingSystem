package com.netcracker.solutions.kpi.persistence.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = "schedule_time_point")
public class ScheduleTimePoint implements Serializable {

    private static final long serialVersionUID = -8906473195615020302L;
    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "schedule_time_point")
    private Timestamp timePoint;
    //toDO change db_table??
    @Transient
    private Set<User> users;
    @OneToMany(mappedBy = "scheduleTimePoint")
    private Set<UserTimePriority> userTimePriorities;

    public ScheduleTimePoint() {
    }

    public ScheduleTimePoint(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getTimePoint() {
        return timePoint;
    }

    public void setTimePoint(Timestamp timePoint) {
        this.timePoint = timePoint;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<UserTimePriority> getUserTimePriorities() {
        return userTimePriorities;
    }

    public void setUserTimePriorities(Set<UserTimePriority> userTimePriorities) {
        this.userTimePriorities = userTimePriorities;
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
        ScheduleTimePoint other = (ScheduleTimePoint) obj;
        return new EqualsBuilder().append(id, other.id).append(timePoint, other.timePoint).append(users, other.users)
                .isEquals();
    }

}
