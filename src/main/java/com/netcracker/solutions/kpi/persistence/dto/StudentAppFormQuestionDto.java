package com.netcracker.solutions.kpi.persistence.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Korzh
 */
public class StudentAppFormQuestionDto {
    private long id;
    private String questionTitle;
    private String questionType;
    private boolean isMandatory;
    private List<StudentAnswerDto> answers = new ArrayList<>();
    private List<QuestionVariantDto> variants = new ArrayList<>();

    public StudentAppFormQuestionDto() {
    }

    public StudentAppFormQuestionDto(long id, String questionTitle, String questionType, boolean isMandatory, List<StudentAnswerDto> answers, List<QuestionVariantDto> variants) {
        this.id = id;
        this.questionTitle = questionTitle;
        this.questionType = questionType;
        this.isMandatory = isMandatory;
        this.answers = answers;
        this.variants = variants;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public boolean isMandatory() {
        return isMandatory;
    }

    public void setMandatory(boolean mandatory) {
        isMandatory = mandatory;
    }

    public List<StudentAnswerDto> getAnswers() {
        return answers;
    }

    public void setAnswers(List<StudentAnswerDto> answers) {
        this.answers = answers;
    }

    public List<QuestionVariantDto> getVariants() {
        return variants;
    }

    public void setVariants(List<QuestionVariantDto> variants) {
        this.variants = variants;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "StudentAppFormQuestionDto{" +
                "id=" + id +
                ", questionTitle='" + questionTitle + '\'' +
                ", questionType='" + questionType + '\'' +
                ", isMandatory=" + isMandatory +
                ", answers=" + answers +
                ", variants=" + variants +
                '}';
    }
}
