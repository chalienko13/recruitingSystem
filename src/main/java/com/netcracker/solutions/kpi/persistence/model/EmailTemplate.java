package com.netcracker.solutions.kpi.persistence.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "email_template")
public class EmailTemplate implements Serializable {

    private static final long serialVersionUID = -325738497072982583L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "text")
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_notification")
    private NotificationType notificationType;

    public EmailTemplate() {
    }

    public EmailTemplate(String title, String text, NotificationType notificationType) {
        this.title = title;
        this.text = text;
        this.notificationType = notificationType;
    }

    public EmailTemplate(Long id, String title, String text, NotificationType notificationType) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.notificationType = notificationType;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        EmailTemplate that = (EmailTemplate) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(title, that.title)
                .append(text, that.text)
                .append(notificationType, that.notificationType)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(title)
                .append(text)
                .append(notificationType)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "EmailTemplate{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", notificationType=" + notificationType +
                '}';
    }
}
