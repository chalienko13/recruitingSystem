package com.netcracker.solutions.kpi.persistence.model;

import java.io.Serializable;

/**
 * Created by Алексей on 21.04.2016.
 */
public class Status implements Serializable {

    private static final long serialVersionUID = 3459020950880424774L;
    private Long id;
    private String title;

    public Status() {
    }

    public Status(String title) {
        this.title = title;
    }

    public Status(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Status status = (Status) o;

        if (id != null ? !id.equals(status.id) : status.id != null) return false;
        return title != null ? title.equals(status.title) : status.title == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Status{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
