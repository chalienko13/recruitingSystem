package com.netcracker.solutions.kpi.persistence.model.impl.proxy;

import com.netcracker.solutions.kpi.persistence.model.impl.real.FormAnswerVariantImpl;
import com.netcracker.solutions.kpi.service.FormAnswerVariantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

@Configurable
public class FormAnswerVariantProxy implements FormAnswerVariant {

    private static final long serialVersionUID = -4692963972854360647L;
    private Long id;
    private FormAnswerVariantImpl formAnswerVariant;

    @Autowired
    private FormAnswerVariantService formAnswerVariantService;

    public FormAnswerVariantProxy() {
    }

    public FormAnswerVariantProxy(Long id) {
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
        checkFormAnswerVariant();
        return formAnswerVariant.getAnswer();
    }

    @Override
    public void setAnswer(String answer) {
        checkFormAnswerVariant();
        formAnswerVariant.setAnswer(answer);
    }

    public FormQuestion getFormQuestion() {
        checkFormAnswerVariant();
        return formAnswerVariant.getFormQuestion();
    }

    public void setFormQuestion(FormQuestion formQuestion) {
        checkFormAnswerVariant();
        formAnswerVariant.setFormQuestion(formQuestion);
    }

    private void checkFormAnswerVariant() {
        if (formAnswerVariant == null) {
            formAnswerVariant = downloadRecruitment();
        }
    }

    private FormAnswerVariantImpl downloadRecruitment() {
        return (FormAnswerVariantImpl) formAnswerVariantService.getAnswerVariantById(id);
    }
}
