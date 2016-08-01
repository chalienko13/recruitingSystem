package com.netcracker.solutions.kpi.service.util;


import javax.mail.MessagingException;

/**
 * Created by dima on 13.04.16.
 */
public interface SenderService {

    void send(String email, String subject, String text) throws MessagingException;

}
