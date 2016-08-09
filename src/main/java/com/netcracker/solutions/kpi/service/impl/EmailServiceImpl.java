package com.netcracker.solutions.kpi.service.impl;

import com.netcracker.solutions.kpi.persistence.model.*;
import com.netcracker.solutions.kpi.persistence.model.enums.StatusEnum;
import com.netcracker.solutions.kpi.persistence.repository.EmailTemplateRepository;
import com.netcracker.solutions.kpi.service.*;
import com.netcracker.solutions.kpi.util.TokenUtil;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class EmailServiceImpl implements EmailService {
    private Logger log = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Autowired
    UserService userService;

    @Autowired
    ApplicationFormService applicationFormService;

    @Autowired
    EmailTemplateRepository emailTemplateRepository;

    @Autowired
    StatusService statusService;

    @Autowired
    MailSender mailSender;

    @Autowired
    TokenUtil tokenUtil;

    private static long EXPIRE_TIME = 24*60*60*1000L;

    private static long REG_CONFIRM_TEMPLATE_ID = 2L;
    private static long PASS_RECOVERY_TEMPLATE_ID = 14L;
    private static long USER_CREATED_TEMPLATE_ID = 3L;
    private static long INTERVIEW_INVITE_TEMPLATE_ID = 15L;
    private static long INTERVIEW_RESULT_TEMPLATE_ID = 11L;

    @Override
    public void sendRegistrationConfirmation(String email) throws Exception {
        User user = userService.getUserByUsername(email);
        if (user == null) {
            throw new Exception();
        }

        user.setConfirmToken(tokenUtil.generateToken(email, EXPIRE_TIME));
        userService.updateUser(user);

        SimpleMailMessage simpleMailMessage = getMessage(REG_CONFIRM_TEMPLATE_ID, user, null);
        simpleMailMessage.setTo(email);

        mailSender.send(simpleMailMessage);
    }

    @Override
    public void sendCreationNotification(String email) throws Exception {
        User user = userService.getUserByUsername(email);
        if (user == null) {
            throw new Exception();
        }

        SimpleMailMessage simpleMailMessage = getMessage(USER_CREATED_TEMPLATE_ID, user, null);
        simpleMailMessage.setTo(email);

        mailSender.send(simpleMailMessage);
    }

    @Override
    public void sendPasswordRecovery(String email) throws Exception {
        User user = userService.getUserByUsername(email);
        if (user == null) {
            throw new Exception();
        }

        Map<String, String> variables = new HashMap<>();
        variables.put("recoveryToken", tokenUtil.generateToken(email, EXPIRE_TIME));

        SimpleMailMessage simpleMailMessage = getMessage(PASS_RECOVERY_TEMPLATE_ID, user, null);
        simpleMailMessage.setTo(email);

        mailSender.send(simpleMailMessage);
    }

    @Override
    public void sendInterviewInvitation(Recruitment recruitment) {
        SimpleMailMessage simpleMailMessage = getMessage(INTERVIEW_INVITE_TEMPLATE_ID, null, null);

        String[] emails = applicationFormService.getByStatusAndRecruitment(StatusEnum.APPROVED.getStatus(), recruitment)
                .stream()
                .map(applicationForm -> applicationForm.getUser().getEmail())
                .toArray(String[]::new);

        simpleMailMessage.setBcc(emails);

        mailSender.send(simpleMailMessage);
    }

    @Override
    public void sendInterviewResults(Recruitment recruitment) {
        for(Status status : new Status[]{StatusEnum.APPROVED_TO_ADVANCED_COURSES.getStatus(),
                                         StatusEnum.APPROVED_TO_ADVANCED_COURSES.getStatus(),
                                         StatusEnum.APPROVED_TO_JOB.getStatus()}) {
            SimpleMailMessage simpleMailMessage = getMessage(INTERVIEW_RESULT_TEMPLATE_ID, null, null);

            String[] emails = applicationFormService.getByStatusAndRecruitment(status, recruitment)
                    .stream()
                    .map(applicationForm -> applicationForm.getUser().getEmail())
                    .toArray(String[]::new);

            simpleMailMessage.setBcc(emails);

            mailSender.send(simpleMailMessage);
        }
    }

    private SimpleMailMessage getMessage(Long templateId, User user, Map<String, String> variables) {
        EmailTemplate emailTemplate = emailTemplateRepository.findOne(templateId);

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setSubject(emailTemplate.getTitle());

        String text = emailTemplate.getText();
        if(user != null) {
            text = text
                    .replace("%firstName%", user.getFirstName())
                    .replace("%secondName%", user.getSecondName())
                    .replace("%secondName%", user.getLastName())
                    .replace("%email%", user.getEmail())
                    .replace("%id%", String.valueOf(user.getId()))
                    .replace("%password%", user.getPassword())
                    .replace("%confirmationLink%", user.getConfirmToken())
                    .replace("%recoveryPassLink%", tokenUtil.generateToken(user.getEmail(), EXPIRE_TIME));
        }
        if(variables != null) {
            for(Map.Entry<String, String> variable : variables.entrySet()) {
                text = text.replace("%"+variable.getKey()+"%", variable.getValue());
            }
        }
        simpleMailMessage.setText(text);

        log.debug("SimpleMailMessage From Template: {}", simpleMailMessage);
        return simpleMailMessage;
    }

}
