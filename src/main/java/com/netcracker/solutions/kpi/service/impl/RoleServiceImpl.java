package com.netcracker.solutions.kpi.service.impl;

import com.netcracker.solutions.kpi.persistence.model.ApplicationForm;
import com.netcracker.solutions.kpi.persistence.model.Role;
import com.netcracker.solutions.kpi.persistence.model.User;
import com.netcracker.solutions.kpi.persistence.model.enums.RoleEnum;
import com.netcracker.solutions.kpi.persistence.repository.RoleRepository;
import com.netcracker.solutions.kpi.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role getRoleById(Long id) {
        return roleRepository.getOne(id);
    }

    @Override
    public Role getRoleByTitle(String title) {
        return roleRepository.getByRoleName(title);
    }

    @Override
    public List<Role> getAll() {
        return roleRepository.findAll();
    }

    public boolean isInterviewerRole(Role role) {
        return RoleEnum.ROLE_SOFT.name().equals(role.getRoleName()) || RoleEnum.ROLE_TECH.name().equals(role.getRoleName());
    }

    @Override
    public List<Role> getPossibleInterviewsRoles(ApplicationForm applicationForm, User interviewer) {
        if (applicationForm != null && interviewer != null) {
            return roleRepository.getPossibleInterviewsRoles(interviewer.getId(), applicationForm.getId());
        }
        return Collections.emptyList();
    }
}
