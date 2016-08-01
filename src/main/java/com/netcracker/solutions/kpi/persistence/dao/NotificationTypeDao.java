package com.netcracker.solutions.kpi.persistence.dao;

import com.netcracker.solutions.kpi.persistence.model.NotificationType;

import java.util.Set;

/**
 * Created by dima on 27.04.16.
 */
public interface NotificationTypeDao {

    public NotificationType getById(Long id);

    public NotificationType getByTitle(String title);

    public int updateNotificationType(NotificationType notificationType);

    public int deleteNotificationType(NotificationType notificationType);

    public Set<NotificationType> getAll();
}
