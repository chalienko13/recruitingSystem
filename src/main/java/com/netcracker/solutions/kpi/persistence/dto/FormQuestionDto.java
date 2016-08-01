package com.netcracker.solutions.kpi.persistence.dto;

import java.util.List;

/**
 * Created by dima on 04.05.16.
 */
public class FormQuestionDto {

    private Long id;
    private String question;
    private String type;
    private boolean enable;
    private boolean mandatory;
    private List<String> formAnswerVariants;
    private String role;
    private int order;


    public FormQuestionDto() {
    }

    public FormQuestionDto(Long id, String question, String type, boolean enable, boolean mandatory, List<String> formAnswerVariants, String role, int order) {
        this.id = id;
        this.question = question;
        this.type = type;
        this.enable = enable;
        this.mandatory = mandatory;
        this.formAnswerVariants = formAnswerVariants;
        this.role = role;
        this.order = order;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public List<String> getFormAnswerVariants() {
        return formAnswerVariants;
    }

    public void setFormAnswerVariants(List<String> formAnswerVariants) {
        this.formAnswerVariants = formAnswerVariants;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isMandatory() {
        return mandatory;
    }

    public void setMandatory(boolean mandatory) {
        this.mandatory = mandatory;
    }

    @Override
    public String toString() {
        return "FormQuestionDto{" +
                "id=" + id +
                ", question='" + question + '\'' +
                ", type='" + type + '\'' +
                ", enable=" + enable +
                ", mandatory=" + mandatory +
                ", formAnswerVariants=" + formAnswerVariants +
                ", role='" + role + '\'' +
                ", order=" + order +
                '}';
    }
}
