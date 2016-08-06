package com.netcracker.solutions.kpi.service.impl;

import com.netcracker.solutions.kpi.persistence.model.Recruitment;
import com.netcracker.solutions.kpi.persistence.model.Status;
import com.netcracker.solutions.kpi.persistence.model.User;
import com.netcracker.solutions.kpi.service.*;
import com.netcracker.solutions.kpi.util.TokenUtil;
import javassist.NotFoundException;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    UserService userService;

    @Autowired
    ApplicationFormService applicationFormService;

    @Autowired
    StatusService statusService;

    @Autowired
    MailSender mailSender;

    @Autowired
    TokenUtil tokenUtil;

    @Override
    public void sendRegistrationConfirmation(String email) throws Exception {
        if(!userService.isExist(email)) {
            throw new Exception();
        }

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("[NetCracker Courses] Confirm your registration");
        simpleMailMessage.setText("Congratulations");

        mailSender.send(simpleMailMessage);
    }

    @Override
    public void sendCreationNotification(String email) throws Exception {
        if(!userService.isExist(email)) {
            throw new Exception();
        }

        User user = userService.getUserByUsername(email);
        user.setConfirmToken(tokenUtil.generateToken(email, 24*60*60*1000L));
        userService.updateUser(user);

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("[NetCracker Courses] Your account was created");
        simpleMailMessage.setText("For recovery follow link: "+user.getConfirmToken());

        mailSender.send(simpleMailMessage);
    }

    @Override
    public void sendPasswordRecovery(String email) throws Exception {
        if(!userService.isExist(email)) {
            throw new Exception();
        }

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("[NetCracker Courses] Password Recovery");
        simpleMailMessage.setText("For recovery follow link: "+tokenUtil.generateToken(email, 24*60*60*1000L));

        mailSender.send(simpleMailMessage);
    }

    @Override
    public void sendInterviewInvitation(Recruitment recruitment) {
        for(Status status : statusService.getAllStatuses()) {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

            String[] emails = applicationFormService.getByStatusAndRecruitment(status, recruitment)
                                    .stream()
                                    .filter(applicationForm -> applicationForm.getStatus().equals(status))
                                    .map(applicationForm -> applicationForm.getUser().getEmail())
                                    .toArray(String[]::new);

            simpleMailMessage.setBcc(emails);

            simpleMailMessage.setSubject("[NetCracker Courses] Interview Invitation");
            simpleMailMessage.setText("GOOD JOB MY FRIEND!!!");

            mailSender.send(simpleMailMessage);
        }
    }

    @Override
    public void sendInterviewResults(Recruitment recruitment) {
        throw new NotImplementedException();
    }
}
