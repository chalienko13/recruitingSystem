package com.netcracker.solutions.kpi.persistence.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

/**
 * @author Korzh
 */
public class Message implements Serializable {

    private static final long serialVersionUID = -1827405242957243162L;

    private Long id;
    private String subject;
    private String text;
    private String email;
    private Boolean status;

    public Message() {
    }

    public Message(String subject, String text, String email, Boolean status) {
        this.subject = subject;
        this.text = text;
        this.email = email;
        this.status = status;
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

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Message that = (Message) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(subject, that.subject)
                .append(text, that.text)
                .append(email, that.email)
                .append(status, that.status)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(subject)
                .append(text)
                .append(email)
                .append(status)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", subject='" + subject + '\'' +
                ", text='" + text + '\'' +
                ", email='" + email + '\'' +
                ", status=" + status +
                '}';
    }
}
