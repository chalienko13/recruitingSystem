package com.netcracker.solutions.kpi.persistence.dto;

import com.netcracker.solutions.kpi.persistence.model.Role;

import java.util.Set;

public class CurrentAuthUser {
    private Long id;
    private String firstName;
    private Set<Role> roles;

    public CurrentAuthUser(Long id, String firstName, Set<Role> roles) {
        this.id = id;
        this.firstName = firstName;
        this.roles = roles;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
