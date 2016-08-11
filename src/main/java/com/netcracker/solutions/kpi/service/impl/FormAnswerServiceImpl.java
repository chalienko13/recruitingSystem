package com.netcracker.solutions.kpi.service.impl;

import com.netcracker.solutions.kpi.persistence.dao.FormAnswerDao;
import com.netcracker.solutions.kpi.persistence.model.*;
import com.netcracker.solutions.kpi.persistence.repository.FormAnswerRepository;
import com.netcracker.solutions.kpi.service.FormAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FormAnswerServiceImpl implements FormAnswerService {

    @Autowired
    private FormAnswerDao formAnswerDao;

    @Autowired
    private FormAnswerRepository formAnswerRepository;

    @Override
    public List<FormAnswer> getByInterviewAndQuestion(Interview interview, FormQuestion question) {
        return formAnswerDao.getByInterviewAndQuestion(interview, question);
    }

    @Override
    public List<FormAnswer> getByApplicationFormAndQuestion(ApplicationForm applicationForm, FormQuestion question) {
        return formAnswerDao.getByApplicationFormAndQuestion(applicationForm, question);
    }

    public void insertFormAnswerForApplicationForm(FormAnswer formAnswer, FormQuestion question,
                                       ApplicationForm applicationForm){
        formAnswer.setFormQuestion(question);
        formAnswer.setApplicationForm(applicationForm);
        formAnswerRepository.save(formAnswer);

    }
}
