package com.netcracker.solutions.kpi.service;

import com.netcracker.solutions.kpi.persistence.model.ApplicationForm;
import com.netcracker.solutions.kpi.persistence.model.Role;
import com.netcracker.solutions.kpi.persistence.model.User;

import java.util.List;
import java.util.Set;

public interface RoleService {
	Role getRoleById(Long id);

	Role getRoleByTitle(String title);

	List<Role> getAll();

	void deleteRole(Role role);

	public boolean isInterviewerRole(Role role);
	
	//List<Role> getPossibleInterviewsRoles(ApplicationForm applicationForm, User interviewer);
}
