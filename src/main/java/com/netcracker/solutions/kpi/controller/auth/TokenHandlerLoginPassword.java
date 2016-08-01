package com.netcracker.solutions.kpi.controller.auth;

import com.netcracker.solutions.kpi.persistence.model.User;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("TokenHandlerLoginPassword")
public class TokenHandlerLoginPassword extends TokenHandler {
    private Logger log = LoggerFactory.getLogger(TokenHandlerLoginPassword.class);

    @Autowired
    private UserAuthServiceLoginPassword userService;

    /*private static class TokenHandlerLoginPasswordHolder {
        private static final TokenHandlerLoginPassword HOLDER = new TokenHandlerLoginPassword();
    }

    private TokenHandlerLoginPassword() {
        super();
        userService = UserAuthServiceLoginPassword.getInstance();
        log = LoggerFactory.getLogger(TokenHandlerLoginPassword.class);
    }

    public static TokenHandlerLoginPassword getInstance() {
        return TokenHandlerLoginPasswordHolder.HOLDER;
    }
*/
    public UserAuthentication parseUserFromToken(String token) {

        log.info("Start parsing tokne - {}", token);
        try {
            String subject = parse(token);
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
        log.info("Start create token for user - {}", user.getEmail());
        return createToken(user.getUsername());

    }

}
