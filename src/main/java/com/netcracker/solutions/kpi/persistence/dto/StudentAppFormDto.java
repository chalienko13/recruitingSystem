package com.netcracker.solutions.kpi.persistence.dto;

import com.netcracker.solutions.kpi.persistence.model.Status;

import java.util.List;


public class StudentAppFormDto {

    private Long id;
    private Long appFormId;
    private String firstName;
    private String lastName;
    private String status;
    private Integer softMark;
    private Integer techMark;
    private Integer finalMark;
    private List<Status> possibleStatus;

    public StudentAppFormDto() {
    }

    public StudentAppFormDto(Long id, Long appFormId, String firstName,
                             String lastName, String status, List<Status> possibleStatus) {
        this.id = id;
        this.appFormId = appFormId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.status = status;
        this.possibleStatus = possibleStatus;
    }

    public StudentAppFormDto(Long id, Long appFormId, String firstName, String lastName, String status,
                             Integer softMark, Integer techMark, Integer finalMark, List<Status> possibleStatus) {
        this.id = id;
        this.appFormId = appFormId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.status = status;
        this.softMark = softMark;
        this.techMark = techMark;
        this.finalMark = finalMark;
        this.possibleStatus = possibleStatus;
    }

    public Integer getSoftMark() {
        return softMark;
    }

    public void setSoftMark(Integer softMark) {
        this.softMark = softMark;
    }

    public Integer getTechMark() {
        return techMark;
    }

    public void setTechMark(Integer techMark) {
        this.techMark = techMark;
    }

    public Integer getFinalMark() {
        return finalMark;
    }

    public void setFinalMark(Integer finalMark) {
        this.finalMark = finalMark;
    }

    public List<Status> getPossibleStatus() {
        return possibleStatus;
    }

    public void setPossibleStatus(List<Status> possibleStatus) {
        this.possibleStatus = possibleStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAppFormId() {
        return appFormId;
    }

    public void setAppFormId(Long appFormId) {
        this.appFormId = appFormId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "StudentAppFormDto{" +
                "id=" + id +
                ", appFormId=" + appFormId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", status='" + status + '\'' +
                ", possibleStatus=" + possibleStatus +
                '}';
    }
}
