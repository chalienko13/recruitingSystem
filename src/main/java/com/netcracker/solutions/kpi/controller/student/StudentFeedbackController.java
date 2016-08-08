package com.netcracker.solutions.kpi.controller.student;

import com.netcracker.solutions.kpi.config.auth.CustomUserDetails;
import com.netcracker.solutions.kpi.persistence.dto.ApplicationFormDto;
import com.netcracker.solutions.kpi.persistence.model.ApplicationForm;
import com.netcracker.solutions.kpi.persistence.model.User;
import com.netcracker.solutions.kpi.service.ApplicationFormService;
import com.netcracker.solutions.kpi.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/student")
public class StudentFeedbackController {
    private static Logger log = LoggerFactory.getLogger(RegistrationController.class.getName());

    @Autowired
    private ApplicationFormService applicationFormService;
    @Autowired
    private UserService userService;


    @RequestMapping(value = "saveFeedBack", method = RequestMethod.POST)
    public void getFeedback(@RequestParam String feedBack) {
        log.info("Getting last application form");
        ApplicationForm applicationForm = applicationFormService.getLastApplicationFormByUserId(
                ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()).getId());
        applicationForm.setFeedback(feedBack);
        log.info("Save feedback in application form id - {}", applicationForm.getId());
        applicationFormService.updateApplicationForm(applicationForm);
    }

    @RequestMapping(value = "getFeedBack", method = RequestMethod.GET)
    public ApplicationFormDto getFeedBack(@RequestParam String id) {
        User user = userService.getUserByID(Long.parseLong(id));
        if (null != user) {
            return new ApplicationFormDto(applicationFormService.getLastApplicationFormByUserId(user.getId()).getFeedback());
        } else {
            return null;
        }
    }
}
