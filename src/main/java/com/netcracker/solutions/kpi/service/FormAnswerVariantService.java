package com.netcracker.solutions.kpi.service;

import java.util.List;

/**
 * AnswerVariant service
 *
 * @author Yaroslav Kruk on 4/23/16.
 *         e-mail: yakruck@gmail.com
 *         GitHub: https://github.com/uakruk
 * @version 1.0
 * @since 1.7
 */
public interface FormAnswerVariantService {

	
    /**
     * Answer variants by question
     * @param formQuestion question by which the search will be done
     * @return  set of available answer variants for the question
     */
    List<FormAnswerVariant> getAnswerVariantsByQuestion(FormQuestion formQuestion);

    /**
     * Add new answer variant for the question
     * @param answerVariant Answer the variant for the question
     * @param formQuestion The question for which answer variant will be assigned
     * @return the id of added answer variant
     */
    Long addAnswerVariant(FormAnswerVariant answerVariant);

    /**
     * Update answer variant
     * @param formAnswerVariant the answer variant to be updated
     * @return whether the answer variant was updated
     */
    boolean changeAnswerVariant(FormAnswerVariant formAnswerVariant);

    /**
     * Delete Answer variant
     * @param formVariant the answer variant to be deleted
     * @return whether the answer variant was deleted
     */
    boolean deleteAnswerVariant(FormAnswerVariant formVariant);

    /**
     * Get Answer variant by id
     * @param id the id by which answer variant will be taken
     * @return Answer variant
     */
    FormAnswerVariant getAnswerVariantById(Long id);

    FormAnswerVariant getAnswerVariantByTitleAndQuestion(String title, FormQuestion question);
    
}
