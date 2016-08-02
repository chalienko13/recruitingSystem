package com.netcracker.solutions.kpi.service;

import com.netcracker.solutions.kpi.persistence.model.EmailTemplate;
import com.netcracker.solutions.kpi.persistence.model.NotificationType;

/**
 * Created by Chalienko on 21.04.2016.
 */
public interface EmailTemplateService {

    EmailTemplate getById(Long id);

    EmailTemplate getByTitle(String title);

    EmailTemplate getByNotificationType(NotificationType notificationType);

    int updateEmailTemplate(EmailTemplate emailTemplate);

    int deleteEmailTemplate(EmailTemplate emailTemplate);

    String showTemplateParams(String inputText, User user);

}
