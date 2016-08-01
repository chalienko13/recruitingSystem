package com.netcracker.solutions.kpi.persistence.model;

import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Set;


public interface Role extends Serializable, GrantedAuthority {
    Long getId();

    void setId(Long id);

    String getRoleName();

    void setRoleName(String roleName);

    Set<User> getUsers();

    void setUsers(Set<User> users);
}