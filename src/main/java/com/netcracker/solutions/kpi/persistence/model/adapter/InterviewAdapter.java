package com.netcracker.solutions.kpi.persistence.model.adapter;

import com.google.gson.*;
import com.netcracker.solutions.kpi.persistence.model.FormQuestion;
import com.netcracker.solutions.kpi.persistence.model.Interview;
import com.netcracker.solutions.kpi.persistence.model.User;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * @author Korzh
 */
public class InterviewAdapter extends AbstractQuestionsWithAnswersAdapter implements JsonSerializer<Interview> {

    @Override
    public JsonElement serialize(Interview interview, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", interview.getId());
        jsonObject.addProperty("mark", interview.getMark());
        jsonObject.addProperty("date", interview.getDate().toString());
        JsonObject jsonInterviewer = new JsonObject();
        User interviewer = interview.getInterviewer();
        jsonInterviewer.addProperty("id", interview.getId());
        jsonInterviewer.addProperty("firstName", interviewer.getFirstName());
        jsonInterviewer.addProperty("lastName", interviewer.getLastName());
        jsonInterviewer.addProperty("secondName", interviewer.getSecondName());
        jsonObject.add("interviewer", jsonInterviewer);
        jsonObject.addProperty("role", interview.getRole().getId());
        jsonObject.addProperty("adequateMark", interview.isAdequateMark());
        jsonObject.addProperty("applicationForm", interview.getApplicationForm().getId());
        JsonArray jsonQuestions = new JsonArray();
        Map<FormQuestion, JsonObject> questionsMap = generateQuestionsAndAnswers(interview.getAnswers());
        for (FormQuestion question : questionsMap.keySet()) {
            jsonQuestions.add(questionsMap.get(question));
        }
        jsonObject.add("questions", jsonQuestions);
        return jsonObject;
    }
}
