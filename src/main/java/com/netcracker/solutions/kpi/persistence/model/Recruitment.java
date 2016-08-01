package com.netcracker.solutions.kpi.persistence.model;

import java.io.Serializable;
import java.sql.Timestamp;

public interface Recruitment extends Serializable {

    Long getId();

    void setId(Long id);

    String getName();

    void setName(String name);

    Timestamp getStartDate();

    void setStartDate(Timestamp startDate);

    Timestamp getEndDate();

    void setEndDate(Timestamp endDate);

    int getMaxGeneralGroup();

    void setMaxGeneralGroup(int maxGeneralGroup);

    int getMaxAdvancedGroup();

    void setMaxAdvancedGroup(int maxAdvancedGroup);

    Timestamp getRegistrationDeadline();

    void setRegistrationDeadline(Timestamp registrationDeadline);

    Timestamp getScheduleChoicesDeadline();

    void setScheduleChoicesDeadline(Timestamp scheduleChoicesDeadline);

    int getStudentsOnInterview();

    void setStudentsOnInterview(int studentsOnInterview);

    int getTimeInterviewTech();

    void setTimeInterviewTech(int timeInterviewTech);

    int getTimeInterviewSoft();

    void setTimeInterviewSoft(int timeInterviewSoft);

    int getNumberTechInterviewers();

    void setNumberTechInterviewers(int numberTechInterviewers);

    int getNumberSoftInterviewers();

    void setNumberSoftInterviewers(int numberSoftInterviewers);

    int getNumberOfDays();

    void setNumberOfDays(int numberOfDays);

    SchedulingStatus getSchedulingStatus();

    void setSchedulingStatus(SchedulingStatus schedulingStatus);
}
