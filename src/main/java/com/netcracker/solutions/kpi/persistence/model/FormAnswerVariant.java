package com.netcracker.solutions.kpi.persistence.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Алексей on 21.04.2016.
 */
@Entity
@Table(name = "form_answer_variant")
public class FormAnswerVariant implements Serializable {

    private static final long serialVersionUID = 1091069075594065071L;

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "answer")
    private String answer;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_question")
    private FormQuestion formQuestion;

    public FormAnswerVariant() {
    }

    public FormAnswerVariant(Long id) {
        this.id = id;
    }

    public FormAnswerVariant(String answer) {
        this.answer = answer;
    }

    public FormAnswerVariant(String answer, FormQuestion formQuestion) {
        this.answer = answer;
        this.formQuestion = formQuestion;
    }

    public FormAnswerVariant(Long id, String title, FormQuestion formQuestion) {
        this.id = id;
        this.answer = title;
        this.formQuestion = formQuestion;
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

        FormAnswerVariant that = (FormAnswerVariant) o;

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
