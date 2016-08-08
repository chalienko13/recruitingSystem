package com.netcracker.solutions.kpi.controller.admin;

import com.netcracker.solutions.kpi.persistence.dto.*;
import com.netcracker.solutions.kpi.persistence.model.*;
import com.netcracker.solutions.kpi.persistence.model.enums.RoleEnum;
import com.netcracker.solutions.kpi.persistence.model.enums.SchedulingStatusEnum;
import com.netcracker.solutions.kpi.service.*;
import com.netcracker.solutions.kpi.service.util.SenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
public class AdminSchedulingController {

    @Autowired
    SchedulingService schedulingService;

    //FOR WORK WITH SCHEDULE

    @RequestMapping(value = "/createScheduleDayPoints", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void createScheduleDayPoints (@RequestBody List<ScheduleDayPoint> scheduleDayPoints) {
        schedulingService.createScheduleDayPoints(scheduleDayPoints);
    }

    @RequestMapping(value = "/deleteScheduleDayPoints", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void deleteScheduleDayPoints (@RequestBody List<ScheduleDayPoint> scheduleDayPoints) {
        schedulingService.deleteScheduleDayPoints(scheduleDayPoints);
    }

    @RequestMapping(value = "/deleteScheduleDayPointsById", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void deleteScheduleDayPointsById (@RequestBody Short id) {
        schedulingService.deleteScheduleDayPointsById(id);
    }

    //mby make post. think about it
    @RequestMapping(value = "/getAllScheduleDayPoints", method = RequestMethod.GET)
    public List<ScheduleDayPoint> getAllScheduleDayPoints () {
        return schedulingService.findAllScheduleDayPoints();
    }

}
