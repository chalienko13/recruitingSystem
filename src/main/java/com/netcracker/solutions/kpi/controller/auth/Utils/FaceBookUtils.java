package com.netcracker.solutions.kpi.controller.auth.Utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class FaceBookUtils {

    private static String USER_ID = "userID";
    private static String ACCESS_TOKEN_TITLE = "accessToken";

    public static Long getSocialUserId(String info){
        return ((JsonObject) new JsonParser().parse(info)).get(USER_ID).getAsLong();
    }

    public static  String getFaceBookAccessToken(String info) {
        return ((JsonObject) new JsonParser().parse(info)).get(ACCESS_TOKEN_TITLE).getAsString();
    }
}
