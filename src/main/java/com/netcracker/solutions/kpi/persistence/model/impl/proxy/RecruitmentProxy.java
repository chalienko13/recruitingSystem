package com.netcracker.solutions.kpi.persistence.model.impl.proxy;

import com.netcracker.solutions.kpi.persistence.model.SchedulingStatus;
import com.netcracker.solutions.kpi.persistence.model.impl.real.RecruitmentImpl;
import com.netcracker.solutions.kpi.service.RecruitmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import java.sql.Timestamp;

@Configurable
public class RecruitmentProxy implements Recruitment {

    private static final long serialVersionUID = -3463375498462494737L;
    private Long id;

    private RecruitmentImpl recruitment;

    @Autowired
    private RecruitmentService recruitmentService;

    public RecruitmentProxy() {

    }

    public RecruitmentProxy(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return  id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;

    }


    @Override
    public String getName() {
        checkRecruitmentForExist();
        return recruitment.getName();
    }

    @Override
    public void setName(String name) {
        checkRecruitmentForExist();
        recruitment.setName(name);
    }

    @Override
    public Timestamp getStartDate() {
        checkRecruitmentForExist();
        return recruitment.getStartDate();
    }

    @Override
    public void setStartDate(Timestamp startDate) {
        checkRecruitmentForExist();
        recruitment.setStartDate(startDate);
    }

    @Override
    public Timestamp getEndDate() {
        checkRecruitmentForExist();
        return recruitment.getEndDate();
    }

    @Override
    public void setEndDate(Timestamp endDate) {
        checkRecruitmentForExist();
        recruitment.setEndDate(endDate);
    }

    @Override
    public int getMaxGeneralGroup() {
        checkRecruitmentForExist();
        return recruitment.getMaxGeneralGroup();
    }

    @Override
    public void setMaxGeneralGroup(int maxGeneralGroup) {
        checkRecruitmentForExist();
        recruitment.setMaxGeneralGroup(maxGeneralGroup);
    }

    @Override
    public int getMaxAdvancedGroup() {
        checkRecruitmentForExist();
        return recruitment.getMaxAdvancedGroup();
    }

    @Override
    public void setMaxAdvancedGroup(int maxAdvancedGroup) {
        checkRecruitmentForExist();
        recruitment.setMaxAdvancedGroup(maxAdvancedGroup);
    }

    @Override
    public Timestamp getRegistrationDeadline() {
        checkRecruitmentForExist();
        return recruitment.getRegistrationDeadline();
    }

    @Override
    public void setRegistrationDeadline(Timestamp registrationDeadline) {
        checkRecruitmentForExist();
        recruitment.setRegistrationDeadline(registrationDeadline);

    }

    @Override
    public Timestamp getScheduleChoicesDeadline() {
        checkRecruitmentForExist();
        return recruitment.getScheduleChoicesDeadline();
    }

    @Override
    public void setScheduleChoicesDeadline(Timestamp scheduleChoicesDeadline) {
        checkRecruitmentForExist();
        recruitment.setScheduleChoicesDeadline(scheduleChoicesDeadline);
    }

    @Override
    public int getStudentsOnInterview() {
        checkRecruitmentForExist();
        return recruitment.getStudentsOnInterview();
    }

    @Override
    public void setStudentsOnInterview(int studentsOnInterview) {
        checkRecruitmentForExist();
        recruitment.setStudentsOnInterview(studentsOnInterview);
    }

    @Override
    public int getTimeInterviewTech() {
        checkRecruitmentForExist();
        return recruitment.getTimeInterviewTech();
    }

    @Override
    public void setTimeInterviewTech(int timeInterviewTech) {
        checkRecruitmentForExist();
        recruitment.setTimeInterviewTech(timeInterviewTech);
    }

    @Override
    public int getTimeInterviewSoft() {
        checkRecruitmentForExist();
        return recruitment.getTimeInterviewSoft();
    }

    @Override
    public void setTimeInterviewSoft(int timeInterviewSoft) {
        checkRecruitmentForExist();
        recruitment.setTimeInterviewSoft(timeInterviewSoft);
    }

    @Override
    public int getNumberTechInterviewers() {
        checkRecruitmentForExist();
        return recruitment.getNumberTechInterviewers();
    }

    @Override
    public void setNumberTechInterviewers(int numberTechInterviewers) {
        checkRecruitmentForExist();
        recruitment.setNumberTechInterviewers(numberTechInterviewers);
    }

    @Override
    public int getNumberSoftInterviewers() {
        checkRecruitmentForExist();
        return recruitment.getNumberSoftInterviewers();
    }

    @Override
    public void setNumberSoftInterviewers(int numberSoftInterviewers) {
        checkRecruitmentForExist();
        recruitment.setNumberSoftInterviewers(numberSoftInterviewers);
    }

    @Override
    public int getNumberOfDays() {
        checkRecruitmentForExist();
        return recruitment.getNumberOfDays();
    }

    @Override
    public void setNumberOfDays(int numberOfDays) {
        checkRecruitmentForExist();
        recruitment.setNumberOfDays(numberOfDays);
    }

    @Override
    public SchedulingStatus getSchedulingStatus() {
        checkRecruitmentForExist();
        return recruitment.getSchedulingStatus();
    }

    @Override
    public void setSchedulingStatus(SchedulingStatus schedulingStatus) {
        checkRecruitmentForExist();
        recruitment.setSchedulingStatus(schedulingStatus);
    }

    private void checkRecruitmentForExist(){
        if (recruitment == null) {
            recruitment = downloadRecruitment();
        }
    }

    private RecruitmentImpl downloadRecruitment() {
        return (RecruitmentImpl) recruitmentService.getRecruitmentById(id);
    }
}
