package com.netcracker.solutions.kpi.service;

import com.netcracker.solutions.kpi.persistence.model.ApplicationForm;
import com.netcracker.solutions.kpi.persistence.model.FormAnswer;
import com.netcracker.solutions.kpi.persistence.model.FormQuestion;
import com.netcracker.solutions.kpi.persistence.model.Interview;

import java.util.List;

/**
 * Created by Chalienko on 21.04.2016.
 */
public interface FormAnswerService {
    List<FormAnswer> getByInterviewAndQuestion(Interview interview, FormQuestion question);

    List<FormAnswer> getByApplicationFormAndQuestion(ApplicationForm applicationForm, FormQuestion question);
}
