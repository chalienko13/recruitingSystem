package com.netcracker.solutions.kpi.persistence.dao;

import com.netcracker.solutions.kpi.persistence.model.EmailTemplate;
import com.netcracker.solutions.kpi.persistence.model.NotificationType;

/**
 * @author Korzh
 */
public interface EmailTemplateDao {

    EmailTemplate getById(Long id);

    EmailTemplate getByTitle(String title);

    EmailTemplate getByNotificationType(NotificationType notificationType);

    int updateEmailTemplate(EmailTemplate emailTemplate);

    int deleteEmailTemplate(EmailTemplate emailTemplate);

}
