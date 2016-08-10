package com.netcracker.solutions.kpi.controller.admin;

import com.google.gson.Gson;
import com.netcracker.solutions.kpi.persistence.dto.*;
import com.netcracker.solutions.kpi.persistence.model.*;
import com.netcracker.solutions.kpi.persistence.model.adapter.GsonFactory;
import com.netcracker.solutions.kpi.persistence.model.enums.RoleEnum;
import com.netcracker.solutions.kpi.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.List;

import static com.netcracker.solutions.kpi.persistence.model.enums.StatusEnum.*;

@RestController
@RequestMapping("/admin")
public class AdminManagementStudentController {

    @Autowired
    private ApplicationFormService applicationFormService;
    @Autowired
    private UserService userService;
    @Autowired
    private FormAnswerService formAnswerService;
    @Autowired
    private FormQuestionService formQuestionService;
    @Autowired
    private StatusService statusService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private RecruitmentService recruitmentService;
    @Autowired
    private InterviewService interviewService;
    //@Autowired
    //private ScheduleTimePointService scheduleTimePointService;
    @Autowired
    private DecisionService decisionService;
    @Autowired
    private EmailService emailService;


    @RequestMapping(value = "showAllStudents", method = RequestMethod.GET)
    public List<StudentAppFormDto> showStudents(@RequestParam int pageNum, @RequestParam Long rowsNum, @RequestParam Long sortingCol,
                                                @RequestParam boolean increase) {
        Long fromRow = calculateStartRow(pageNum, rowsNum);
        List<ApplicationForm> applicationForms = applicationFormService.getApplicationFormsSorted(fromRow, rowsNum,
                sortingCol, increase);
        return getAllStudent(applicationForms);
    }

    private List<StudentAppFormDto> getAllStudent(List<ApplicationForm> applicationForms) {
        List<StudentAppFormDto> studentAppFormDtoList = new ArrayList<>();

        for (ApplicationForm applicationForm : applicationForms) {
            Interview interviewSoft = interviewService.getByApplicationFormAndInterviewerRoleId(applicationForm,
                    RoleEnum.ROLE_SOFT.getId());
            Interview interviewTech = interviewService.getByApplicationFormAndInterviewerRoleId(applicationForm,
                    RoleEnum.ROLE_TECH.getId());
            Integer softMark = null;
            Integer techMark = null;
            Integer finalMark = null;
            if (interviewSoft != null) {
                softMark = interviewSoft.getMark();
            }
            if (interviewTech != null) {
                techMark = interviewTech.getMark();
            }
            if (softMark != null && techMark != null) {
                softMark = interviewSoft.getMark();
                techMark = interviewTech.getMark();
                finalMark = decisionService.getByMarks(softMark, techMark).getFinalMark();
            }
            studentAppFormDtoList.add(new StudentAppFormDto(applicationForm.getUser().getId(),
                    applicationForm.getId(), applicationForm.getUser().getFirstName(),
                    applicationForm.getUser().getLastName(), applicationForm.getStatus().getTitle(),
                    softMark,
                    techMark,
                    finalMark,
                    getPossibleStatus(applicationForm.getStatus())));
        }
        return studentAppFormDtoList;
    }

    @RequestMapping(value = "showFilteredStudents", method = RequestMethod.POST)
    public List<StudentAppFormDto> showFilteredStudents(@RequestBody StudentFiltrationParamsDto studentFiltrationParamsDto) {

        Long fromRow = calculateStartRow(studentFiltrationParamsDto.getPageNum(), studentFiltrationParamsDto.getRowsNum());
        List<FormQuestion> questions = new ArrayList<>();

        Gson questionGson = GsonFactory.getFormQuestionGson();
        for (String question : studentFiltrationParamsDto.getRestrictions()) {
            questions.add(questionGson.fromJson(question, FormQuestion.class));
        }

        List<StudentAppFormDto> studentAppFormDtoList = new ArrayList<>();
        List<ApplicationForm> applicationForms = applicationFormService.getCurrentsApplicationFormsFiltered(fromRow,
                studentFiltrationParamsDto.getRowsNum(), studentFiltrationParamsDto.getSortingCol(), studentFiltrationParamsDto.isIncrease(),
                questions, studentFiltrationParamsDto.getStatuses(), studentFiltrationParamsDto.isActive());
        for (ApplicationForm applicationForm : applicationForms) {
            StudentAppFormDto studentAppFormDto = new StudentAppFormDto();

            studentAppFormDto.setId(applicationForm.getUser().getId());
            studentAppFormDto.setAppFormId(applicationForm.getId());
            studentAppFormDto.setFirstName(applicationForm.getUser().getFirstName());
            studentAppFormDto.setLastName(applicationForm.getUser().getLastName());
            studentAppFormDto.setStatus(applicationForm.getStatus().getTitle());
            studentAppFormDto.setPossibleStatus(getPossibleStatus(applicationForm.getStatus()));

            studentAppFormDtoList.add(studentAppFormDto);
        }
        return studentAppFormDtoList;
    }

    private Long calculateStartRow(int pageNum, Long rowsNum) {
        return (pageNum - 1) * rowsNum;
    }

    @RequestMapping(value = "getapplicationquestionsnontext", method = RequestMethod.POST)
    public List<String> getAppFormQuestionsNonText(@RequestParam String role) {
        Role roleTitle = roleService.getRoleByTitle(role);
        List<FormQuestion> formQuestionList = formQuestionService.getByRoleNonText(roleTitle);
        List<String> adapterFormQuestionList = new ArrayList<>();
        for (FormQuestion formQuestion : formQuestionList) {
            Gson questionGson = GsonFactory.getFormQuestionGson();
            String jsonResult = questionGson.toJson(formQuestion);
            adapterFormQuestionList.add(jsonResult);
        }
        return adapterFormQuestionList;
    }


    @RequestMapping(value = "changeSelectedStatuses", method = RequestMethod.POST)
    public ResponseEntity changeSelectedStatuses(@RequestParam String changeStatus,
                                                 @RequestParam List<Long> appFormIdList) {
        Status status = statusService.getByName(changeStatus);
        for (Long id : appFormIdList) {
            ApplicationForm applicationForm = applicationFormService.getApplicationFormById(id);
            applicationForm.setStatus(status);
            applicationFormService.updateApplicationForm(applicationForm);
        }
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    private List<Status> getPossibleStatus(Status status) {
        List<Status> statusList = new ArrayList<>();

        if (status.getTitle().equals(valueOf(REGISTERED))) {
            statusList.add(new Status(valueOf(REGISTERED)));
        } else if (status.getTitle().equals(valueOf(IN_REVIEW))) {
            statusList.add(new Status(valueOf(IN_REVIEW)));
            statusList.add(new Status(valueOf(APPROVED)));
            statusList.add(new Status(valueOf(REJECTED)));
        } else {
            statusList.add(new Status(valueOf(APPROVED)));
            statusList.add(new Status(valueOf(APPROVED_TO_ADVANCED_COURSES)));
            statusList.add(new Status(valueOf(APPROVED_TO_GENERAL_COURSES)));
            statusList.add(new Status(valueOf(APPROVED_TO_JOB)));
            statusList.add(new Status(valueOf(REJECTED)));
        }

        return statusList;
    }

    @RequestMapping(value = "changeStatus", method = RequestMethod.POST)
    public void changeStatuse(@RequestParam String changeStatus, @RequestParam Long appFormId) {
        Status status = statusService.getByName(changeStatus);
        ApplicationForm applicationForm = applicationFormService.getApplicationFormById(appFormId);
        applicationForm.setStatus(status);
        applicationFormService.updateApplicationForm(applicationForm);
    }

    @RequestMapping(value = "getCountOfStudents", method = RequestMethod.GET)
    public Long getCountOfStudents() {
        return userService.getAllStudentCount();
    }

    @RequestMapping(value = "getAllStatuses", method = RequestMethod.GET)
    public List<Status> getAllStatuses() {
        return statusService.getAllStatuses();
    }

    @RequestMapping(value = "getStatus", method = RequestMethod.GET)
    public Status getStatusById(@RequestParam Long id) {
        ApplicationForm af = applicationFormService.getCurrentApplicationFormByUserId(id);
        return af.getStatus();
    }

    @RequestMapping(value = "getQuestionAnswer", method = RequestMethod.GET)
    public ResponseEntity<FormAnswer> getUniverseById(@RequestParam Long userId, @RequestParam Long questionId) {
        ApplicationForm af = applicationFormService.getCurrentApplicationFormByUserId(userId);
        List<FormAnswer> formAnswer = formAnswerService.getByApplicationFormAndQuestion(af, formQuestionService.getById(questionId));
        if (null == formAnswer.get(0))
            return new ResponseEntity<FormAnswer>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<FormAnswer>(formAnswer.get(0), HttpStatus.OK);
    }

    @RequestMapping(value = "getRejectCount", method = RequestMethod.GET)
    public Long getRejectCount() {
        return applicationFormService.getCountRejectedAppForm();
    }

    @RequestMapping(value = "getJobCount", method = RequestMethod.GET)
    public Long getJobCount() {
        return applicationFormService.getCountToWorkAppForm();
    }

    @RequestMapping(value = "getAdvancedCount", method = RequestMethod.GET)
    public Long getAdvancedCount() {
        return applicationFormService.getCountAdvancedAppForm();
    }

    @RequestMapping(value = "getApprovedCount", method = RequestMethod.GET)
    public Long getApprovedCount() {
        return applicationFormService.getCountApprovedAppForm();
    }

    @RequestMapping(value = "getGeneralCount", method = RequestMethod.GET)
    public Long getGeneralCount() {
        return applicationFormService.getCountGeneralAppForm();
    }

    @RequestMapping(value = "calculateStatuses", method = RequestMethod.POST)
    public void calculateStatuses() {
        applicationFormService.calculateStatuses(recruitmentService.getCurrentRecruitmnet());
    }


    @RequestMapping(value = "announceResults", method = RequestMethod.POST)
    public String announceResults() throws MessagingException {
        emailService.sendInterviewResults(recruitmentService.getCurrentRecruitmnet());

        return new Gson().toJson(new MessageDto("Results were announced.", MessageDtoType.SUCCESS));
    }

    /*@RequestMapping(value = "scheduleDatesExist", method = RequestMethod.GET)
    public boolean isScheduleDatesExist() {
        return scheduleTimePointService.isScheduleDatesExists();
    }*/

    //TODO (Olesia)
    /*@RequestMapping(value = "confirmSelection", method = RequestMethod.POST)
    public String confirmSelection() throws MessagingException {
        Gson gson = new Gson();
        if (userTimePriorityService.isSchedulePrioritiesExistStudent()) {
            return gson.toJson(new MessageDto("Selection is already confirmed.",
                    MessageDtoType.ERROR));
        }
        Long appFormInReviewCount = applicationFormService.getCountInReviewAppForm();
        if (appFormInReviewCount != 0) {
            return gson.toJson(new MessageDto("You haven't reviewed all application forms. There are still "
                    + appFormInReviewCount + " unreviewed forms.", MessageDtoType.ERROR));
        }
        if (!scheduleTimePointService.isScheduleDatesExists()) {
            return gson.toJson(new MessageDto("You have to choose dates for schedule before confirming selection.",
                    MessageDtoType.ERROR));
        }
        Recruitment recruitment = recruitmentService.getCurrentRecruitmnet();
        processApprovedStudents(recruitment);
        emailService.sendInterviewInvitation(recruitment);
        return gson.toJson(new MessageDto("Selection confirmed.",
                MessageDtoType.SUCCESS));
    }*/

    private void processApprovedStudents(Recruitment recruitment) throws MessagingException {
        List<ApplicationForm> approvedForms = applicationFormService
                .getByStatusAndRecruitment(APPROVED.getStatus(), recruitment);
        for (ApplicationForm applicationForm : approvedForms) {
            User student = applicationForm.getUser();
            //didnt work old recruitment
            //userTimePriorityService.createStudentTimePriotities(student);
        }
    }

    @RequestMapping(value = "searchStudent", method = RequestMethod.POST)
    public List<StudentAppFormDto> searchStudentById(@RequestParam String lastName,
                                                     @RequestParam int pageNum, @RequestParam Long rowsNum) {
        Long fromRow = calculateStartRow(pageNum, rowsNum);
        List<ApplicationForm> applicationForms = applicationFormService.getSearchAppFormByNameFromToRows(lastName,
                fromRow, rowsNum);
        return getAllStudent(applicationForms);
    }

    //TODO(Olesia)
   /* @RequestMapping(value = "getRecruitmentStatus", method = RequestMethod.GET)
    public String getRecruitmentStatus() {
        Recruitment recruitment = recruitmentService.getCurrentRecruitmnet();
        RecruitmentStatusDto recruitmentStatusDto = new RecruitmentStatusDto();
        if (recruitment != null) {
            recruitmentStatusDto.setRecruitmentExists(true);
            recruitmentStatusDto.setScheduleExists(userTimePriorityService.isSchedulePrioritiesExistStudent());
        }
        return new Gson().toJson(recruitmentStatusDto);
    }*/

    /*@RequestMapping(value = "getTimePoints", method = RequestMethod.GET)
    public List<ScheduleTimePoint> getTimePoints() {
        return scheduleTimePointService.getAll();
    }*/
}
