package com.netcracker.solutions.kpi.persistence.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

public class TimePriorityType implements Serializable {

	private static final long serialVersionUID = 5491212300116403572L;

	private Long id;

	private String priority;

	public TimePriorityType() {
	}

	public TimePriorityType(String priority) {
		this.priority = priority;
	}

	public TimePriorityType(Long id, String priority) {
		this.id = id;
		this.priority = priority;
	}

	public TimePriorityType(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	@Override
	public String toString() {
		return "TimePriorityType [id=" + id + ", priority=" + priority + "]";
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(id).append(priority).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TimePriorityType other = (TimePriorityType) obj;
		return new EqualsBuilder().append(id, other.id).append(priority, other.priority).isEquals();
	}

}
