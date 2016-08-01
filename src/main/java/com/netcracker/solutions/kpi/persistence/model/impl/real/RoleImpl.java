package com.netcracker.solutions.kpi.persistence.model.impl.real;

import com.netcracker.solutions.kpi.persistence.model.Role;
import com.netcracker.solutions.kpi.persistence.model.User;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Set;

/**
 * Created by Chalienko on 13.04.2016.
 */
public class RoleImpl implements Role{

    private static final long serialVersionUID = -3446275256614511482L;

    private Long id;

    private String roleName;

    private Set<User> users;

    public RoleImpl(Long id, String roleName, Set<User> users ) {
        this.roleName = roleName;
        this.id = id;
        this.users = users;
    }

    public RoleImpl(Long id, String roleName) {
        this.id = id;
        this.roleName = roleName;
    }

    public RoleImpl(String roleName, Set<User> users) {
        this.roleName = roleName;
        this.users = users;
    }

    public RoleImpl() {
    }

    public RoleImpl(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getRoleName() {
        return roleName;
    }

    @Override
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public Set<User> getUsers() {
        return users;
    }

    @Override
    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        RoleImpl role = (RoleImpl) o;

        return new EqualsBuilder()
                .append(id, role.id)
                .append(roleName, role.roleName)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(roleName)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "Role: " +
                "roleName= " + roleName + "\n";
    }

    @Override
    public String getAuthority() {
        return getRoleName();
    }
}
