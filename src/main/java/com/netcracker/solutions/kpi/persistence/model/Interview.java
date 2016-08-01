package com.netcracker.solutions.kpi.persistence.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Алексей on 21.04.2016.
 */
public interface Interview extends Serializable {
    Long getId();

    void setId(Long id);

    Role getRole();

    void setRole(Role role);

    List<FormAnswer> getAnswers();

    void setAnswers(List<FormAnswer> answers);

    Integer getMark();

    void setMark(Integer mark);

    Timestamp getDate();

    void setDate(Timestamp date);

    User getInterviewer();

    void setInterviewer(User user);

    Boolean isAdequateMark();

    void setAdequateMark(Boolean adequateMark);

    ApplicationForm getApplicationForm();

    void setApplicationForm(ApplicationForm applicationForm);

}
