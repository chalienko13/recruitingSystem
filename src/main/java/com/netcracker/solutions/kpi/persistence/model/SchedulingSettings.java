package com.netcracker.solutions.kpi.persistence.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author Korzh
 */

@Entity
@Table(name = "scheduling_settings")
public class SchedulingSettings implements Serializable {
    private static final long serialVersionUID = 3756080692775410186L;

    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "start_time")
    private Timestamp startDate;
    @Column(name = "end_time")
    private Timestamp endDate;


    public SchedulingSettings() {
    }

    public SchedulingSettings(Timestamp startDate, Timestamp endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public SchedulingSettings(Long id, Timestamp startDate, Timestamp endDate) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        SchedulingSettings that = (SchedulingSettings) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(startDate, that.startDate)
                .append(endDate, that.endDate)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(startDate)
                .append(endDate)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "SchedulingSettings{" +
                "id=" + id +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
