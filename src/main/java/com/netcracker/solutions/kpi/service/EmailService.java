package com.netcracker.solutions.kpi.service;

import com.netcracker.solutions.kpi.persistence.model.Recruitment;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface EmailService {
    void sendRegistrationConfirmation(String email) throws UsernameNotFoundException;

    void sendCreationNotification(String email) throws UsernameNotFoundException;

    void sendPasswordRecovery(String email) throws UsernameNotFoundException;

    void sendInterviewInvitation(Recruitment recruitment);

    void sendInterviewResults(Recruitment recruitment);
}
