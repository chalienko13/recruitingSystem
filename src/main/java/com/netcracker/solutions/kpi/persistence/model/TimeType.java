package com.netcracker.solutions.kpi.persistence.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;


/**
 * Created by olil0716 on 8/4/2016.
 */
@Entity
@Table(name = "time_type")
public class TimeType implements Serializable{
    private static final long serialVersionUID = -4079981095057369340L;

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    private Short id;

    @Column(name = "type", unique = true, nullable = false)
    String type;

    @OneToMany(mappedBy = "timeType", fetch = FetchType.LAZY)
    Set<UserTime> userTimes;

    public TimeType() {
    }

    public TimeType(String type) {
        this.type = type;
    }

    public Short getId() {
        return id;
    }
    public void setId(Short id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public Set<UserTime> getUserTimes() {
        return userTimes;
    }
    public void setUserTimes(Set<UserTime> userTimes) {
        this.userTimes = userTimes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TimeType timeType = (TimeType) o;

        if (!id.equals(timeType.id)) return false;
        return type.equals(timeType.type);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + type.hashCode();
        return result;
    }
}
