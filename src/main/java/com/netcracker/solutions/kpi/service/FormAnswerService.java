package com.netcracker.solutions.kpi.service;

import java.util.List;

/**
 * Created by Chalienko on 21.04.2016.
 */
public interface FormAnswerService {
    FormAnswer getFormAnswerByID(Long id);

    List<FormAnswer> getByInterviewAndQuestion(Interview interview, FormQuestion question);

    Long insertFormAnswerForApplicationForm(FormAnswer formAnswer);

    Long insertBlankFormAnswerForApplicationForm(FormAnswer formAnswer);
    
    List<FormAnswer> getByApplicationFormAndQuestion(ApplicationForm applicationForm, FormQuestion question);

    int updateFormAnswer(FormAnswer formAnswer);

    int deleteFormAnswer(FormAnswer formAnswer);
    
    Long insertFormAnswerForInterview(FormAnswer formAnswer);
}
