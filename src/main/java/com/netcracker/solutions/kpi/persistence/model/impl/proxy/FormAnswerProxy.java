package com.netcracker.solutions.kpi.persistence.model.impl.proxy;

import com.netcracker.solutions.kpi.persistence.model.*;
import com.netcracker.solutions.kpi.persistence.model.impl.real.FormAnswerImpl;
import com.netcracker.solutions.kpi.service.FormAnswerService;
import org.springframework.beans.factory.annotation.Configurable;

@Configurable
public class FormAnswerProxy implements FormAnswer {

    private static final long serialVersionUID = -6304555865417768920L;

    private Long id;

    private FormAnswerImpl formAnswer;

    private FormAnswerService answerService;

    public FormAnswerProxy() {
    }

    public FormAnswerProxy(Long id) {
        this.id = id;
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
    public String getAnswer() {
        checkFormAnswer();
        return formAnswer.getAnswer();
    }

    @Override
    public void setAnswer(String answer) {
        checkFormAnswer();
        formAnswer.setAnswer(answer);
    }

    public FormQuestion getFormQuestion() {
        checkFormAnswer();
        return formAnswer.getFormQuestion();
    }

    public void setFormQuestion(FormQuestion formQuestion) {
        checkFormAnswer();
        formAnswer.setFormQuestion(formQuestion);
    }

    public ApplicationForm getApplicationForm() {
        checkFormAnswer();
        return formAnswer.getApplicationForm();
    }

    public void setApplicationForm(ApplicationForm applicationForm) {
        checkFormAnswer();
        formAnswer.setApplicationForm(applicationForm);
    }

    public FormAnswerVariant getFormAnswerVariant() {
        checkFormAnswer();
        return formAnswer.getFormAnswerVariant();
    }

    public void setFormAnswerVariant(FormAnswerVariant formAnswerVariant) {
        checkFormAnswer();
        formAnswer.setFormAnswerVariant(formAnswerVariant);
    }

    public Interview getInterview() {
        checkFormAnswer();
        return formAnswer.getInterview();
    }

    public void setInterview(Interview interview) {
        checkFormAnswer();
        formAnswer.setInterview(interview);
    }

    private void checkFormAnswer() {
        if (formAnswer == null) {
            formAnswer = downloadFormAnswer();
        }
    }

    private FormAnswerImpl downloadFormAnswer() {
        return (FormAnswerImpl) answerService.getFormAnswerByID(id);
    }
}
