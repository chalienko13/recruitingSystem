package com.netcracker.solutions.kpi.service.impl;

import com.netcracker.solutions.kpi.config.ApplicationConfiguration;
import com.netcracker.solutions.kpi.persistence.model.*;
import com.netcracker.solutions.kpi.persistence.model.enums.StatusEnum;
import com.netcracker.solutions.kpi.persistence.repository.EmailTemplateRepository;
import com.netcracker.solutions.kpi.service.*;
import com.netcracker.solutions.kpi.util.TokenUtil;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    @Autowired
    ApplicationConfiguration configuration;

    private static long REG_CONFIRM_TEMPLATE_ID = 2L;
    private static long PASS_RECOVERY_TEMPLATE_ID = 14L;
    private static long USER_CREATED_TEMPLATE_ID = 3L;
    private static long INTERVIEW_INVITE_TEMPLATE_ID = 15L;
    private static long INTERVIEW_RESULT_TEMPLATE_ID = 11L;

    @Override
    public void sendRegistrationConfirmation(String email) {
        User user = userService.getUserByUsername(email);

        SimpleMailMessage simpleMailMessage = getMessage(REG_CONFIRM_TEMPLATE_ID, getContextVariables(user, null));
        simpleMailMessage.setTo(email);

        mailSender.send(simpleMailMessage);
    }

    @Override
    public void sendCreationNotification(String email) {
        User user = userService.getUserByUsername(email);

        SimpleMailMessage simpleMailMessage = getMessage(USER_CREATED_TEMPLATE_ID, getContextVariables(user, null));
        simpleMailMessage.setTo(email);

        mailSender.send(simpleMailMessage);
    }

    @Override
    public void sendPasswordRecovery(String email) throws UsernameNotFoundException {
        User user = userService.getUserByUsername(email);

        Map<String, String> variables = getContextVariables(user, null);
        variables.put("recoveryToken", configuration.serverUrl + "/recoverPassword?token=" + tokenUtil.generateToken(email, configuration.tokenExpireTime));

        SimpleMailMessage simpleMailMessage = getMessage(PASS_RECOVERY_TEMPLATE_ID, variables);
        simpleMailMessage.setTo(email);

        mailSender.send(simpleMailMessage);
    }

    @Override
    public void sendInterviewInvitation(Recruitment recruitment) {
        SimpleMailMessage simpleMailMessage = getMessage(INTERVIEW_INVITE_TEMPLATE_ID, getContextVariables(null, null));

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
            SimpleMailMessage simpleMailMessage = getMessage(INTERVIEW_RESULT_TEMPLATE_ID, getContextVariables(null, null));

            String[] emails = applicationFormService.getByStatusAndRecruitment(status, recruitment)
                    .stream()
                    .map(applicationForm -> applicationForm.getUser().getEmail())
                    .toArray(String[]::new);

            simpleMailMessage.setBcc(emails);

            mailSender.send(simpleMailMessage);
        }
    }

    private SimpleMailMessage getMessage(Long templateId, Map<String, String> variables) {
        EmailTemplate emailTemplate = emailTemplateRepository.findOne(templateId);

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setSubject(emailTemplate.getTitle());

        String text = emailTemplate.getText();
        StrSubstitutor strSubstitutor = new StrSubstitutor(variables);
        text = strSubstitutor.replace(text);
        simpleMailMessage.setText(text);

        log.debug("SimpleMailMessage From Template: {}", simpleMailMessage);
        return simpleMailMessage;
    }

    private Map<String, String> getContextVariables(User user, Map<String, String> vars) {
        HashMap<String, String> contextVariables = new HashMap<>();
        contextVariables.put("server.url", configuration.serverUrl);
        if(vars != null)
            contextVariables.putAll(vars);

        if(user != null) {
            contextVariables.put("firstName", user.getFirstName());
            contextVariables.put("secondName", user.getSecondName());
            contextVariables.put("secondName", user.getLastName());
            contextVariables.put("email", user.getEmail());
            contextVariables.put("id", String.valueOf(user.getId()));
            contextVariables.put("password", user.getPassword());
            contextVariables.put("confirmationLink", user.getConfirmToken());
            contextVariables.put("recoveryPassLink", tokenUtil.generateToken(user.getEmail(), configuration.tokenExpireTime));
        }
        return contextVariables;
    }

}
