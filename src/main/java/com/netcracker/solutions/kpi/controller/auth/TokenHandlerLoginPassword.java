package com.netcracker.solutions.kpi.controller.auth;

import com.netcracker.solutions.kpi.persistence.model.User;
import com.netcracker.solutions.kpi.util.TokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("TokenHandlerLoginPassword")
public class TokenHandlerLoginPassword {
    private Logger log = LoggerFactory.getLogger(TokenHandlerLoginPassword.class);

    @Autowired
    private UserAuthServiceLoginPassword userService;

    @Autowired
    private TokenUtil tokenUtil;

    public UserAuthentication parseUserFromToken(String token) {

        try {
            String subject = tokenUtil.extractEmail(token);
            User user = userService.loadUserByUsername(subject);
            if (null != user) {
                return new UserAuthentication(user);
            }
        } catch (ExpiredJwtException e) {
            log.error("Token expired", e);
        }
        return null;
    }

    public String createTokenForUser(UserAuthentication userAuthentication) {
        User user = userAuthentication.getDetails();
        return tokenUtil.generateToken(user.getUsername(), 1000 * 24 * 60 * 60L);

    }

}
