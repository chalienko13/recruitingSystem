package com.netcracker.solutions.kpi.service.util;

import com.netcracker.solutions.kpi.config.PropertiesReader;
import com.netcracker.solutions.kpi.persistence.model.Message;
import com.netcracker.solutions.kpi.service.SendMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
public class SenderServiceImpl implements SenderService {

    private static Logger log = LoggerFactory.getLogger(SenderService.class.getName());

   /* private static volatile SenderServiceImpl instance;

    private SenderServiceImpl() {

    }

    public static SenderServiceImpl getInstance() {
        if (instance == null)
            synchronized (SenderServiceImpl.class) {
                if (instance == null)
                    instance = new SenderServiceImpl();
            }
        return instance;
    }*/


    private PropertiesReader propertiesReader = PropertiesReader.getInstance();

    @Autowired
    private SendMessageService sendMessageService;// = ServiceFactory.getResendMessageService();

    private String email = propertiesReader.propertiesReader("sender.email");
    private String password = propertiesReader.propertiesReader("sender.password");

    private Sender tlsSender = new Sender(email, password);


    @Override
    public void send(String email, String subject, String text) {
        try {
            tlsSender.send(subject, text, email);
        } catch (MessagingException e) {
                log.info("Message not resend {}", e);
                Message message = new Message(subject, text, email, false);
                sendMessageService.insert(message);
        }
    }

}
