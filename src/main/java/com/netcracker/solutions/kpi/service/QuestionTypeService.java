package com.netcracker.solutions.kpi.service;

import com.netcracker.solutions.kpi.persistence.model.QuestionType;

import java.util.List;

public interface QuestionTypeService {

    List<QuestionType> getAllQuestionType();

    QuestionType getQuestionTypeByName(String name);
}
