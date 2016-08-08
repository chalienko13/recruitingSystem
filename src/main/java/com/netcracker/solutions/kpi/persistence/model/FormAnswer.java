package com.netcracker.solutions.kpi.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Алексей on 21.04.2016.
 */
@Entity
@Table(name = "form_answer")
public class FormAnswer implements Serializable {

    private static final long serialVersionUID = 7004025676148335072L;

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "answer")
    private String answer;

    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_question")
    private FormQuestion formQuestion;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_application_form")
    private ApplicationForm applicationForm;

    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_variant")
    private FormAnswerVariant formAnswerVariant;

    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_interview")
    private Interview interview;

    public FormAnswer() {
    }

    public FormAnswer(String answer, FormQuestion formQuestion, ApplicationForm applicationForm, FormAnswerVariant formAnswerVariant, Interview interview) {
        this.answer = answer;
        this.formQuestion = formQuestion;
        this.applicationForm = applicationForm;
        this.formAnswerVariant = formAnswerVariant;
        this.interview = interview;
    }

    public FormAnswer(Long id, String answer, FormQuestion formQuestion, ApplicationForm applicationForm, FormAnswerVariant formAnswerVariant, Interview interview) {
        this.id = id;
        this.answer = answer;
        this.formQuestion = formQuestion;
        this.applicationForm = applicationForm;
        this.formAnswerVariant = formAnswerVariant;
        this.interview = interview;
    }

    public FormAnswer(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public FormQuestion getFormQuestion() {
        return formQuestion;
    }

    public void setFormQuestion(FormQuestion formQuestion) {
        this.formQuestion = formQuestion;
    }

    public ApplicationForm getApplicationForm() {
        return applicationForm;
    }

    public void setApplicationForm(ApplicationForm applicationForm) {
        this.applicationForm = applicationForm;
    }

    public FormAnswerVariant getFormAnswerVariant() {
        return formAnswerVariant;
    }

    public void setFormAnswerVariant(FormAnswerVariant formAnswerVariant) {
        this.formAnswerVariant = formAnswerVariant;
    }

    public Interview getInterview() {
        return interview;
    }

    public void setInterview(Interview interview) {
        this.interview = interview;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FormAnswer that = (FormAnswer) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (answer != null ? !answer.equals(that.answer) : that.answer != null) return false;
        if (formQuestion != null ? !formQuestion.equals(that.formQuestion) : that.formQuestion != null) return false;
        if (applicationForm != null ? !applicationForm.equals(that.applicationForm) : that.applicationForm != null)
            return false;
        if (formAnswerVariant != null ? !formAnswerVariant.equals(that.formAnswerVariant) : that.formAnswerVariant != null)
            return false;
        return interview != null ? interview.equals(that.interview) : that.interview == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (answer != null ? answer.hashCode() : 0);
        result = 31 * result + (formQuestion != null ? formQuestion.hashCode() : 0);
        result = 31 * result + (applicationForm != null ? applicationForm.hashCode() : 0);
        result = 31 * result + (formAnswerVariant != null ? formAnswerVariant.hashCode() : 0);
        result = 31 * result + (interview != null ? interview.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FormAnswerImpl{" +
                "id=" + id +
                ", answer='" + answer + '\'' +
                ", formQuestion=" + formQuestion +
                ", applicationForm=" + applicationForm +
                ", formAnswerVariant=" + formAnswerVariant +
                ", interview=" + interview +
                '}';
    }
}
