package com.netcracker.solutions.kpi.persistence.model;

import java.io.Serializable;

/**
 * Created by Алексей on 21.04.2016.
 */
public interface FormAnswer extends Serializable {
    Long getId();

    void setId(Long id);

    String getAnswer();

    void setAnswer(String answer);

    FormQuestion getFormQuestion();

    void setFormQuestion(FormQuestion formQuestion);

    ApplicationForm getApplicationForm();

    void setApplicationForm(ApplicationForm applicationForm);

    FormAnswerVariant getFormAnswerVariant();

    void setFormAnswerVariant(FormAnswerVariant formAnswerVariant);

    Interview getInterview();

    void setInterview(Interview interview);
}
