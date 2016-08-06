package com.netcracker.solutions.kpi.persistence.model.adapter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.netcracker.solutions.kpi.persistence.model.ApplicationForm;
import com.netcracker.solutions.kpi.persistence.model.FormQuestion;
import com.netcracker.solutions.kpi.persistence.model.Interview;

/**
 * Created by Chalienko on 25.04.2016.
 */
public class GsonFactory {
    private static Gson applicationFormGson;
    private static Gson userGson;
    private static Gson formQuestionGson;
    private static Gson interviewGson;

    public static Gson getApplicationFormGson() {
        if (applicationFormGson == null) {
            applicationFormGson = new GsonBuilder().registerTypeAdapter(ApplicationForm.class,
                    new ApplicationFormAdapter())
                    .setPrettyPrinting()
                    .create();
        }
        return applicationFormGson;
    }

    public static Gson getInterviewGson() {
        if (interviewGson == null) {
            interviewGson = new GsonBuilder().registerTypeAdapter(Interview.class, new InterviewAdapter())
                    .setPrettyPrinting()
                    .create();
        }
        return interviewGson;
    }

    public static Gson getFormQuestionGson() {
        if (formQuestionGson == null) {
            formQuestionGson = new GsonBuilder().registerTypeAdapter(FormQuestion.class, new FormQuestionAdapter())
                    .setPrettyPrinting()
                    .create();
        }
        return formQuestionGson;
    }
}
