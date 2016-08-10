package com.netcracker.solutions.kpi.controller.admin;

import com.netcracker.solutions.kpi.persistence.model.EmailTemplate;
import com.netcracker.solutions.kpi.persistence.model.NotificationType;
import com.netcracker.solutions.kpi.service.NotificationTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Set;

@RestController
@RequestMapping("/admin")
public class AdminNotificationController {

    @Autowired
    private NotificationTypeService notificationTypeService;

    @RequestMapping(value = "/getAllNotificationType", method = RequestMethod.GET)
    public Set<NotificationType> getAllNotificationType() {
        return notificationTypeService.getAll();
    }

    @RequestMapping(value = "/showTemplate", method = RequestMethod.GET)
    public EmailTemplate showTemplate(@RequestParam String title) {
        throw new NotImplementedException();
    }

    @RequestMapping(value = "/changeNotification", method = RequestMethod.POST)
    public void changeNotification(@RequestBody EmailTemplate emailTemplate) {
        throw new NotImplementedException();
    }
}
