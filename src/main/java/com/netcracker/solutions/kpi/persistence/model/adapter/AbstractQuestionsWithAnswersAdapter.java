package com.netcracker.solutions.kpi.persistence.model.adapter;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Korzh
 */
public abstract class AbstractQuestionsWithAnswersAdapter {

    protected Map<FormQuestion, JsonObject> generateQuestionsAndAnswers(List<FormAnswer> formAnswers) {
        Map<FormQuestion, JsonObject> questionsMap = new TreeMap<>((o1, o2) -> Integer.compare(o1.getOrder(),
                o2.getOrder()));
        for (FormAnswer answer : formAnswers) {
            FormQuestion question = answer.getFormQuestion();
            if(!question.isEnable()) {
            	continue;
            }
            JsonObject jsonQuestion = questionsMap.get(question);
            if (jsonQuestion == null) {
                jsonQuestion = new JsonObject();
                jsonQuestion.addProperty("id", question.getId());
                jsonQuestion.addProperty("questionTitle", question.getTitle());
                jsonQuestion.addProperty("questionType", question.getQuestionType().getTypeTitle());
                jsonQuestion.addProperty("isMandatory", question.isMandatory());
                JsonArray jsonAnswers = new JsonArray();
                jsonQuestion.add("answers", jsonAnswers);
                if (question.getFormAnswerVariants() != null) {
                    JsonArray jsonAnswerVariants = new JsonArray();
                    for (FormAnswerVariant variant : question.getFormAnswerVariants()) {
                        JsonObject jsonAnswerVariant = new JsonObject();
                        jsonAnswerVariant.addProperty("id", variant.getId());
                        jsonAnswerVariant.addProperty("variant", variant.getAnswer());
                        jsonAnswerVariants.add(jsonAnswerVariant);
                    }
                    jsonQuestion.add("variants", jsonAnswerVariants);
                }
                questionsMap.put(question, jsonQuestion);
            }
            JsonArray jsonAnswers = jsonQuestion.getAsJsonArray("answers");
            JsonObject jsonAnswer = new JsonObject();
            if (question.getFormAnswerVariants() == null) {
                jsonAnswer.addProperty("answer", answer.getAnswer());
                jsonAnswer.addProperty("id", answer.getId());
            } else if (answer.getFormAnswerVariant() != null) {
                jsonAnswer.addProperty("answer", answer.getFormAnswerVariant().getAnswer());
                jsonAnswer.addProperty("id", answer.getId());
            }
            jsonAnswers.add(jsonAnswer);
        }

        return questionsMap;
    }
}
