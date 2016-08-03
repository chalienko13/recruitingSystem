package com.netcracker.solutions.kpi.persistence.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "form_question")
public class FormQuestion implements Serializable {

    private static final long serialVersionUID = -4875241221362139428L;

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "title")
    private String title;
    private QuestionType questionType;

    @Column(name = "enable")
    private boolean enable;

    @Column(name = "mandatory")
    private boolean mandatory;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "users")
    private List<Role> questionRoles;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "formQuestion")
    private List<FormAnswerVariant> formAnswerVariants;

    @Column(name = "order")
    private int order;


    public FormQuestion() {
    }

    public FormQuestion(Long id, String title, QuestionType questionType, boolean enable, boolean mandatory, List<Role> questionRoles, List<FormAnswerVariant> formAnswerVariants) {
        this.id = id;
        this.title = title;
        this.questionType = questionType;
        this.enable = enable;
        this.mandatory = mandatory;
        this.questionRoles = questionRoles;
        this.formAnswerVariants = formAnswerVariants;
    }

    public FormQuestion(String title, QuestionType questionType, boolean enable, boolean mandatory, List<Role> questionRoles, List<FormAnswerVariant> formAnswerVariants, int order) {
        this.title = title;
        this.questionType = questionType;
        this.enable = enable;
        this.mandatory = mandatory;
        this.questionRoles = questionRoles;
        this.formAnswerVariants = formAnswerVariants;
        this.order = order;
    }

    public FormQuestion(Long id, String title, QuestionType questionType, List<FormAnswerVariant> formAnswerVariants, int order) {
        this.id = id;
        this.title = title;
        this.questionType = questionType;
        this.formAnswerVariants = formAnswerVariants;
        this.order = order;
    }

    public FormQuestion(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public List<Role> getQuestionRoles() {
        return questionRoles;
    }


    public void setQuestionRoles(List<Role> questionRoles) {
        this.questionRoles = questionRoles;
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

        FormQuestion that = (FormQuestion) o;

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
