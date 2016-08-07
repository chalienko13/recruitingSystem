package com.netcracker.solutions.kpi;

import com.netcracker.solutions.kpi.config.DataConfig;
import com.netcracker.solutions.kpi.config.MailConfig;
import com.netcracker.solutions.kpi.persistence.dao.UserDao;
import com.netcracker.solutions.kpi.service.UserService;
import com.netcracker.solutions.kpi.service.impl.MailSenderImpl;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = {MailConfig.class, DataConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class MailSenderTest {
    @Autowired
    MailSender mailSender;

    @Autowired
    UserDao userDao;

    Logger logger = Logger.getLogger(MailSenderTest.class);

    @Test
    public void mailSenderTest() {
        ((MailSenderImpl)mailSender).setBatchSize(2);
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(new String[]{"filimonov@NetCracker.com", "freest93@hotmail.com", "filimonov@NetCracker.com", "freest93@hotmail.com"});
        message.setSubject("Spring Mail Sender Test");
        message.setText("Test Batch");
        logger.debug("trying to send email");

        try
        {
            mailSender.send(message);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
