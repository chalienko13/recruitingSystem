package com.netcracker.solutions.kpi.service.impl;

import com.netcracker.solutions.kpi.persistence.dao.RoleDao;
import com.netcracker.solutions.kpi.persistence.model.ApplicationForm;
import com.netcracker.solutions.kpi.persistence.model.Role;
import com.netcracker.solutions.kpi.persistence.model.User;
import com.netcracker.solutions.kpi.persistence.model.enums.RoleEnum;
import com.netcracker.solutions.kpi.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    /*public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }*/

    @Override
    public Role getRoleById(Long id) {
        return roleDao.getByID(id);
    }

    @Override
    public Role getRoleByTitle(String title) {
        return roleDao.getByTitle(title);
    }

    @Override
    public Set<Role> getAll() {
        return roleDao.getAll();
    }

    @Override
    public Long insertRole(Role role) {
        return roleDao.insertRole(role);
    }

    @Override
    public int updateRole(Role role) {
        return roleDao.updateRole(role);
    }

    @Override
    public int deleteRole(Role role) {
        return roleDao.deleteRole(role);
    }

    public boolean isInterviewerRole(Role role) {
        return RoleEnum.ROLE_SOFT.name().equals(role.getRoleName()) || RoleEnum.ROLE_TECH.name().equals(role.getRoleName());
    }

	@Override
	public List<Role> getPossibleInterviewsRoles(ApplicationForm applicationForm, User interviewer) {
		return roleDao.getPossibleInterviewsRoles(applicationForm, interviewer);
	}
}
