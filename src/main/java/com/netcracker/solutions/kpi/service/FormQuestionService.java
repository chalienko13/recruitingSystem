package com.netcracker.solutions.kpi.service;

import com.netcracker.solutions.kpi.persistence.model.ApplicationForm;
import com.netcracker.solutions.kpi.persistence.model.FormAnswerVariant;
import com.netcracker.solutions.kpi.persistence.model.FormQuestion;
import com.netcracker.solutions.kpi.persistence.model.Role;

import java.util.List;
import java.util.Set;

public interface FormQuestionService {

    int updateFormQuestion(FormQuestion formQuestion);

    boolean updateQuestions(FormQuestion formQuestion, List<FormAnswerVariant> formAnswerVariants);

    boolean insertFormQuestion(FormQuestion formQuestion, Role role, List<FormAnswerVariant> formAnswerVariants);

    int deleteFormQuestion(FormQuestion formQuestion);

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
