package com.netcracker.solutions.kpi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainPageController {
    @RequestMapping(value = "/")
    public String mainPage() {
        return "index";
    }

}
