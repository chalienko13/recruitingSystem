package com.netcracker.solutions.kpi.persistence.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.ManyToAny;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Chalienko on 13.04.2016.
 */

@Entity
@Table(name = "user")
public class Role implements GrantedAuthority {

    private static final long serialVersionUID = -3446275256614511482L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "role")
    private String roleName;

    @ManyToMany(mappedBy="roles")
    private Set<User> users;

    public Role(Long id, String roleName, Set<User> users ) {
        this.roleName = roleName;
        this.id = id;
        this.users = users;
    }

    public Role(Long id, String roleName) {
        this.id = id;
        this.roleName = roleName;
    }

    public Role(String roleName, Set<User> users) {
        this.roleName = roleName;
        this.users = users;
    }

    public Role() {
    }

    public Role(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Role role = (Role) o;

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
