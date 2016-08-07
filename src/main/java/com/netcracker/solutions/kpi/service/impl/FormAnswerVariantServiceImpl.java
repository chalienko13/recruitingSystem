package com.netcracker.solutions.kpi.service.impl;

import com.netcracker.solutions.kpi.persistence.dao.FormAnswerVariantDao;
import com.netcracker.solutions.kpi.persistence.model.FormAnswerVariant;
import com.netcracker.solutions.kpi.persistence.model.FormQuestion;
import com.netcracker.solutions.kpi.service.FormAnswerVariantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FormAnswerVariantServiceImpl implements FormAnswerVariantService {

    private static Logger log = LoggerFactory.getLogger(FormAnswerVariantServiceImpl.class.getName());

    @Autowired
    private FormAnswerVariantDao formAnswerVariantDao;

    @Override
    public List<FormAnswerVariant> getAnswerVariantsByQuestion(FormQuestion question) {
        return formAnswerVariantDao.getByQuestionId(question.getId());
    }

    @Override
    public FormAnswerVariant getAnswerVariantByTitleAndQuestion(String title, FormQuestion question) {
        return formAnswerVariantDao.getAnswerVariantByTitleAndQuestion(title, question);
    }
}
