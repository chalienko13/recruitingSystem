package com.netcracker.solutions.kpi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by pasy0716 on 04.08.2016.
 */
@Controller
public class MainPageController {
    @RequestMapping(value = "/")
    public String mainPage() {
        return "index";
    }

}
