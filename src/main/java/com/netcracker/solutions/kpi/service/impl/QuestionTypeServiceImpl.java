package com.netcracker.solutions.kpi.service.impl;

import com.netcracker.solutions.kpi.persistence.dao.QuestionTypeDao;
import com.netcracker.solutions.kpi.persistence.model.QuestionType;
import com.netcracker.solutions.kpi.service.QuestionTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionTypeServiceImpl implements QuestionTypeService {

    @Autowired
    private QuestionTypeDao questionTypeDao;

   /* public QuestionTypeServiceImpl(QuestionTypeDao questionTypeDao) {
        this.questionTypeDao = questionTypeDao;
    }*/

    @Override
    public List<QuestionType> getAllQuestionType() {
        return questionTypeDao.getAllQuestionType();
    }

    @Override
    public QuestionType getQuestionTypeById(Long id) {
        return questionTypeDao.getById(id);
    }

    @Override
    public QuestionType getQuestionTypeByName(String name) {
        return questionTypeDao.getByName(name);
    }

    @Override
    public Long persistQuestionType(QuestionType questionType) {
        return questionTypeDao.persistQuestionType(questionType);
    }

    @Override
    public int deleteQuestionType(QuestionType questionType) {
        return questionTypeDao.deleteQuestionType(questionType);
    }
}