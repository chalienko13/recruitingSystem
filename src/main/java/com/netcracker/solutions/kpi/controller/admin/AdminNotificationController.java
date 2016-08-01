package com.netcracker.solutions.kpi.controller.admin;

import com.netcracker.solutions.kpi.persistence.model.EmailTemplate;
import com.netcracker.solutions.kpi.persistence.model.NotificationType;
import com.netcracker.solutions.kpi.service.EmailTemplateService;
import com.netcracker.solutions.kpi.service.NotificationTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/admin")
public class AdminNotificationController {

    @Autowired
    private NotificationTypeService notificationTypeService;// = ServiceFactory.getNotificationTypeService();
    @Autowired
    private EmailTemplateService emailTemplateService;// = ServiceFactory.getEmailTemplateService();

    @RequestMapping(value = "/getAllNotificationType", method = RequestMethod.GET)
    public Set<NotificationType> getAllNotificationType() {
        return notificationTypeService.getAll();
    }

    @RequestMapping(value = "/showTemplate", method = RequestMethod.GET)
    public EmailTemplate showTemplate(@RequestParam String title) {
        return emailTemplateService.getByTitle(title);
    }

    @RequestMapping(value = "/changeNotification", method = RequestMethod.POST)
    public void changeNotification(@RequestBody EmailTemplate emailTemplate) {
        emailTemplateService.updateEmailTemplate(emailTemplate);
    }
}
