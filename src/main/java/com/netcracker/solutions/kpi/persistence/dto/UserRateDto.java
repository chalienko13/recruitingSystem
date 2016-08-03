package com.netcracker.solutions.kpi.persistence.dto;

import com.netcracker.solutions.kpi.persistence.model.Role;
import com.netcracker.solutions.kpi.persistence.model.User;
import com.netcracker.solutions.kpi.persistence.model.enums.RoleEnum;

/**
 * Created by IO on 11.05.2016.
 */
public class UserRateDto {

    private long id;
    private long idInterview;
    private String firstName;
    private String secondName;
    private String lastName;
    private Integer mark;
    private String interviewType;

    public UserRateDto(User user, Integer mark, Role role, Long idInterview) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.secondName = user.getSecondName();
        this.lastName = user.getLastName();
        this.mark = mark;
        this.interviewType = defineInterviewType(role);
        this.idInterview = idInterview;
    }

    public UserRateDto(String firstName, long id, String interviewType, String lastName, Integer mark, String secondName) {
        this.firstName = firstName;
        this.id = id;
        this.interviewType = interviewType;
        this.lastName = lastName;
        this.mark = mark;
        this.secondName = secondName;
    }

    private String defineInterviewType(Role role) {
        switch (RoleEnum.valueOf(role.getRoleName())) {
            case ROLE_SOFT:
                return "SOFT";
            case ROLE_TECH:
                return "TECH";
        }
        throw new IllegalArgumentException("No role defined for");
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    public String getInterviewType() {
        return interviewType;
    }

    public void setInterviewType(String interviewType) {
        this.interviewType = interviewType;
    }

    public long getIdInterview() {
        return idInterview;
    }

    public void setIdInterview(long idInterview) {
        this.idInterview = idInterview;
    }
}


