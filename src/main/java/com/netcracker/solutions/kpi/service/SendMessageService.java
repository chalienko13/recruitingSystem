package com.netcracker.solutions.kpi.service;

import com.netcracker.solutions.kpi.persistence.model.Message;

import java.util.List;

public interface SendMessageService {

    Message getById(Long id);

    Message getBySubject(String subject);

    Long insert(Message message);

    int delete(Message message);

    List<Message> getAll();

    int update(Message message);

    boolean isExsist(Message message);

}
