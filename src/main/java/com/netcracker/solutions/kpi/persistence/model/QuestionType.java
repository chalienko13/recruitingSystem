package com.netcracker.solutions.kpi.persistence.model;

import java.io.Serializable;

/**
 * Created by Алексей on 21.04.2016.
 */
public class QuestionType implements Serializable {

    private static final long serialVersionUID = -191062081481479636L;

    private Long id;
    private String typeTitle;

    public QuestionType() {
    }

    public QuestionType(String typeTitle) {
        this.typeTitle = typeTitle;
    }

    public QuestionType(Long id, String typeTitle) {
        this.id = id;
        this.typeTitle = typeTitle;
    }

    public Long getId() {
        return id;
    }

    public String getTypeTitle() {
        return typeTitle;
    }

    public void setTypeTitle(String typeTitle) {
        this.typeTitle = typeTitle;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QuestionType that = (QuestionType) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return typeTitle != null ? typeTitle.equals(that.typeTitle) : that.typeTitle == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (typeTitle != null ? typeTitle.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "QuestionType{" +
                "id=" + id +
                ", typeTitle='" + typeTitle + '\'' +
                '}';
    }
}
