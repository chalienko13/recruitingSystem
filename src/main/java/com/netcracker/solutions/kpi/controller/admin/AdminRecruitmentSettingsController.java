package com.netcracker.solutions.kpi.controller.admin;

import com.netcracker.solutions.kpi.DeadlineController;
import com.netcracker.solutions.kpi.persistence.dto.MessageDto;
import com.netcracker.solutions.kpi.persistence.dto.RecruitmentSettingsDto;
import com.netcracker.solutions.kpi.persistence.model.Recruitment;
import com.netcracker.solutions.kpi.service.RecruitmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.sql.Timestamp;
import java.util.Date;

@RestController
@RequestMapping("/admin")
public class AdminRecruitmentSettingsController {

    private final static String END_DATE_ERROR = "End Date of recruitment must be after registration deadline and schedule choice deadline";
    private final static String SCHEDULE_DEADLINE_ERROR = "Schedule choice deadline must be after registration deadline ";
    private final static String END_DATE_LESS_THEN_CURRENT_ERROR = "End of recruitment must be after current date";
    @Autowired
    private RecruitmentService recruitmentService;
    @Autowired
    private DeadlineController deadlineController;

    @RequestMapping(value = "/addRecruitment", method = RequestMethod.POST)
    public ResponseEntity addRecruitmentSettings(@RequestBody RecruitmentSettingsDto recruitmentDto) throws MessagingException {
        Timestamp registrationDeadline = Timestamp.valueOf(recruitmentDto.getRegistrationDeadline());
        Timestamp scheduleChoicesDeadline = Timestamp.valueOf(recruitmentDto.getScheduleChoicesDeadline());
        Timestamp endDate = Timestamp.valueOf(recruitmentDto.getEndDate());
        ResponseEntity response = checkTimeValidity(registrationDeadline, scheduleChoicesDeadline, endDate);
        if (null != response) {
            return response;
        }
        Recruitment recruitment = new Recruitment();
        recruitment.setName(recruitmentDto.getName());
        recruitment.setEndDate(endDate);
        recruitment.setStartDate(new Timestamp(System.currentTimeMillis()));
        recruitment.setRegistrationDeadline(registrationDeadline);
        recruitment.setScheduleChoicesDeadline(scheduleChoicesDeadline);

        recruitmentService.addRecruitment(recruitment);

        deadlineController.setRegisteredDeadline(recruitment.getRegistrationDeadline());
        deadlineController.setEndOfRecruitingDeadLine(recruitment.getEndDate());
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    private ResponseEntity checkTimeValidity(Timestamp registrationDeadline, Timestamp scheduleChoicesDeadline, Timestamp endDate) {
        if (endDate.getTime() <= registrationDeadline.getTime() || endDate.getTime() <= scheduleChoicesDeadline.getTime()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageDto(END_DATE_ERROR));
        }
        if (scheduleChoicesDeadline.getTime() <= registrationDeadline.getTime()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageDto(SCHEDULE_DEADLINE_ERROR));
        }
        if (endDate.getTime() <= new Date().getTime()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageDto(END_DATE_LESS_THEN_CURRENT_ERROR));
        }
        return null;
    }

    @RequestMapping(value = "/editRecruitment", method = RequestMethod.POST)
    public ResponseEntity editRecruitment(@RequestBody RecruitmentSettingsDto recruitmentDto) {
        Timestamp registrationDeadline = Timestamp.valueOf(recruitmentDto.getRegistrationDeadline());
        Timestamp scheduleChoicesDeadline = Timestamp.valueOf(recruitmentDto.getScheduleChoicesDeadline());
        Timestamp endDate = Timestamp.valueOf(recruitmentDto.getEndDate());
        ResponseEntity response = checkTimeValidity(registrationDeadline, scheduleChoicesDeadline, endDate);
        if (null != response) {
            return response;
        }
        Recruitment recruitment = recruitmentService.getRecruitmentById(recruitmentDto.getId());
        recruitment.setName(recruitmentDto.getName());
        recruitment.setRegistrationDeadline(registrationDeadline);
        recruitment.setScheduleChoicesDeadline(scheduleChoicesDeadline);
        recruitment.setEndDate(endDate);
        recruitmentService.updateRecruitment(recruitment);
        deadlineController.setRegisteredDeadline(recruitment.getRegistrationDeadline());
        deadlineController.setEndOfRecruitingDeadLine(recruitment.getEndDate());
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @RequestMapping(value = "/getCurrentRecruitment", method = RequestMethod.GET)
    public Recruitment getCurrentRecruitment() {
        return recruitmentService.getCurrentRecruitmnet();
    }

}
