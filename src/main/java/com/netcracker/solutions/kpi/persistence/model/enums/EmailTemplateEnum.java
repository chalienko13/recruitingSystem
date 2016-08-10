package com.netcracker.solutions.kpi.persistence.model.enums;

/**
 * Created by Admin on 19.05.2016.
 */
public enum EmailTemplateEnum {

    STAFF_REGISTRATION(3L), STUDENT_REGISTRATION(2L), INTERVIEW_APPROVED(5L),
    INTERVIEW_REJECTED(5L), CONFIRM_PARTICIPATE(7L), INTERVIEW_RESULT_REJECTED(8L),
    INTERVIEW_RESULT_APPROVED(9L), INTERVIEW_RESULT_APPROVED_JOB(10L),
    INTERVIEW_RESULT_APPROVED_ADVANCED(11L), STAFF_INTERVIEW_SELECT(12L), INTERVIEW_INVITE(15L);

    Long id;

    EmailTemplateEnum(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
