package com.netcracker.solutions.kpi.service.impl;

import org.springframework.mail.*;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.*;

public class MailSenderImpl extends JavaMailSenderImpl implements MailSender
{
    private int batchSize = 100;

    public void setBatchSize(int batchSize)
    {
        this.batchSize = batchSize;
    }

    @Override
    public void send(SimpleMailMessage simpleMessage) throws MailException
    {
        simpleMessage.setFrom(getUsername());

        for(int i=0; i < simpleMessage.getTo().length; i+=batchSize)
        {
            SimpleMailMessage batchMail = new SimpleMailMessage();
            simpleMessage.copyTo(batchMail);

            batchMail.setTo(getUsername());

            int lastIndex = i+batchSize < simpleMessage.getTo().length ? i+batchSize : simpleMessage.getTo().length;
            batchMail.setBcc(Arrays.copyOfRange(simpleMessage.getTo(), i, lastIndex));

            super.send(batchMail);
        }
    }

    @Override
    public void send(SimpleMailMessage... simpleMessages) throws MailException
    {
        super.send(simpleMessages);
    }
}
