package com.netcracker.solutions.kpi.service;

import com.netcracker.solutions.kpi.persistence.model.FormAnswerVariant;
import com.netcracker.solutions.kpi.persistence.model.FormQuestion;

import java.util.List;

public interface FormAnswerVariantService {
    List<FormAnswerVariant> getAnswerVariantsByQuestion(FormQuestion formQuestion);

    FormAnswerVariant getAnswerVariantByTitleAndQuestion(String title, FormQuestion question);

}
