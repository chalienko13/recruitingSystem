package com.netcracker.solutions.kpi.persistence.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "notification_type")
public class NotificationType implements Serializable {

    private static final long serialVersionUID = 5660260758528854372L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "n_title")
    private String title;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "notificationType")
    private List<EmailTemplate> emailTemplateList;


    public NotificationType() {
    }

    public NotificationType(String title) {
        this.title = title;
    }

    public NotificationType(Long id, String title) {
        super();
        this.id = id;
        this.title = title;
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

    public List<EmailTemplate> getEmailTemplateList() {
        return emailTemplateList;
    }

    public void setEmailTemplateList(List<EmailTemplate> emailTemplateList) {
        this.emailTemplateList = emailTemplateList;
    }

    @Override
    public String toString() {
        return "NotificationType [id=" + id + ", title=" + title + "]";
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).append(title).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        NotificationType other = (NotificationType) obj;
        return new EqualsBuilder().append(id, other.id).append(title, other.title).isEquals();
    }
}
