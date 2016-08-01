package com.netcracker.solutions.kpi.persistence.model.impl.real;

import com.netcracker.solutions.kpi.persistence.model.FormAnswerVariant;
import com.netcracker.solutions.kpi.persistence.model.FormQuestion;
import com.netcracker.solutions.kpi.persistence.model.QuestionType;
import com.netcracker.solutions.kpi.persistence.model.Role;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.List;

/**
 * Created by Алексей on 21.04.2016.
 */
public class FormQuestionImpl implements FormQuestion {

    private static final long serialVersionUID = -4875241221362139428L;
    private Long id;
    private String title;
    private QuestionType questionType;
    private boolean enable;
    private boolean mandatory;
    private List<Role> roles;
    private List<FormAnswerVariant> formAnswerVariants;
    private int order;


    public FormQuestionImpl() {
    }

    public FormQuestionImpl(Long id, String title, QuestionType questionType, boolean enable, boolean mandatory, List<Role> roles, List<FormAnswerVariant> formAnswerVariants) {
        this.id = id;
        this.title = title;
        this.questionType = questionType;
        this.enable = enable;
        this.mandatory = mandatory;
        this.roles = roles;
        this.formAnswerVariants = formAnswerVariants;
    }

    public FormQuestionImpl(String title, QuestionType questionType, boolean enable, boolean mandatory, List<Role> roles, List<FormAnswerVariant> formAnswerVariants, int order) {
        this.title = title;
        this.questionType = questionType;
        this.enable = enable;
        this.mandatory = mandatory;
        this.roles = roles;
        this.formAnswerVariants = formAnswerVariants;
        this.order = order;
    }

    public FormQuestionImpl(Long id, String title, QuestionType questionType, List<FormAnswerVariant> formAnswerVariants, int order) {
        this.id = id;
        this.title = title;
        this.questionType = questionType;
        this.formAnswerVariants = formAnswerVariants;
        this.order = order;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public List<Role> getRoles() {
        return roles;
    }

    @Override
    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public boolean isMandatory() {
        return mandatory;
    }

    public void setMandatory(boolean mandatory) {
        this.mandatory = mandatory;
    }

    public List<FormAnswerVariant> getFormAnswerVariants() {
        return formAnswerVariants;
    }

    public void setFormAnswerVariants(List<FormAnswerVariant> formAnswerVariants) {
        this.formAnswerVariants = formAnswerVariants;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        FormQuestionImpl that = (FormQuestionImpl) o;

        return new EqualsBuilder()
                .append(enable, that.enable)
                .append(mandatory, that.mandatory)
                .append(id, that.id)
                .append(title, that.title)
                .append(questionType, that.questionType)
                .append(order, that.order)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(title)
                .append(questionType)
                .append(enable)
                .append(mandatory)
                .append(order)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "FormQuestionImpl{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", questionType=" + questionType +
                ", enable=" + enable +
                ", mandatory=" + mandatory +
                ", order=" + order +
                ", formAnswerVariants=" + formAnswerVariants +
                '}';
    }

}
