package com.netcracker.solutions.kpi.persistence.model.impl.real;

import com.netcracker.solutions.kpi.persistence.model.FormAnswerVariant;
import com.netcracker.solutions.kpi.persistence.model.FormQuestion;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Created by Алексей on 21.04.2016.
 */
public class FormAnswerVariantImpl implements FormAnswerVariant {

    private static final long serialVersionUID = 1091069075594065071L;
    private Long id;
    private String answer;
    private FormQuestion formQuestion;

    public FormAnswerVariantImpl() {
    }


    public FormAnswerVariantImpl(String answer) {
        this.answer = answer;
    }

    public FormAnswerVariantImpl(String answer, FormQuestion formQuestion) {
        this.answer = answer;
        this.formQuestion = formQuestion;
    }

    public FormAnswerVariantImpl(Long id, String title, FormQuestion formQuestion) {
        this.id = id;
        this.answer = title;
        this.formQuestion = formQuestion;
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
        return answer;
    }
    @Override
    public void setAnswer(String title) {
        this.answer = title;
    }
    public FormQuestion getFormQuestion() {
        return formQuestion;
    }
    public void setFormQuestion(FormQuestion formQuestion) {
        this.formQuestion = formQuestion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        FormAnswerVariantImpl that = (FormAnswerVariantImpl) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(answer, that.answer)
                .append(formQuestion, that.formQuestion)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(answer)
                .append(formQuestion)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "FormAnswerVariantImpl{" +
                "id=" + id +
                ", answer='" + answer + '\'' +
                ", formQuestion=" + formQuestion +
                '}';
    }
}
