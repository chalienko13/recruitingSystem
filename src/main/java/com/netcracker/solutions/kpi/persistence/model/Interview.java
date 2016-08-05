package com.netcracker.solutions.kpi.persistence.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "interview")
public class Interview implements Serializable {

    private static final long serialVersionUID = 7837728826371592710L;

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "mark")
    private Integer mark;

    @Column(name = "date")
    private Timestamp date;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_interviewer")
    private User user;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "interviewer_role")
    private Role role;

    @Column(name = "adequate_mark")
    private Boolean adequateMark;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_application_form")
    private ApplicationForm applicationForm;

    @Transient
    private List<FormAnswer> answers;

    public Interview() {
    }

    public Interview(int mark, Timestamp date, User user, Role role, boolean adequateMark, ApplicationForm applicationForm, List<FormAnswer> answers) {
        this.mark = mark;
        this.date = date;
        this.user = user;
        this.role = role;
        this.adequateMark = adequateMark;
        this.applicationForm = applicationForm;
        this.answers = answers;
    }

    public Interview(Long id, int mark, Timestamp date, User user, Role role,
                     boolean adequateMark, ApplicationForm applicationForm) {
        this.id = id;
        this.mark = mark;
        this.date = date;
        this.user = user;
        this.role = role;
        this.adequateMark = adequateMark;
        this.applicationForm = applicationForm;
    }

    public Interview(Long id) {
        this.id = id;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public User getInterviewer() {
        return user;
    }

    public void setInterviewer(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Boolean isAdequateMark() {
        return adequateMark;
    }

    public void setAdequateMark(Boolean adequateMark) {
        this.adequateMark = adequateMark;
    }

    public ApplicationForm getApplicationForm() {
        return applicationForm;
    }

    public void setApplicationForm(ApplicationForm applicationForm) {
        this.applicationForm = applicationForm;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean getAdequateMark() {
        return adequateMark;
    }

    public List<FormAnswer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<FormAnswer> answers) {
        this.answers = answers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Interview interview = (Interview) o;

        if (mark != interview.mark)
            return false;
        if (adequateMark != interview.adequateMark)
            return false;
        if (id != null ? !id.equals(interview.id) : interview.id != null)
            return false;
        if (date != null ? !date.equals(interview.date) : interview.date != null)
            return false;
        if (user != null ? !user.equals(interview.user) : interview.user != null)
            return false;
        if (role != null ? !role.equals(interview.role)
                : interview.role != null)
            return false;
        return applicationForm != null ? applicationForm.equals(interview.applicationForm)
                : interview.applicationForm == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + mark;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + (adequateMark ? 1 : 0);
        result = 31 * result + (applicationForm != null ? applicationForm.hashCode() : 0);
        return result;
    }



    @Override
    public String toString() {
        return "InterviewImpl{" + "id=" + id + ", mark=" + mark + ", date=" + date + ", user=" + user
                + ", role=" + role + ", adequateMark=" + adequateMark + ", applicationForm="
                + applicationForm + '}';
    }
}
