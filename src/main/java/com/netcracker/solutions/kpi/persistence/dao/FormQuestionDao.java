package com.netcracker.solutions.kpi.persistence.dao;

import java.sql.Connection;
import java.util.List;
import java.util.Set;

/**
 * Created by IO on 21.04.2016.
 */
public interface FormQuestionDao {

    Long insertFormQuestion(FormQuestion formQuestion, Connection connection);

    int deleteFormQuestion(FormQuestion formQuestion);

    boolean addRole(FormQuestion formQuestion, Role role);

    boolean addRole(FormQuestion formQuestion, Role role, Connection connection);

    int deleteRole(FormQuestion formQuestion, Role role);

    FormQuestion getById(Long id);

    List<FormQuestion> getByRole(Role role);

    List<FormQuestion> getByRoleNonText(Role role);

    List<FormQuestion> getAll();

	int updateFormQuestion(FormQuestion formQuestion);

    int updateFormQuestion(FormQuestion formQuestion, Connection connection);

	Set<FormQuestion> getEnableByRoleAsSet(Role role);

	List<FormQuestion> getEnableByRole(Role role);

	Set<FormQuestion> getByApplicationFormAsSet(ApplicationForm applicationForm);

	List<FormQuestion> getEnableUnconnectedQuestion(Role role, ApplicationForm applicationForm);

	List<FormQuestion> getWithVariantsByRole(Role role);


}
