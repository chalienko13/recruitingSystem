package com.netcracker.solutions.kpi.service.impl;

import com.netcracker.solutions.kpi.persistence.dao.NotificationTypeDao;
import com.netcracker.solutions.kpi.persistence.model.NotificationType;
import com.netcracker.solutions.kpi.service.NotificationTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional
public class NotificationTypeServiceImpl implements NotificationTypeService {

    @Autowired
    private NotificationTypeDao notificationTypeDao;

    @Override
    public NotificationType getById(Long id) {
        return notificationTypeDao.getById(id);
    }

    @Override
    public NotificationType getByTitle(String title) {
        return notificationTypeDao.getByTitle(title);
    }

    @Override
    public Set<NotificationType> getAll() {
        return notificationTypeDao.getAll();
    }
}
