package com.netcracker.solutions.kpi.persistence.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "application_form")
public class ApplicationForm implements Serializable {
    private static final long serialVersionUID = 2573334038825578138L;
    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_status")
    private Status status;

    @Column(name = "is_active")
    private boolean active;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_recruitment")
    private Recruitment recruitment;

    @Column(name = "photo_scope")
    private String photoScope;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_user")
    private User user;

    @Column(name = "date_create")
    private Timestamp dateCreate;

    @Column(name = "feedback")
    private String feedback;
    @Transient
    private List<Interview> interviews;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "applicationForm")
    private List<FormAnswer> answers;
    @Transient
    private List<FormQuestion> questions;

    public ApplicationForm() {
    }

    public ApplicationForm(Status status, boolean active, Recruitment recruitment, String photoScope, User user,
                           Timestamp dateCreate, List<Interview> interviews, List<FormAnswer> answers, List<FormQuestion> questions) {
        this.status = status;
        this.active = active;
        this.recruitment = recruitment;
        this.photoScope = photoScope;
        this.user = user;
        this.dateCreate = dateCreate;
        this.interviews = interviews;
        this.answers = answers;
        this.questions = questions;
    }

    public ApplicationForm(Long id, Status status, User user) {
        this.id = id;
        this.status = status;
        this.user = user;
    }

    public ApplicationForm(Long id, Status status, boolean active, Recruitment recruitment, String photoScope,
                           User user, Timestamp dateCreate, List<Interview> interviews, List<FormAnswer> answers, List<FormQuestion> questions) {
        this.id = id;
        this.status = status;
        this.active = active;
        this.recruitment = recruitment;
        this.photoScope = photoScope;
        this.user = user;
        this.dateCreate = dateCreate;
        this.interviews = interviews;
        this.answers = answers;
        this.questions = questions;
    }

    public ApplicationForm(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public Status getStatus() {
        return status;
    }


    public void setStatus(Status status) {
        this.status = status;
    }


    public boolean isActive() {
        return active;
    }


    public void setActive(boolean active) {
        this.active = active;
    }


    public Recruitment getRecruitment() {
        return recruitment;
    }


    public void setRecruitment(Recruitment recruitment) {
        this.recruitment = recruitment;
    }


    public String getPhotoScope() {
        return photoScope;
    }


    public void setPhotoScope(String photoScope) {
        this.photoScope = photoScope;
    }


    public User getUser() {
        return user;
    }


    public void setUser(User user) {
        this.user = user;
    }


    public Timestamp getDateCreate() {
        return dateCreate;
    }


    public void setDateCreate(Timestamp dateCreate) {
        this.dateCreate = dateCreate;
    }


    public List<Interview> getInterviews() {
        return interviews;
    }


    public void setInterviews(List<Interview> interviews) {
        this.interviews = interviews;
    }


    public List<FormAnswer> getAnswers() {
        return answers;
    }


    public void setAnswers(List<FormAnswer> answers) {
        this.answers = answers;
    }


    public List<FormQuestion> getQuestions() {
        return questions;
    }


    public void setQuestions(List<FormQuestion> questions) {
        this.questions = questions;
    }


    public String getFeedback() {
        return feedback;
    }


    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    @Override
    public String toString() {
        return "ApplicationFormImpl{" +
                "id=" + id +
                ", status=" + status +
                ", active=" + active +
                ", recruitment=" + recruitment +
                ", photoScope='" + photoScope + '\'' +
                ", user=" + user +
                ", dateCreate=" + dateCreate +
                ", feedback='" + feedback + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31).append(active).append(dateCreate).append(id).append(photoScope)
                .append(status).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ApplicationForm other = (ApplicationForm) obj;
        return new EqualsBuilder().append(active, other.active).append(dateCreate, other.dateCreate)
                .append(id, other.id).append(interviews, other.interviews).append(photoScope, other.photoScope)
                .append(status, other.status).isEquals();
    }


}
