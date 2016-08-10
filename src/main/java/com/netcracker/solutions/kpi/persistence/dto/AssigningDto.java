package com.netcracker.solutions.kpi.persistence.dto;

import java.util.Arrays;

public class AssigningDto {

    private Long id;

    private Long[] roles;

    public AssigningDto() {
    }

    public AssigningDto(Long id, Long[] roles) {
        super();
        this.id = id;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long[] getRoles() {
        return roles;
    }

    public void setRoles(Long[] roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "AssigningDto [id=" + id + ", roles=" + Arrays.toString(roles) + "]";
    }

}
