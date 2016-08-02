package com.netcracker.solutions.kpi.persistence.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.netcracker.solutions.kpi.persistence.model.User;
import com.netcracker.solutions.kpi.persistence.model.Role;

import java.util.List;

/**
 * Created by dima on 27.04.16.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

    private Long id;
    private String email;
    private String firstName;
    private String secondName;
    private String lastName;
    private String password;
    private String token;
    private List<Role> roleList;

    public UserDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.secondName = user.getSecondName();
        this.lastName = user.getLastName();
    }

    public UserDto(String email) {
        this.email = email;
    }

    public UserDto(String email, String firstName) {
        this.email = email;
        this.firstName = firstName;
    }



    public UserDto(String email, String firstName, String secondName, String lastName, List<Role> roleList) {
        this.email = email;
        this.firstName = firstName;
        this.secondName = secondName;
        this.lastName = lastName;
        this.password = password;
    }

    public UserDto(String email, String firstName, String secondName, String lastName, String password, List<Role> roleList) {
        this.email = email;
        this.firstName = firstName;
        this.secondName = secondName;
        this.lastName = lastName;
        this.password = password;
        this.roleList = roleList;
    }

    public UserDto() {

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public List<Role> getRoleList() {

        return roleList;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "roleList=" + roleList +
                ", lastName='" + lastName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
