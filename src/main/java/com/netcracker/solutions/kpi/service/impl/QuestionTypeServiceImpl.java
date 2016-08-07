package com.netcracker.solutions.kpi.service.impl;

import com.netcracker.solutions.kpi.persistence.dao.QuestionTypeDao;
import com.netcracker.solutions.kpi.persistence.model.QuestionType;
import com.netcracker.solutions.kpi.service.QuestionTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class QuestionTypeServiceImpl implements QuestionTypeService {

    @Autowired
    private QuestionTypeDao questionTypeDao;

    @Override
    public List<QuestionType> getAllQuestionType() {
        return questionTypeDao.getAllQuestionType();
    }

    @Override
    public QuestionType getQuestionTypeByName(String name) {
        return questionTypeDao.getByName(name);
    }
}
