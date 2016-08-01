package com.netcracker.solutions.kpi.persistence.model.adapter;

import com.google.gson.*;
import com.netcracker.solutions.kpi.persistence.model.FormAnswerVariant;
import com.netcracker.solutions.kpi.persistence.model.FormQuestion;
import com.netcracker.solutions.kpi.persistence.model.QuestionType;
import com.netcracker.solutions.kpi.persistence.model.impl.real.FormAnswerVariantImpl;
import com.netcracker.solutions.kpi.persistence.model.impl.real.FormQuestionImpl;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chalienko on 03.05.2016.
 */
public class FormQuestionAdapter implements JsonSerializer<FormQuestion>,JsonDeserializer<FormQuestion> {
    @Override
    public JsonElement serialize(FormQuestion formQuestion, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", formQuestion.getId());
        jsonObject.addProperty("title", formQuestion.getTitle());
        jsonObject.addProperty("type", formQuestion.getQuestionType().getTypeTitle());
        jsonObject.addProperty("mandatory", formQuestion.isMandatory());
        jsonObject.addProperty("enable", formQuestion.isEnable());
        jsonObject.addProperty("order", formQuestion.getOrder());
        JsonArray jsonAnswerVariants = new JsonArray();
        if (formQuestion.getFormAnswerVariants() != null) {
            for (FormAnswerVariant variant : formQuestion.getFormAnswerVariants()) {
                JsonObject jsonAnswerVariant = new JsonObject();
                jsonAnswerVariant.addProperty("variant", variant.getAnswer());
                jsonAnswerVariants.add(jsonAnswerVariant);
            }
            jsonObject.addProperty("variants", String.valueOf(jsonAnswerVariants));
        }
        return jsonObject;
    }


    public FormQuestion deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        FormQuestion formQuestion = new FormQuestionImpl();
        JsonObject jsonObject = (JsonObject) jsonElement;
        formQuestion.setId(jsonObject.get("id").getAsLong());
        formQuestion.setTitle(jsonObject.get("title").getAsString());
        formQuestion.setQuestionType(new QuestionType(jsonObject.get("type").getAsString()));
        formQuestion.setMandatory(jsonObject.get("mandatory").getAsBoolean());
        formQuestion.setEnable(jsonObject.get("enable").getAsBoolean());
        List<FormAnswerVariant> formAnswerVariantList = new ArrayList<>();
        for(JsonElement arrayElement: jsonObject.get("variants").getAsJsonArray()) {
                JsonObject jsonVariant = (JsonObject) arrayElement;
            if (!jsonVariant.get("variant").getAsString().isEmpty()) {
                formAnswerVariantList.add(new FormAnswerVariantImpl(jsonVariant.get("variant").getAsString()));
            }
        }
        formQuestion.setFormAnswerVariants(formAnswerVariantList);
        return formQuestion;
    }
}
