package com.netcracker.solutions.kpi.service;

import com.netcracker.solutions.kpi.persistence.model.Recruitment;

public interface EmailService {
    void sendRegistrationConfirmation(String email) throws Exception;

    void sendCreationNotification(String email) throws Exception;

    void sendPasswordRecovery(String email) throws Exception ;

    void sendInterviewInvitation(Recruitment recruitment);

    void sendInterviewResults(Recruitment recruitment);
}
