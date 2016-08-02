package com.netcracker.solutions.kpi.persistence.dao;

import java.util.List;
import java.util.Set;

/**
 * Created by Chalienko on 13.04.2016.
 */
public interface RoleDao {
	Role getByID(Long id);

	Role getByTitle(String title);
	
	Set<Role> getAll();

	Long insertRole(Role role);

	int updateRole(Role role);

	int deleteRole(Role role);

	List<Role> getPossibleInterviewsRoles(ApplicationForm applicationForm, User interviewer);
}
