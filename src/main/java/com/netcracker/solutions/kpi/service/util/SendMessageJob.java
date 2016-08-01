package com.netcracker.solutions.kpi.service.util;

import com.netcracker.solutions.kpi.persistence.model.Message;
import com.netcracker.solutions.kpi.service.SendMessageService;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.util.List;

@Component
@DisallowConcurrentExecution
public class SendMessageJob extends QuartzJobBean {
    private static Logger log = LoggerFactory.getLogger(SendMessageJob.class.getName());

    @Autowired
    private SendMessageService sendMessageService;// = ServiceFactory.getResendMessageService();

    @Autowired
    private SenderService senderService;// = SenderServiceImpl.getInstance();

    @Override
    protected void executeInternal(JobExecutionContext ctx) throws JobExecutionException {
        List<Message> messageServiceList = sendMessageService.getAll();
        for (Message message : messageServiceList) {
            try {
                sendMessageService.delete(message);
                senderService.send(message.getEmail(), message.getSubject(), message.getText());
            } catch (MessagingException e) {
                log.error("Cannot send message {}",e);
            }
        }

        JobKey jobKey = ctx.getJobDetail().getKey();
        log.info("Job key: {}", jobKey);
    }

}
