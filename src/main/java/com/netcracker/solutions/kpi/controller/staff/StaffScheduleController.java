package com.netcracker.solutions.kpi.controller.staff;

import com.netcracker.solutions.kpi.persistence.dto.UserTimePriorityDto;
import com.netcracker.solutions.kpi.persistence.model.TimePriorityType;
import com.netcracker.solutions.kpi.persistence.model.UserTimePriority;
import com.netcracker.solutions.kpi.persistence.model.enums.SchedulingStatusEnum;
import com.netcracker.solutions.kpi.persistence.model.impl.proxy.ScheduleTimePointProxy;
import com.netcracker.solutions.kpi.service.RecruitmentService;
import com.netcracker.solutions.kpi.service.ScheduleTimePointService;
import com.netcracker.solutions.kpi.service.UserService;
import com.netcracker.solutions.kpi.service.UserTimePriorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/staff")
public class StaffScheduleController {

    @Autowired
    private UserTimePriorityService userTimePriorityService;// = ServiceFactory.getUserTimePriorityService();
    @Autowired
    private UserService userService;// = ServiceFactory.getUserService();
    @Autowired
    private ScheduleTimePointService scheduleTimePointService;// = ServiceFactory.getScheduleTimePointService();
    @Autowired
    private RecruitmentService recruitmentService;// = ServiceFactory.getRecruitmentService();

    @RequestMapping(value = "getUserTimePriorities", method = RequestMethod.POST)
    public List<UserTimePriorityDto> getUserTimePriorities() {
        Long statusId = recruitmentService.getLastRecruitment().getSchedulingStatus().getId();
        if (SchedulingStatusEnum.DATES.getId().equals(statusId)) {
            return userTimePriorityService.getAllTimePriorityForUserById(getCurrentUser().getId());
        } else {
            return null;
        }
    }

    @RequestMapping(value = "getFinalTimePoints", method = RequestMethod.GET)
    public List<Timestamp> getFinalTimePoint() {
        User user = getCurrentUser();
        List<ScheduleTimePoint> scheduleTimePoints = scheduleTimePointService.getFinalTimePointByUserId(user.getId());
        List<Timestamp> finalTimePointList = scheduleTimePoints.stream().map(ScheduleTimePoint::getTimePoint).collect(Collectors.toList());
        return finalTimePointList;
    }

    @RequestMapping(value = "saveVariants", method = RequestMethod.POST)
    public ResponseEntity saveAppropriateVariant(@RequestBody List<UserTimePriorityDto> userTimePriorityListDto) {
        List<UserTimePriority> userTimePriorities = userTimePriorityService.getAllUserTimePriorities(getCurrentUser().getId());
        List<UserTimePriority> updatingUserTimePriorities = IntStream.range(0, userTimePriorityListDto.size())
                .mapToObj(i -> {
                    UserTimePriority userTimePriority = new UserTimePriority();
                    userTimePriority.setUser(userTimePriorities.get(i).getUser());
                    userTimePriority.setScheduleTimePoint(new ScheduleTimePointProxy(userTimePriorityListDto.get(i).getTimeStampId()));
                    userTimePriority.setTimePriorityType(new TimePriorityType(userTimePriorityListDto.get(i).getIdPriorityType()));
                    return userTimePriority;
                }).collect(Collectors.toList());
        userTimePriorityService.batchUpdateUserPriority(updatingUserTimePriorities);

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        return userService.getUserByUsername(name);
    }

}
