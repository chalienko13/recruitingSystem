package com.netcracker.solutions.kpi.controller.auth;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.netcracker.solutions.kpi.persistence.model.User;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("TokenHandlerSocial")
public class TokenHandlerSocial extends TokenHandler {
    private Logger log = LoggerFactory.getLogger(TokenHandlerSocial.class);

    @Autowired
    private UserAuthServiceSocial userAuthServiceSocial;

    /*private static class TokenHandlerSocialHolder {
        private static final TokenHandlerSocial HOLDER = new TokenHandlerSocial();
    }

    public static TokenHandlerSocial getInstance() {
        return TokenHandlerSocialHolder.HOLDER;
    }

    private TokenHandlerSocial() {
        super();
        userAuthServiceSocial = UserAuthServiceSocial.getInstance();
        log = LoggerFactory.getLogger(TokenHandlerSocial.class);
    }*/

    @Override
    public UserAuthentication parseUserFromToken(String token) {
        try {
            String subject = parse(token);
            JsonObject jsonObject = ((JsonObject) new JsonParser().parse(subject));
            Long idUserSocial = jsonObject.get("idUserSocial").getAsLong();
            Long idNetwork = jsonObject.get("idNetwork").getAsLong();
            User user = userAuthServiceSocial.loadUserBySocialIdNetworkId(idUserSocial, idNetwork);
            if (null != user) {
                return new UserAuthentication(user, idUserSocial, idNetwork);
            }
        } catch (ExpiredJwtException e) {
            log.error("Token expired", e);
        }
        return null;
    }

    @Override
    public String createTokenForUser(UserAuthentication userAuthentication) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("idUserSocial", userAuthentication.getIdUserSocialNetwork());
        jsonObject.addProperty("idNetwork", userAuthentication.getIdNetwork());
        return createToken(jsonObject.toString());
    }
}
