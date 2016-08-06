package com.netcracker.solutions.kpi.config;

import com.netcracker.solutions.kpi.service.impl.MailSenderImpl;
import org.springframework.context.annotation.*;
import org.springframework.mail.MailSender;

import java.util.Properties;

@Configuration
public class MailConfig {
    private String MAIL_SMTP_CONNECTIONTIMEOUT = "mail.smtp.connectiontimeout";
    private String MAIL_SMTP_TIMEOUT = "mail.smtp.timeout";
    private String MAIL_SMTP_WRITETIMEOUT = "mail.smtp.writetimeout";
    private String MAIL_SOCKET_TIMEOUT = "15000";

    @Bean
    public MailSender mailSender() {
        MailSenderImpl mailSender = new MailSenderImpl();

        mailSender.setUsername("freest93@gmail.com");
        mailSender.setPassword("");

        mailSender.setBatchSize(100);

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put(MAIL_SMTP_CONNECTIONTIMEOUT, MAIL_SOCKET_TIMEOUT);
        props.put(MAIL_SMTP_TIMEOUT, MAIL_SOCKET_TIMEOUT);
        props.put(MAIL_SMTP_WRITETIMEOUT, MAIL_SOCKET_TIMEOUT);

        mailSender.setJavaMailProperties(props);

        return mailSender;
    }
}
