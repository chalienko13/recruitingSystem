package com.netcracker.solutions.kpi.persistence.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "recruitment")
public class Recruitment implements Serializable {

    private static final long serialVersionUID = 4839409160085869405L;

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "start_date")
    private Timestamp startDate;

    @Column(name = "end_date")
    private Timestamp endDate;

    @Column(name = "max_general_group")
    private int maxGeneralGroup;

    @Column(name = "max_advanced_group")
    private int maxAdvancedGroup;

    @Column(name = "registration_deadline")
    private Timestamp registrationDeadline;

    @Column(name = "schedule_choices_deadline")
    private Timestamp scheduleChoicesDeadline;

    @Column(name = "students_on_interview")
    private int studentsOnInterview;

    @Column(name = "time_interview_tech")
    private int timeInterviewTech;

    @Column(name = "time_interview_soft")
    private int timeInterviewSoft;

    @Column(name = "number_tech_interviewers")
    private int numberTechInterviewers;

    @Column(name = "number_soft_interviewers")
    private int numberSoftInterviewers;

    @Column(name = "number_of_hours")
    private int numberOfDays;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "scheduling_status")
    private SchedulingStatus schedulingStatus;

    public Recruitment() {
    }

    public Recruitment(int numberOfDays, String name, Timestamp startDate, Timestamp endDate,
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

    public Recruitment(Long id, String name, Timestamp startDate, Timestamp endDate, int maxGeneralGroup,
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

    public Recruitment(String name, Timestamp startDate, int maxAdvancedGroup, int maxGeneralGroup, Timestamp registrationDeadline, Timestamp scheduleChoicesDeadline) {
        this.name = name;
        this.startDate = startDate;
        this.maxGeneralGroup = maxGeneralGroup;
        this.maxAdvancedGroup = maxAdvancedGroup;
        this.registrationDeadline = registrationDeadline;
        this.scheduleChoicesDeadline = scheduleChoicesDeadline;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public int getMaxGeneralGroup() {
        return maxGeneralGroup;
    }

    public void setMaxGeneralGroup(int maxGeneralGroup) {
        this.maxGeneralGroup = maxGeneralGroup;
    }

    public int getMaxAdvancedGroup() {
        return maxAdvancedGroup;
    }

    public void setMaxAdvancedGroup(int maxAdvancedGroup) {
        this.maxAdvancedGroup = maxAdvancedGroup;
    }

    public Timestamp getRegistrationDeadline() {
        return registrationDeadline;
    }

    public void setRegistrationDeadline(Timestamp registrationDeadline) {
        this.registrationDeadline = registrationDeadline;
    }

    public Timestamp getScheduleChoicesDeadline() {
        return scheduleChoicesDeadline;
    }

    public void setScheduleChoicesDeadline(Timestamp scheduleChoicesDeadline) {
        this.scheduleChoicesDeadline = scheduleChoicesDeadline;
    }

    public int getStudentsOnInterview() {
        return studentsOnInterview;
    }

    public void setStudentsOnInterview(int studentsOnInterview) {
        this.studentsOnInterview = studentsOnInterview;
    }

    public int getTimeInterviewTech() {
        return timeInterviewTech;
    }

    public void setTimeInterviewTech(int timeInterviewTech) {
        this.timeInterviewTech = timeInterviewTech;
    }

    public int getTimeInterviewSoft() {
        return timeInterviewSoft;
    }

    public void setTimeInterviewSoft(int timeInterviewSoft) {
        this.timeInterviewSoft = timeInterviewSoft;
    }

    public int getNumberTechInterviewers() {
        return numberTechInterviewers;
    }

    public void setNumberTechInterviewers(int numberTechInterviewers) {
        this.numberTechInterviewers = numberTechInterviewers;
    }

    public int getNumberSoftInterviewers() {
        return numberSoftInterviewers;
    }

    public void setNumberSoftInterviewers(int numberSoftInterviewers) {
        this.numberSoftInterviewers = numberSoftInterviewers;
    }

    public int getNumberOfDays() {
        return numberOfDays;
    }

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

        Recruitment that = (Recruitment) o;

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
