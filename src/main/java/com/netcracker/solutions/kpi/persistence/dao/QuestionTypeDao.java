package com.netcracker.solutions.kpi.persistence.dao;

import com.netcracker.solutions.kpi.persistence.model.QuestionType;

import java.util.List;

/**
 * Created by IO on 21.04.2016.
 */
public interface QuestionTypeDao {

    List<QuestionType> getAllQuestionType();

    QuestionType getById(Long id);

    QuestionType getByName(String name);

    Long persistQuestionType(QuestionType questionType);

    int deleteQuestionType(QuestionType questionType);

}
