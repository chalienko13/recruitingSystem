package com.netcracker.solutions.kpi.controller.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TokenAuthenticationService {
    private static final String AUTH_HEADER_NAME_LOGIN_PASSWORD = "X-AUTH-TOKEN_LOGIN_PASSWORD";
    private static final String AUTH_HEADER_NAME_SOCIAL = "X-AUTH-TOKEN_SOCIAL";
    private static Logger log = LoggerFactory.getLogger(TokenAuthenticationService.class.getName());
    private final TokenHandler tokenHandler;

    public TokenAuthenticationService(TokenHandler tokenHandler) {
        this.tokenHandler = tokenHandler;
    }

    public String addAuthentication(HttpServletResponse response, Authentication authentication) {
        UserAuthentication userAuthentication = ((UserAuthentication) authentication);
        return addToken(userAuthentication, response);
    }

    public Authentication getAuthentication(HttpServletRequest request, HttpServletResponse response) {
        log.info("Get token from query");
        String token = null;
        if (request.getHeader(AUTH_HEADER_NAME_LOGIN_PASSWORD) != null) {
            token = request.getHeader(AUTH_HEADER_NAME_LOGIN_PASSWORD);
        } else if (request.getHeader(AUTH_HEADER_NAME_SOCIAL) != null) {
            token = request.getHeader(AUTH_HEADER_NAME_SOCIAL);
        }
        if (token != null) {
            log.info("Token found");
            UserAuthentication user = tokenHandler.parseUserFromToken(token);
            if (user != null) {
                log.info("User found");
                addToken(user, response);
                return user;
            }
        }
        log.info("User with token - {} not exist", token);
        return null;
    }

    private String addToken(UserAuthentication userAuthentication, HttpServletResponse response) {
        String token = tokenHandler.createTokenForUser(userAuthentication);
        log.info("Add token to header");
        if (tokenHandler instanceof TokenHandlerLoginPassword) {
            response.addHeader(AUTH_HEADER_NAME_LOGIN_PASSWORD, token);
        }

        return token;
    }
}
