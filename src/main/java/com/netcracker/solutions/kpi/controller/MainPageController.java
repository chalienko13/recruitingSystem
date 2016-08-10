package com.netcracker.solutions.kpi.controller;

import org.springframework.http.MediaType;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainPageController {
    @RequestMapping(value = "/")
    public String mainPage() {
        return "index";
    }

    @ResponseBody
    @RequestMapping(value = {"/authUrl"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public String redirectAuth(SecurityContextHolderAwareRequestWrapper request) {
        if (request.isUserInRole("STUDENT")) {
            return "{\"redirectUrl\" : \"/student/appform\"}";
        } else if (request.isUserInRole("ADMIN")) {
            return "{\"redirectUrl\" : \"/admin/main\"}";
        } else if (request.isUserInRole("TECH") || request.isUserInRole("SOFT")) {
            return "{\"redirectUrl\" : \"/staff/main\"}";
        } else {
            return "{\"redirectUrl\" : \"/\"}";
        }
    }
}
