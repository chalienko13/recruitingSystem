package com.netcracker.solutions.kpi.persistence.model.impl.real;

import com.netcracker.solutions.kpi.persistence.model.Recruitment;
import com.netcracker.solutions.kpi.persistence.model.SchedulingStatus;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.sql.Timestamp;


public class RecruitmentImpl implements Recruitment {

    private static final long serialVersionUID = 4839409160085869405L;

    private Long id;

    private String name;

    private Timestamp startDate;

    private Timestamp endDate;

    private int maxGeneralGroup;

    private int maxAdvancedGroup;

    private Timestamp registrationDeadline;

    private Timestamp scheduleChoicesDeadline;

    private int studentsOnInterview;

    private int timeInterviewTech;

    private int timeInterviewSoft;

    private int numberTechInterviewers;

    private int numberSoftInterviewers;

    private int numberOfDays;

    private SchedulingStatus schedulingStatus;

    public RecruitmentImpl() {
    }

    public RecruitmentImpl(int numberOfDays, String name, Timestamp startDate, Timestamp endDate,
                           int maxGeneralGroup, int maxAdvancedGroup, Timestamp registrationDeadline,
                           Timestamp scheduleChoicesDeadline, int studentsOnInterview, int timeInterviewTech,
                           int timeInterviewSoft, int numberTechInterviwers, int numberSoftInterviwers, SchedulingStatus schedulingStatus) {
        this.numberOfDays = numberOfDays;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.maxGeneralGroup = maxGeneralGroup;
        this.maxAdvancedGroup = maxAdvancedGroup;
        this.registrationDeadline = registrationDeadline;
        this.scheduleChoicesDeadline = scheduleChoicesDeadline;
        this.studentsOnInterview = studentsOnInterview;
        this.timeInterviewTech = timeInterviewTech;
        this.timeInterviewSoft = timeInterviewSoft;
        this.numberTechInterviewers = numberTechInterviwers;
        this.numberSoftInterviewers = numberSoftInterviwers;
        this.schedulingStatus = schedulingStatus;
    }

    public RecruitmentImpl(Long id, String name, Timestamp startDate, Timestamp endDate, int maxGeneralGroup,
                           int maxAdvancedGroup, Timestamp registrationDeadline, Timestamp scheduleChoicesDeadline,
                           int studentsOnInterview, int timeInterviewTech, int timeInterviewSoft,
                           int numberTechInterviewers, int numberSoftInterviewers, int numberOfDays, SchedulingStatus schedulingStatus) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.maxGeneralGroup = maxGeneralGroup;
        this.maxAdvancedGroup = maxAdvancedGroup;
        this.registrationDeadline = registrationDeadline;
        this.scheduleChoicesDeadline = scheduleChoicesDeadline;
        this.studentsOnInterview = studentsOnInterview;
        this.timeInterviewTech = timeInterviewTech;
        this.timeInterviewSoft = timeInterviewSoft;
        this.numberTechInterviewers = numberTechInterviewers;
        this.numberSoftInterviewers = numberSoftInterviewers;
        this.numberOfDays = numberOfDays;
        this.schedulingStatus = schedulingStatus;
    }

    public RecruitmentImpl(String name, Timestamp startDate, int maxAdvancedGroup, int maxGeneralGroup, Timestamp registrationDeadline, Timestamp scheduleChoicesDeadline) {
        this.name = name;
        this.startDate = startDate;
        this.maxGeneralGroup = maxGeneralGroup;
        this.maxAdvancedGroup = maxAdvancedGroup;
        this.registrationDeadline = registrationDeadline;
        this.scheduleChoicesDeadline = scheduleChoicesDeadline;
    }


    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Timestamp getStartDate() {
        return startDate;
    }

    @Override
    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    @Override
    public Timestamp getEndDate() {
        return endDate;
    }

    @Override
    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    @Override
    public int getMaxGeneralGroup() {
        return maxGeneralGroup;
    }

    @Override
    public void setMaxGeneralGroup(int maxGeneralGroup) {
        this.maxGeneralGroup = maxGeneralGroup;
    }

    @Override
    public int getMaxAdvancedGroup() {
        return maxAdvancedGroup;
    }

    @Override
    public void setMaxAdvancedGroup(int maxAdvancedGroup) {
        this.maxAdvancedGroup = maxAdvancedGroup;
    }

    @Override
    public Timestamp getRegistrationDeadline() {
        return registrationDeadline;
    }

    @Override
    public void setRegistrationDeadline(Timestamp registrationDeadline) {
        this.registrationDeadline = registrationDeadline;
    }

    @Override
    public Timestamp getScheduleChoicesDeadline() {
        return scheduleChoicesDeadline;
    }

    @Override
    public void setScheduleChoicesDeadline(Timestamp scheduleChoicesDeadline) {
        this.scheduleChoicesDeadline = scheduleChoicesDeadline;
    }

    @Override
    public int getStudentsOnInterview() {
        return studentsOnInterview;
    }

    @Override
    public void setStudentsOnInterview(int studentsOnInterview) {
        this.studentsOnInterview = studentsOnInterview;
    }

    @Override
    public int getTimeInterviewTech() {
        return timeInterviewTech;
    }

    @Override
    public void setTimeInterviewTech(int timeInterviewTech) {
        this.timeInterviewTech = timeInterviewTech;
    }

    @Override
    public int getTimeInterviewSoft() {
        return timeInterviewSoft;
    }

    @Override
    public void setTimeInterviewSoft(int timeInterviewSoft) {
        this.timeInterviewSoft = timeInterviewSoft;
    }

    @Override
    public int getNumberTechInterviewers() {
        return numberTechInterviewers;
    }

    @Override
    public void setNumberTechInterviewers(int numberTechInterviewers) {
        this.numberTechInterviewers = numberTechInterviewers;
    }

    @Override
    public int getNumberSoftInterviewers() {
        return numberSoftInterviewers;
    }

    @Override
    public void setNumberSoftInterviewers(int numberSoftInterviewers) {
        this.numberSoftInterviewers = numberSoftInterviewers;
    }

    @Override
    public int getNumberOfDays() {
        return numberOfDays;
    }

    @Override
    public void setNumberOfDays(int numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public SchedulingStatus getSchedulingStatus() {
        return schedulingStatus;
    }

    public void setSchedulingStatus(SchedulingStatus schedulingStatus) {
        this.schedulingStatus = schedulingStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        RecruitmentImpl that = (RecruitmentImpl) o;

        return new EqualsBuilder()
                .append(maxGeneralGroup, that.maxGeneralGroup)
                .append(maxAdvancedGroup, that.maxAdvancedGroup)
                .append(studentsOnInterview, that.studentsOnInterview)
                .append(timeInterviewTech, that.timeInterviewTech)
                .append(timeInterviewSoft, that.timeInterviewSoft)
                .append(numberTechInterviewers, that.numberTechInterviewers)
                .append(numberSoftInterviewers, that.numberSoftInterviewers)
                .append(numberOfDays, that.numberOfDays)
                .append(id, that.id)
                .append(name, that.name)
                .append(startDate, that.startDate)
                .append(endDate, that.endDate)
                .append(registrationDeadline, that.registrationDeadline)
                .append(scheduleChoicesDeadline, that.scheduleChoicesDeadline)
                .append(schedulingStatus, that.schedulingStatus)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(name)
                .append(startDate)
                .append(endDate)
                .append(maxGeneralGroup)
                .append(maxAdvancedGroup)
                .append(registrationDeadline)
                .append(scheduleChoicesDeadline)
                .append(studentsOnInterview)
                .append(timeInterviewTech)
                .append(timeInterviewSoft)
                .append(numberTechInterviewers)
                .append(numberSoftInterviewers)
                .append(numberOfDays)
                .append(schedulingStatus)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "RecruitmentImpl{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", maxGeneralGroup=" + maxGeneralGroup +
                ", maxAdvancedGroup=" + maxAdvancedGroup +
                ", registrationDeadline=" + registrationDeadline +
                ", scheduleChoicesDeadline=" + scheduleChoicesDeadline +
                ", studentsOnInterview=" + studentsOnInterview +
                ", timeInterviewTech=" + timeInterviewTech +
                ", timeInterviewSoft=" + timeInterviewSoft +
                ", numberTechInterviewers=" + numberTechInterviewers +
                ", numberSoftInterviewers=" + numberSoftInterviewers +
                ", numberOfDays=" + numberOfDays +
                ", schedulingStatus=" + schedulingStatus +
                '}';
    }
}
