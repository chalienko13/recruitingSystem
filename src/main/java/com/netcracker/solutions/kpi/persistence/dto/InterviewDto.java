package com.netcracker.solutions.kpi.persistence.dto;

import java.util.ArrayList;
import java.util.List;

public class InterviewDto {

    private Long id;

    private Boolean adequateMark;

    private Integer mark;

    private List<StudentAppFormQuestionDto> questions = new ArrayList<>();

    public InterviewDto() {
    }

    public InterviewDto(Long id, Boolean adequateMark, Integer mark, List<StudentAppFormQuestionDto> questions) {
        super();
        this.id = id;
        this.adequateMark = adequateMark;
        this.mark = mark;
        this.questions = questions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isAdequateMark() {
        return adequateMark;
    }

    public void setAdequateMark(Boolean adequateMark) {
        this.adequateMark = adequateMark;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    public List<StudentAppFormQuestionDto> getQuestions() {
        return questions;
    }

    public void setQuestions(List<StudentAppFormQuestionDto> questions) {
        this.questions = questions;
    }

    @Override
    public String toString() {
        return "InterviewDto [id=" + id + ", adequateMark=" + adequateMark + ", mark=" + mark + ", questions="
                + questions + "]";
    }


}
