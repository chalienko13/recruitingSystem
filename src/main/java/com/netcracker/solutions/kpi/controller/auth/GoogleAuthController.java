package com.netcracker.solutions.kpi.controller.auth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by dima on 13.04.16.
 */

@Controller
public class GoogleAuthController {

    //TODO fix
    private String googleId = "379040143751-05sjjdhc4lb8uul12p387isq0h78jto0.apps.googleusercontent.com";


    private XsrfUtils xsrfUtils = XsrfUtils.getInstance();

    @RequestMapping(value = "/login/auth/google/request", method = RequestMethod.GET)
    public void sendRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String state = xsrfUtils.newToken();
        request.getSession().setAttribute(XsrfUtils.XSRF_KEY, state);

        // TODO https://developers.google.com/accounts/docs/OpenIDConnect#discovery
        String location = "https://accounts.google.com/o/oauth2/auth"
                + "?client_id=" + googleId
                + "&response_type=code"
                + "&scope=openid%20email"
                + "&redirect_uri=" + "http://localhost:8082/login/auth/google/response"
                + "&state=" + state;

        response.sendRedirect(location);
    }

}


