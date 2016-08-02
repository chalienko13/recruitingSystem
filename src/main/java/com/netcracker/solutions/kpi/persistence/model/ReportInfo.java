package com.netcracker.solutions.kpi.persistence.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Nikita on 24.04.2016.
 */

@Entity
@Table(name = "report")
public class ReportInfo implements Serializable {

    private static final long serialVersionUID = 4682674283630923553L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "query")
    private String query;
    @Column(name = "title")
    private String title;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
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

        ReportInfo report = (ReportInfo) o;

        if (id != null ? !id.equals(report.id) : report.id != null) return false;
        if (query != null ? !query.equals(report.query) : report.query != null) return false;
        if (title != null ? !title.equals(report.title) : report.title != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (query != null ? query.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", query='" + query + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
