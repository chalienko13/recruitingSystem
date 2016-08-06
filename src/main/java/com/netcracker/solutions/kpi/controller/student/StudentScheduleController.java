package com.netcracker.solutions.kpi.controller.student;

import com.google.gson.Gson;
import com.netcracker.solutions.kpi.persistence.dto.MessageDto;
import com.netcracker.solutions.kpi.persistence.dto.MessageDtoType;
import com.netcracker.solutions.kpi.persistence.dto.StudentScheduleDto;
import com.netcracker.solutions.kpi.persistence.dto.StudentSchedulePriorityDto;
import com.netcracker.solutions.kpi.persistence.model.*;
import com.netcracker.solutions.kpi.persistence.model.enums.StatusEnum;
import com.netcracker.solutions.kpi.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequestMapping("/student")
@RestController
public class StudentScheduleController {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    private static Logger log = LoggerFactory.getLogger(StudentScheduleController.class);
    private static Gson gson = new Gson();
    private static final String NOT_INVITED_MESSAGE = gson
            .toJson(new MessageDto("You weren't invited to interview.", MessageDtoType.INFO));
    private static final String NO_RECRUITMENT_MESSAGE = gson
            .toJson(new MessageDto("There is no recruitment now.", MessageDtoType.INFO));
    private static final String NO_USER_PRIOTIES_MESSAGE = gson
            .toJson(new MessageDto("You cannot choice schedule now.", MessageDtoType.INFO));
    private static final String PRIOTIES_UPDATED_MESSAGE = gson
            .toJson(new MessageDto("Your priorities were updated.", MessageDtoType.SUCCESS));
    private static final String PRIOTIES_NOT_UPDATED_MESSAGE = gson
            .toJson(new MessageDto("Your priorities weren't updated.", MessageDtoType.ERROR));
    private static final String SCHEDULE_CHOICES_DEADLINE_MESSAGE = gson.toJson(
            new MessageDto("You cannot update priorities after schedule choices deadline.", MessageDtoType.ERROR));
    @Autowired
    private UserService userService;
    @Autowired
    private RecruitmentService recruitmentService;
    @Autowired
    private ScheduleTimePointService scheduleTimePointService;
    @Autowired
    private UserTimePriorityService userTimePriorityService;
    @Autowired
    private TimePriorityTypeService timePriorityTypeService;
    @Autowired
    private ApplicationFormService applicationFormService;

    @RequestMapping(value = "schedule", method = RequestMethod.GET)
    public String getStudentSchedule() {
        User student = userService.getAuthorizedUser();
        Recruitment recruitment = recruitmentService.getCurrentRecruitmnet();
        if (recruitment == null) {
            return NO_RECRUITMENT_MESSAGE;
        }
        if (userTimePriorityService.isSchedulePrioritiesExistStudent()) {
            ApplicationForm applicationForm = applicationFormService.getCurrentApplicationFormByUserId(student.getId());
            if (applicationForm == null) {
                return NOT_INVITED_MESSAGE;
            }
            if (scheduleTimePointService.isScheduleExists()
                    && !applicationForm.getStatus().getId().equals(StatusEnum.REJECTED.getId())) {
                List<ScheduleTimePoint> finalTimePoints = scheduleTimePointService.getFinalTimePointByUserId(student.getId());
                return gson.toJson(DATE_FORMAT.format(finalTimePoints.get(0).getTimePoint()));
            } else {
                if (applicationForm.getStatus().getId().equals(StatusEnum.APPROVED.getId())) {
                    List<TimePriorityType> timeTypes = timePriorityTypeService.getAll();
                    List<UserTimePriority> userTimePriorities = userTimePriorityService.getAllUserTimePriorities(student.getId());
                    return gson.toJson(getStudentScheduleDto(userTimePriorities, timeTypes));
                }
            }
            return NOT_INVITED_MESSAGE;
        } else {
            return NO_USER_PRIOTIES_MESSAGE;
        }
    }

    @RequestMapping(value = "updateSchedule", method = RequestMethod.POST)
    public String updateStudentSchedule(@RequestBody StudentSchedulePriorityDto[] dtoPriorities) {
        Recruitment recruitment = recruitmentService.getCurrentRecruitmnet();
        if (recruitment == null || recruitment.getScheduleChoicesDeadline().before(new Date())) {
            return SCHEDULE_CHOICES_DEADLINE_MESSAGE;
        }
        try {
            User user = userService.getAuthorizedUser();
            for (StudentSchedulePriorityDto priorityDto : dtoPriorities) {
                updatePriority(user, priorityDto);
            }
            log.info("Student {} updated his priorities.", user.getId());
            return PRIOTIES_UPDATED_MESSAGE;
        } catch (ParseException e) {
            log.error("Cannot parse timepoint {}", e);
            return PRIOTIES_NOT_UPDATED_MESSAGE;
        }

    }


    private StudentScheduleDto getStudentScheduleDto(List<UserTimePriority> userTimePriorities,
                                                     List<TimePriorityType> timeTypes) {
        List<String> priorityTypes = new ArrayList<>();
        for (TimePriorityType timePriorityType : timeTypes) {
            priorityTypes.add(timePriorityType.getPriority());
        }
        List<StudentSchedulePriorityDto> schedulePriorityDtos = new ArrayList<>();
        for (UserTimePriority userTimePriority : userTimePriorities) {
            String timepoint = DATE_FORMAT.format(userTimePriority.getScheduleTimePoint().getTimePoint().getTime());
            String priority = userTimePriority.getTimePriorityType().getPriority();
            schedulePriorityDtos.add(new StudentSchedulePriorityDto(timepoint, priority));
        }
        return new StudentScheduleDto(priorityTypes, schedulePriorityDtos);
    }

    private void updatePriority(User student, StudentSchedulePriorityDto priorityDto) throws ParseException {
        Date parsedDate = DATE_FORMAT.parse(priorityDto.getTimePoint());
        Timestamp timestamp = new Timestamp(parsedDate.getTime());
        ScheduleTimePoint timePoint = scheduleTimePointService.getScheduleTimePointByTimepoint(timestamp);
        if (timePoint != null) {
            UserTimePriority userTimePriority = userTimePriorityService.getByUserTime(student, timePoint);
            TimePriorityType priorityType = timePriorityTypeService.getByPriority(priorityDto.getPriority());
            userTimePriority.setTimePriorityType(priorityType);
            userTimePriorityService.updateUserPriority(userTimePriority);
        }
    }

}
