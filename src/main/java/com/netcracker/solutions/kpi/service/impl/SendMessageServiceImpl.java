package com.netcracker.solutions.kpi.service.impl;

import com.netcracker.solutions.kpi.persistence.dao.SendMessageDao;
import com.netcracker.solutions.kpi.persistence.model.Message;
import com.netcracker.solutions.kpi.service.SendMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SendMessageServiceImpl implements SendMessageService {

    @Autowired
    private SendMessageDao sendMessageDao;

    /*public SendMessageServiceImpl(SendMessageDao sendMessageDao) {
        this.sendMessageDao = sendMessageDao;
    }*/

    @Override
    public Message getById(Long id) {
        return sendMessageDao.getById(id);
    }

    @Override
    public Message getBySubject(String subject) {
        return sendMessageDao.getBySubject(subject);
    }

    @Override
    public Long insert(Message message) {
        return sendMessageDao.insert(message);
    }

    @Override
    public int delete(Message message) {
        return sendMessageDao.delete(message);
    }

    @Override
    public List<Message> getAll() {
        return sendMessageDao.getAll();
    }

    @Override
    public int update(Message message) {
        return sendMessageDao.update(message);
    }

    @Override
    public boolean isExsist(Message message) {
        return sendMessageDao.isExist(message);
    }

}
