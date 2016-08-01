package com.netcracker.solutions.kpi.persistence.dao;

import com.netcracker.solutions.kpi.persistence.model.Message;

import java.util.List;

/**
 * @author Korzh
 */
public interface SendMessageDao {

    Message getById(Long id);

    Message getBySubject(String subject);

    Long insert(Message message);

    int delete(Message message);

    List<Message> getAll();

    int update(Message message);

    boolean isExist(Message message);


}
