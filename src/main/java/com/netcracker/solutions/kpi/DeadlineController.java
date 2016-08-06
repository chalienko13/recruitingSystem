package com.netcracker.solutions.kpi;

import com.netcracker.solutions.kpi.persistence.model.ApplicationForm;
import com.netcracker.solutions.kpi.persistence.model.Recruitment;
import com.netcracker.solutions.kpi.persistence.model.enums.StatusEnum;
import com.netcracker.solutions.kpi.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.Timestamp;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class DeadlineController {
    private static final ScheduledExecutorService registeredStatusDeadline = Executors.newScheduledThreadPool(1);
    private static final ScheduledExecutorService endOfRecruitingDeadLine = Executors.newScheduledThreadPool(1);

    @Autowired
    private ApplicationFormService applicationFormService;

    @Autowired
    private RecruitmentService recruitmentService;

    @Autowired
    private UserService userService;

    @Autowired
    private SchedulingSettingsService scheduleSettingsService;

    @Autowired
    private ScheduleTimePointService scheduleTimePointService;

    @PostConstruct
    public void init() {
        Recruitment recruitment = recruitmentService.getLastRecruitment();
        if (null != recruitment) {
            checkEndOfRecruitmentDeadLine(recruitment);
            checkRegisteredDeadLine(recruitment);
        }
    }

    public void setRegisteredDeadline(Timestamp date) {
        registeredStatusDeadline.schedule(() -> {
            actionForRegisteredDeadline();
        }, calculateDate(date), TimeUnit.MILLISECONDS);
    }

    public void setEndOfRecruitingDeadLine(Timestamp date) {
        endOfRecruitingDeadLine.schedule(() -> {
            actionForEndOfRecruitingDeadLne();
            registeredStatusDeadline.shutdown();
        }, calculateDate(date), TimeUnit.MILLISECONDS);
    }

    private void checkEndOfRecruitmentDeadLine(Recruitment recruitment) {
        if (recruitment.getEndDate().getTime() >= System.currentTimeMillis()) {
            setEndOfRecruitingDeadLine(recruitment.getEndDate());
        } else {
            actionForEndOfRecruitingDeadLne(recruitment);
        }
    }

    private void checkRegisteredDeadLine(Recruitment recruitment) {
        if (recruitment.getRegistrationDeadline().getTime() >= System.currentTimeMillis()) {
            setRegisteredDeadline(recruitment.getRegistrationDeadline());
        } else {
            actionForRegisteredDeadline();
        }
    }

    private void actionForRegisteredDeadline() {
        applicationFormService.changeCurrentsAppFormStatus(StatusEnum.REGISTERED.getId(), StatusEnum.IN_REVIEW.getId());
    }

    private void actionForEndOfRecruitingDeadLne(Recruitment recruitment) {
        endOfRecruitmentMagic(recruitment);
    }

    private void actionForEndOfRecruitingDeadLne() {
        endOfRecruitmentMagic(recruitmentService.getCurrentRecruitmnet());
    }

    private void endOfRecruitmentMagic(Recruitment recruitment) {
        for (ApplicationForm applicationForm : applicationFormService.getByRecruitment(recruitment)) {
            applicationForm.setActive(false);
            applicationFormService.updateApplicationForm(applicationForm);
        }
        userService.disableAllStaff();
        scheduleSettingsService.deleteAll();
        scheduleTimePointService.deleteAll();
    }


    private Long calculateDate(Timestamp date) {
        return date.getTime() - System.currentTimeMillis();
    }
}
