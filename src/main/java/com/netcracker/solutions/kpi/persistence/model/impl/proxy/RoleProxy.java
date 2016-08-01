package com.netcracker.solutions.kpi.persistence.model.impl.proxy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.netcracker.solutions.kpi.persistence.model.Role;
import com.netcracker.solutions.kpi.persistence.model.User;
import com.netcracker.solutions.kpi.persistence.model.impl.real.RoleImpl;
import com.netcracker.solutions.kpi.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import java.util.Set;

@Configurable
public class RoleProxy implements Role {
    private static final long serialVersionUID = -2080439528365867845L;

    @Autowired
    private RoleService roleService;

    private Long id;

    private RoleImpl role;

    public  RoleProxy(Long id) {
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
        checkRoleForExist();
        return role.getRoleName();
    }

    @Override
    public void setRoleName(String roleName) {
        checkRoleForExist();
        role.setRoleName(roleName);
    }
    @JsonIgnore
    @Override
    public Set<User> getUsers() {
        checkRoleForExist();
        return role.getUsers();
    }

    @Override
    public void setUsers(Set<User> users) {
        checkRoleForExist();
        role.setUsers(users);
    }

    private void checkRoleForExist(){
        if (role == null) {
            role = downloadRole();
        }
    }

    @Override
    public String getAuthority() {
        return this.getRoleName();
    }

    private RoleImpl downloadRole() {
        return (RoleImpl) roleService.getRoleById(id);
    }

    @Override
    public String toString() {
        return "RoleProxy{" +
                "role=" + role +
                '}';
    }
}
