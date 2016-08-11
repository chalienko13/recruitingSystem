package com.netcracker.solutions.kpi.config;

import com.netcracker.solutions.kpi.service.impl.MailSenderImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailSender;

import javax.annotation.Resource;
import java.util.Properties;

@PropertySource("classpath:app.properties")
@Configuration
public class MailConfig {
    private String MAIL_SMTP_CONNECTIONTIMEOUT = "mail.smtp.connectiontimeout";
    private String MAIL_SMTP_TIMEOUT = "mail.smtp.timeout";
    private String MAIL_SMTP_WRITETIMEOUT = "mail.smtp.writetimeout";
    private String MAIL_SOCKET_TIMEOUT = "15000";

    private Integer DEFAULT_SENDER_BATCH_SIZE = 100;

    @Resource
    private Environment env;

    @Bean
    public MailSender mailSender() {
        MailSenderImpl mailSender = new MailSenderImpl();

        mailSender.setUsername(env.getRequiredProperty("sender.email"));
        mailSender.setPassword(env.getRequiredProperty("sender.password"));

        mailSender.setBatchSize(env.getProperty("sender.batch.size", Integer.class, DEFAULT_SENDER_BATCH_SIZE));

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put(MAIL_SMTP_CONNECTIONTIMEOUT, MAIL_SOCKET_TIMEOUT);
        props.put(MAIL_SMTP_TIMEOUT, MAIL_SOCKET_TIMEOUT);
        props.put(MAIL_SMTP_WRITETIMEOUT, MAIL_SOCKET_TIMEOUT);

        mailSender.setJavaMailProperties(props);

        return mailSender;
    }
}
