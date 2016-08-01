package com.netcracker.solutions.kpi.persistence.dao;

import com.netcracker.solutions.kpi.persistence.model.ResendMessage;

import java.util.List;

/**
 * @author Korzh
 */
public interface ResendMessageDao {

    ResendMessage getById(Long id);

    ResendMessage getBySubject(String subject);

    Long insert(ResendMessage resendMessage);

    int delete(ResendMessage resendMessage);

    List<ResendMessage> getAll();


}
