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

    //TODO Change to Long
    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    private Short id;

    @Column(name = "name")
    private String name;

    @Column(name = "start_date")
    private Timestamp startDate;

    @Column(name = "end_date")
    private Timestamp endDate;

    @Column(name = "max_general_group")
    private Short maxGeneralGroup;

    @Column(name = "max_advanced_group")
    private Short maxAdvancedGroup;

    @Column(name = "registration_deadline")
    private Timestamp registrationDeadline;

    @Column(name = "schedule_choices_deadline")
    private Timestamp scheduleChoicesDeadline;

    @Column(name = "students_on_interview")
    private Short studentsOnInterview;

    @Column(name = "time_interview_tech")
    private Short timeInterviewTech;

    @Column(name = "time_interview_soft")
    private Short timeInterviewSoft;

    @Column(name = "number_tech_interviewers")
    private Short numberTechInterviewers;

    @Column(name = "number_soft_interviewers")
    private Short numberSoftInterviewers;

    @Column(name = "number_of_days")
    private Short numberOfDays;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "scheduling_status")
    private SchedulingStatus schedulingStatus;

    public Recruitment() {
    }

    public Recruitment(Short id) {
        this.id = id;
    }

    public Recruitment(Short numberOfDays, String name, Timestamp startDate, Timestamp endDate,
                       Short maxGeneralGroup, Short maxAdvancedGroup, Timestamp registrationDeadline,
                       Timestamp scheduleChoicesDeadline, Short studentsOnInterview, Short timeInterviewTech,
                       Short timeInterviewSoft, Short numberTechInterviwers, Short numberSoftInterviwers, SchedulingStatus schedulingStatus) {
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

    public Recruitment(Short id, String name, Timestamp startDate, Timestamp endDate, Short maxGeneralGroup,
                       Short maxAdvancedGroup, Timestamp registrationDeadline, Timestamp scheduleChoicesDeadline,
                       Short studentsOnInterview, Short timeInterviewTech, Short timeInterviewSoft,
                       Short numberTechInterviewers, Short numberSoftInterviewers, Short numberOfDays, SchedulingStatus schedulingStatus) {
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

    public Recruitment(String name, Timestamp startDate, Short maxAdvancedGroup, Short maxGeneralGroup, Timestamp registrationDeadline, Timestamp scheduleChoicesDeadline) {
        this.name = name;
        this.startDate = startDate;
        this.maxGeneralGroup = maxGeneralGroup;
        this.maxAdvancedGroup = maxAdvancedGroup;
        this.registrationDeadline = registrationDeadline;
        this.scheduleChoicesDeadline = scheduleChoicesDeadline;
    }

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
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

    public Short getMaxGeneralGroup() {
        return maxGeneralGroup;
    }

    public void setMaxGeneralGroup(Short maxGeneralGroup) {
        this.maxGeneralGroup = maxGeneralGroup;
    }

    public Short getMaxAdvancedGroup() {
        return maxAdvancedGroup;
    }

    public void setMaxAdvancedGroup(Short maxAdvancedGroup) {
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

    public Short getStudentsOnInterview() {
        return studentsOnInterview;
    }

    public void setStudentsOnInterview(Short studentsOnInterview) {
        this.studentsOnInterview = studentsOnInterview;
    }

    public Short getTimeInterviewTech() {
        return timeInterviewTech;
    }

    public void setTimeInterviewTech(Short timeInterviewTech) {
        this.timeInterviewTech = timeInterviewTech;
    }

    public Short getTimeInterviewSoft() {
        return timeInterviewSoft;
    }

    public void setTimeInterviewSoft(Short timeInterviewSoft) {
        this.timeInterviewSoft = timeInterviewSoft;
    }

    public Short getNumberTechInterviewers() {
        return numberTechInterviewers;
    }

    public void setNumberTechInterviewers(Short numberTechInterviewers) {
        this.numberTechInterviewers = numberTechInterviewers;
    }

    public Short getNumberSoftInterviewers() {
        return numberSoftInterviewers;
    }

    public void setNumberSoftInterviewers(Short numberSoftInterviewers) {
        this.numberSoftInterviewers = numberSoftInterviewers;
    }

    public Short getNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays(Short numberOfDays) {
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
