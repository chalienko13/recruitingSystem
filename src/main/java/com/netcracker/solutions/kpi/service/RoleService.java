package com.netcracker.solutions.kpi.service;

import java.util.List;
import java.util.Set;

/**
 * Created by Chalienko on 13.04.2016.
 */

public interface RoleService {
	Role getRoleById(Long id);

	Role getRoleByTitle(String title);

	Set<Role> getAll();

	Long insertRole(Role role);

	int updateRole(Role role);

	int deleteRole(Role role);

	public boolean isInterviewerRole(Role role);
	
	List<Role> getPossibleInterviewsRoles(ApplicationForm applicationForm, User interviewer);
}
