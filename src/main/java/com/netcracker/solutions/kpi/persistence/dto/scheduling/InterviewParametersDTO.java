package com.netcracker.solutions.kpi.persistence.dto.scheduling;

/**
 * Created by olil0716 on 8/9/2016.
 */
public class InterviewParametersDTO {
    private Short recruitmentId;
    private Short timeInterviewTech;
    private Short timeInterviewSoft;


    public InterviewParametersDTO() {
    }

    public InterviewParametersDTO(Short recruitmentId, Short timeInterviewTech, Short timeInterviewSoft) {
        this.recruitmentId = recruitmentId;
        this.timeInterviewTech = timeInterviewTech;
        this.timeInterviewSoft = timeInterviewSoft;
    }

    public Short getRecruitmentId() {
        return recruitmentId;
    }
    public void setRecruitmentId(Short recruitmentId) {
        this.recruitmentId = recruitmentId;
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
}
