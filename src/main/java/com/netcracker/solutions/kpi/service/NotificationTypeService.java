package com.netcracker.solutions.kpi.service;

import com.netcracker.solutions.kpi.persistence.model.NotificationType;

import java.util.Set;

/**
 * @author Korzh
 */
public interface NotificationTypeService {
    NotificationType getById(Long id);

    NotificationType getByTitle(String title);

    int updateNotificationType(NotificationType notificationType);

    int deleteNotificationType(NotificationType notificationType);

    Set<NotificationType> getAll();
}
