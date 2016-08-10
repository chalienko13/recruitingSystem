package com.netcracker.solutions.kpi.service;

import com.netcracker.solutions.kpi.persistence.model.NotificationType;

import java.util.Set;

public interface NotificationTypeService {
    NotificationType getById(Long id);

    NotificationType getByTitle(String title);

    Set<NotificationType> getAll();
}
