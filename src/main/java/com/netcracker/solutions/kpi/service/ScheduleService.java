package com.netcracker.solutions.kpi.service;

import com.netcracker.solutions.kpi.persistence.model.Recruitment;
import com.netcracker.solutions.kpi.persistence.model.Role;
import com.netcracker.solutions.kpi.persistence.model.ScheduleTimePoint;
import com.netcracker.solutions.kpi.persistence.model.UserTimePriority;
import com.netcracker.solutions.kpi.persistence.model.impl.real.RoleImpl;
import com.netcracker.solutions.kpi.persistence.model.impl.real.UserImpl;
import com.netcracker.solutions.kpi.util.scheduling.CreatingOfAllSchedules;
import com.netcracker.solutions.kpi.util.scheduling.StudentsScheduleCell;
import com.netcracker.solutions.kpi.util.scheduling.TeachersScheduleCell;
import com.netcracker.solutions.kpi.util.scheduling.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduleService {
    private CreatingOfAllSchedules creatingOfAllSchedules;

    private static final String CAN_TIME_PRIORITY = "Can";

    @Autowired
    private UserService userService;// = ServiceFactory.getUserService();
    @Autowired
    private RecruitmentService recruitmentService;// = ServiceFactory.getRecruitmentService();
    @Autowired
    private ScheduleTimePointService scheduleTimePointService;// = ServiceFactory.getScheduleTimePointService();
    @Autowired
    private UserTimePriorityService userTimePriorityService;// = ServiceFactory.getUserTimePriorityService();

    private static final Role TECH_ROLE = new RoleImpl(2L);
    private static final Role SOFT_ROLE = new RoleImpl(5L);


    private List<User> undistributedStudents = new ArrayList<>();
    private List<Timestamp> datesAndTimesList = new ArrayList<>();
    private int durationOfLongIntervInMinutes;
    private int durationOfShortIntervInMinutes;
    private int totalNumbOfRegisteredTeachersWithLongerInterv;
    private int totalNumbOfRegisteredTeachersWithShorterInterv;
    private List<Integer> numbOfBookedPositionByLongTeacherForEachDay = new ArrayList<>();
    private List<Integer> numbOfBookedPositionByShortTeacherForEachDay = new ArrayList<>();
    private List<User> allLongTeachers = new ArrayList<>();
    private List<User> allShortTeachers = new ArrayList<>();
    private Recruitment recruitment;
    private boolean techLonger;

    @PostConstruct
    public void init() {
        recruitment = recruitmentService.getCurrentRecruitmnet();
        initializeStartParametr();
/*        creatingOfAllSchedules = new CreatingOfAllSchedules(durationOfLongIntervInMinutes, durationOfShortIntervInMinutes,
                totalNumbOfRegisteredTeachersWithLongerInterv, totalNumbOfRegisteredTeachersWithShorterInterv,
                numbOfBookedPositionByLongTeacherForEachDay, numbOfBookedPositionByShortTeacherForEachDay,
                datesAndTimesList, undistributedStudents, allLongTeachers, allShortTeachers);*/
    }

/*    public ScheduleService() {
        initializeStartParametr();
        creatingOfAllSchedules = new CreatingOfAllSchedules(durationOfLongIntervInMinutes, durationOfShortIntervInMinutes,
                totalNumbOfRegisteredTeachersWithLongerInterv, totalNumbOfRegisteredTeachersWithShorterInterv,
                numbOfBookedPositionByLongTeacherForEachDay,numbOfBookedPositionByShortTeacherForEachDay,
                datesAndTimesList,undistributedStudents,allLongTeachers,allShortTeachers);
    }*/

    private void initializeStartParametr() {
        if (recruitment != null) {   //toDo fix recruitment
            initializeInterviewTimeParameter();
            initializeNumOfInterviewerParameter();
            initializeNumbOfBookedPositionByForEachDay();
            initializeDatesAndTimesList();
            initializeUndistributedStudents();
            initializeTeachersList();
        }
    }

    private void initializeTeachersList() {
        List<com.netcracker.solutions.kpi.persistence.model.User> softUsers = userService.getActiveStaffByRole(SOFT_ROLE);
        List<com.netcracker.solutions.kpi.persistence.model.User> techUsers = userService.getActiveStaffByRole(TECH_ROLE);
        if (techLonger) {
            for (com.netcracker.solutions.kpi.persistence.model.User user : softUsers) {
                allShortTeachers.add(adaptUser(user));
            }
            for (com.netcracker.solutions.kpi.persistence.model.User user : techUsers) {
                allLongTeachers.add(adaptUser(user));
            }
        } else {
            for (com.netcracker.solutions.kpi.persistence.model.User user : softUsers) {
                allLongTeachers.add(adaptUser(user));
            }
            for (com.netcracker.solutions.kpi.persistence.model.User user : techUsers) {
                allShortTeachers.add(adaptUser(user));
            }
        }
    }

    private void initializeUndistributedStudents() {
        List<com.netcracker.solutions.kpi.persistence.model.User> users = userService.getAllNotScheduleStudents();
        for (com.netcracker.solutions.kpi.persistence.model.User user : users) {
            undistributedStudents.add(adaptUser(user));
        }
    }

    private void initializeDatesAndTimesList() {
        List<ScheduleTimePoint> scheduleTimePoints = scheduleTimePointService.getAll();
        for (ScheduleTimePoint scheduleTimePoint : scheduleTimePoints) {
            datesAndTimesList.add(scheduleTimePoint.getTimePoint());
        }
    }

    private void initializeNumbOfBookedPositionByForEachDay() {
        if (techLonger) {
            numbOfBookedPositionByLongTeacherForEachDay = userService.getCountUsersOnInterviewDaysForRole(TECH_ROLE);
            numbOfBookedPositionByShortTeacherForEachDay = userService.getCountUsersOnInterviewDaysForRole(SOFT_ROLE);

        } else {
            numbOfBookedPositionByLongTeacherForEachDay = userService.getCountUsersOnInterviewDaysForRole(SOFT_ROLE);
            numbOfBookedPositionByShortTeacherForEachDay = userService.getCountUsersOnInterviewDaysForRole(TECH_ROLE);
        }
    }

    private void initializeNumOfInterviewerParameter() {
        if (techLonger) {
            totalNumbOfRegisteredTeachersWithLongerInterv = recruitment.getNumberTechInterviewers();
            totalNumbOfRegisteredTeachersWithShorterInterv = recruitment.getNumberSoftInterviewers();
        } else {
            totalNumbOfRegisteredTeachersWithLongerInterv = recruitment.getNumberSoftInterviewers();
            totalNumbOfRegisteredTeachersWithShorterInterv = recruitment.getNumberTechInterviewers();
        }
    }

    private void initializeInterviewTimeParameter() {
        int softInterviewTime = recruitment.getTimeInterviewSoft();
        int techInterviewTime = recruitment.getTimeInterviewTech();
        if (softInterviewTime > techInterviewTime) {
            durationOfLongIntervInMinutes = softInterviewTime;
            durationOfShortIntervInMinutes = techInterviewTime;
            techLonger = false;
        } else {
            durationOfLongIntervInMinutes = techInterviewTime;
            durationOfShortIntervInMinutes = softInterviewTime;
            techLonger = true;
        }
    }

    public void startScheduleForStudents() {
        List<StudentsScheduleCell> scheduleCellList = creatingOfAllSchedules.getStudentsSchedule();
        for (StudentsScheduleCell scheduleCell : scheduleCellList) {
            for (User user : scheduleCell.getStudents()) {
                ScheduleTimePoint scheduleTimePoint = scheduleTimePointService
                        .getScheduleTimePointByTimepoint(scheduleCell.getDateAndHour());
                userService.insertFinalTimePoint(reverseAdaptUser(user), scheduleTimePoint);
            }
        }
    }

    public boolean startScheduleForStaff() {
/*        List<TeachersScheduleCell> scheduleCellList = creatingOfAllSchedules.getTeachersSchedule();
        for (TeachersScheduleCell scheduleCell : scheduleCellList) {
            for (User user : scheduleCell.getTeachers()) {
                ScheduleTimePoint scheduleTimePoint = scheduleTimePointService
                        .getScheduleTimePointByTimepoint(scheduleCell.getDate());
                userService.insertFinalTimePoint(reverseAdaptUser(user), scheduleTimePoint);
            }
        }
        return (scheduleCellList == null) ? false : true;*/
        return true;
    }


    private com.netcracker.solutions.kpi.persistence.model.User reverseAdaptUser(User user) {
        return new UserImpl(user.getId());
    }

    private User adaptUser(com.netcracker.solutions.kpi.persistence.model.User user) {
        List<Timestamp> timesAndDates = new ArrayList<>();
        List<UserTimePriority> userTimePriorities = userTimePriorityService.getAllUserTimePriorities(user.getId());
        for (UserTimePriority userTimePriority : userTimePriorities) {
            if (userTimePriority.getTimePriorityType().getPriority().equals(CAN_TIME_PRIORITY)) {
                timesAndDates.add(userTimePriority.getScheduleTimePoint().getTimePoint());
            }
        }
        return new User(user.getId(), timesAndDates);
    }
}
