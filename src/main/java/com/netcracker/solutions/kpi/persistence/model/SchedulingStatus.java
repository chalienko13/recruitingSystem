package com.netcracker.solutions.kpi.persistence.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

/**
 * @author Korzh
 */
public class SchedulingStatus implements Serializable {

    private static final long serialVersionUID = -4656348714213157794L;

    private Long id;
    private String title;

    public SchedulingStatus() {
    }

    public SchedulingStatus(String title, Long id) {
        this.title = title;
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        SchedulingStatus that = (SchedulingStatus) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(title, that.title)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(title)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "SchedulingStatus{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
