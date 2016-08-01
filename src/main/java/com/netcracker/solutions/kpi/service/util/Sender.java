package com.netcracker.solutions.kpi.service.util;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by dima on 13.04.16.
 */
public class Sender {

    private String username;
    private String password;
    private Properties props;

    private String MAIL_SMTP_CONNECTIONTIMEOUT = "mail.smtp.connectiontimeout";
    private String MAIL_SMTP_TIMEOUT = "mail.smtp.timeout";
    private String MAIL_SMTP_WRITETIMEOUT = "mail.smtp.writetimeout";
    private String MAIL_SOCKET_TIMEOUT = "15000";

    public Sender(String username, String password) {
        this.username = username;
        this.password = password;

        props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put(MAIL_SMTP_CONNECTIONTIMEOUT, MAIL_SOCKET_TIMEOUT);
        props.put(MAIL_SMTP_TIMEOUT, MAIL_SOCKET_TIMEOUT);
        props.put(MAIL_SMTP_WRITETIMEOUT, MAIL_SOCKET_TIMEOUT);

    }

    public void send(String subject, String text, String toEmail) throws MessagingException {
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });


        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
        message.setSubject(subject);
        message.setText(text);
        Transport.send(message);
    }

}
