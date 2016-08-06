package com.netcracker.solutions.kpi.persistence.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Korzh
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApplicationFormDto {


    private long id;
    private String status;
    private UserDto user;
    private List<StudentAppFormQuestionDto> questions = new ArrayList<>();
    private String feedBack;

    public ApplicationFormDto() {
    }

    public ApplicationFormDto(String feedBack) {
        this.feedBack = feedBack;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<StudentAppFormQuestionDto> getQuestions() {
        return questions;
    }

    public void setQuestions(List<StudentAppFormQuestionDto> questions) {
        this.questions = questions;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public String getFeedBack() {
        return feedBack;
    }

    public void setFeedBack(String feedBack) {
        this.feedBack = feedBack;
    }

    @Override
    public String toString() {
        return "ApplicationFormDto{" +
                "id=" + id +
                ", status='" + status + '\'' +
                ", user=" + user +
                ", questions=" + questions +
                ", feedBack='" + feedBack + '\'' +
                '}';
    }
}
