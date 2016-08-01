//package ua.kpi.nc.controller.auth;
//
//import com.google.gson.JsonObject;
//import com.google.gson.JsonParser;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//import ServiceFactory;
//import UserService;
//
///**
// * Created by IO on 25.05.2016.
// */
//@RestController
//@RequestMapping(value = "/facebookAuth")
//public class FaceBooAuthController {
//
//    private final static String ACCESS_TOKEN_TITLE = "accessToken";
//    private final static String INFO_OBJECT = "info";
//    private final static String EMAIL_TITLE = "email";
//    private UserService userService = ServiceFactory.getUserService();
//
//    @RequestMapping(method = RequestMethod.POST)
//
//    public ResponseEntity authUser(@RequestBody String info) {
//        JsonObject obj = (JsonObject) new JsonParser().parse(info);
//
//
//        return ResponseEntity.ok(null);
//    }
//
//
//    private String getAccessToken(JsonObject jsonObject) {
//        return jsonObject.getAsJsonObject(INFO_OBJECT).get(ACCESS_TOKEN_TITLE).getAsString();
//    }
//
//    private String getEmail(JsonObject jsonObject) {
//        return jsonObject.getAsJsonObject(INFO_OBJECT).get(EMAIL_TITLE).getAsString();
//    }
//
//}
