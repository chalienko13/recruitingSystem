package com.netcracker.solutions.kpi.controller.admin;

import com.netcracker.solutions.kpi.persistence.dto.*;
import com.netcracker.solutions.kpi.persistence.dto.scheduling.InterviewParametersDTO;
import com.netcracker.solutions.kpi.persistence.dto.scheduling.UserIdTimeIdDTO;
import com.netcracker.solutions.kpi.persistence.model.*;
import com.netcracker.solutions.kpi.persistence.model.enums.RoleEnum;
import com.netcracker.solutions.kpi.persistence.model.enums.SchedulingStatusEnum;
import com.netcracker.solutions.kpi.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
public class AdminSchedulingController {

    @Autowired
    SchedulingService schedulingService;

    @Autowired
    RecruitmentService recruitmentService;

    @Autowired
    UserService userService;

    @Autowired
    ScheduleDayPointService scheduleDayPointService;

    //FOR WORK WITH SCHEDULE

    @RequestMapping(value = "/createScheduleDayPoints", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void createScheduleDayPoints (@RequestBody List<ScheduleDayPoint> scheduleDayPoints) {
        schedulingService.createScheduleDayPoints(scheduleDayPoints);
    }

    /***
     * Better use deleteScheduleDayPointsById!
     * @param scheduleDayPoints
     */
    @RequestMapping(value = "/deleteScheduleDayPoints", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void deleteScheduleDayPoints (@RequestBody List<ScheduleDayPoint> scheduleDayPoints) {
        schedulingService.deleteScheduleDayPoints(scheduleDayPoints);
    }

    @RequestMapping(value = "/deleteScheduleDayPointsById", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void deleteScheduleDayPointsById (@RequestBody ArrayList<Short> ids) {
        schedulingService.deleteScheduleDayPointsById(ids);
    }

    //mby make post. think about it
    @RequestMapping(value = "/getAllScheduleDayPoints", method = RequestMethod.GET)
    public List<ScheduleDayPoint> getAllScheduleDayPoints () {
        return schedulingService.findAllScheduleDayPoints();
    }

    @RequestMapping(value = "/createScheduleTimePoints", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void createScheduleTimePoints (@RequestBody List<ScheduleTimePoint> scheduleTimePoints) {
        schedulingService.createScheduleTimePoints(scheduleTimePoints);
    }

    //FOR WORK WITH RECRUITMENT PARAMETERS

    @RequestMapping(value = "/addTimeInterviewTech", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void addTimeInterviewTech (@RequestBody InterviewParametersDTO interviewParameters) {
        schedulingService.addTimeInterviewTechAndSoft(interviewParameters.getTimeInterviewTech(), interviewParameters.getTimeInterviewSoft(), interviewParameters.getRecruitmentId());
    }

    //FOR WORK WITH INTERVIEWERS IN RECRUITMENT

    @RequestMapping(value = "/addTechInterviewerForInterview", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void addTechInterviewerForInterview(@RequestBody UserIdTimeIdDTO userIdTimeIdDTO) {
        System.out.println("lol");
        User interwiewer = userService.getUserByID(userIdTimeIdDTO.getUserId());
        System.out.println(interwiewer);
        ScheduleDayPoint dayPoint = schedulingService.findScheduleDayPoint(userIdTimeIdDTO.getDayPointId());
        System.out.println(dayPoint);
        //TODO Add enum
        TimeType timeType = schedulingService.findTimeTypeByName("interviewer_time");
        System.out.println(timeType);
        UserTime userTime = new UserTime(interwiewer, dayPoint, timeType);
        schedulingService.addTechInterviewerForInterview(userTime);
    }
}
