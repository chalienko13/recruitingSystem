package com.netcracker.solutions.kpi.service;

import java.util.List;
import java.util.Set;

public interface FormQuestionService {

	public boolean insertFormQuestion(FormQuestion formQuestion, Role role);

	int updateFormQuestion(FormQuestion formQuestion);

	boolean updateQuestions (FormQuestion formQuestion,  List<FormAnswerVariant> formAnswerVariants);

	boolean insertFormQuestion(FormQuestion formQuestion, Role role, List<FormAnswerVariant> formAnswerVariants);

	int deleteFormQuestion(FormQuestion formQuestion);

	boolean addRole(FormQuestion formQuestion, Role role);

	int deleteRole(FormQuestion formQuestion, Role role);

	FormQuestion getById(Long id);

	List<FormQuestion> getByRole(Role role);

	List<FormQuestion> getByRoleNonText(Role role);

	List<FormQuestion> getAll();
	
	List<FormQuestion> getEnableByRole(Role role);
	
	Set<FormQuestion> getByEnableRoleAsSet(Role role);
	
	Set<FormQuestion> getByApplicationFormAsSet(ApplicationForm applicationForm);
	
	List<FormQuestion> getEnableUnconnectedQuestion(ApplicationForm applicationForm);
	
	List<FormQuestion> getWithVariantsByRole(Role role);
}
