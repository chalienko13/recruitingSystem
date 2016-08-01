package com.netcracker.solutions.kpi.persistence.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

/**
 * @author Korzh
 */
public class ResendMessage implements Serializable {

    private static final long serialVersionUID = 1799312788939954583L;

    private Long id;
    private String subject;
    private String text;
    private String email;

    public ResendMessage() {
    }

    public ResendMessage(String subject, String text, String email, boolean status) {
        this.subject = subject;
        this.text = text;
        this.email = email;

    }

    public ResendMessage(Long id, String subject, String text, String email, boolean status) {
        this.id = id;
        this.subject = subject;
        this.text = text;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ResendMessage that = (ResendMessage) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(subject, that.subject)
                .append(text, that.text)
                .append(email, that.email)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(subject)
                .append(text)
                .append(email)
                .toHashCode();
    }
}
