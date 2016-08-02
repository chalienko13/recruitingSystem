package com.netcracker.solutions.kpi.persistence.dao;

import java.sql.Connection;
import java.util.List;

/**
 * Created by Алексей on 21.04.2016.
 */
public interface FormAnswerVariantDao {
	FormAnswerVariant getById(Long id);

	List<FormAnswerVariant> getByQuestionId(Long id);

	Long insertFormAnswerVariant(FormAnswerVariant formatVariant);

	Long insertFormAnswerVariant(FormAnswerVariant formatVariant, Connection connection);

	int updateFormAnswerVariant(FormAnswerVariant formAnswerVariant);

	int deleteFormAnswerVariant(FormAnswerVariant formVariant);

	int deleteFormAnswerVariant(FormAnswerVariant formVariant, Connection connection);

	List<FormAnswerVariant> getAll();

	FormAnswerVariant getAnswerVariantByTitleAndQuestion(String title, FormQuestion question);

}
