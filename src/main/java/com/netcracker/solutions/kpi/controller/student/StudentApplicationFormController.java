package com.netcracker.solutions.kpi.controller.student;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.google.gson.*;
import com.itextpdf.text.DocumentException;
import com.netcracker.solutions.kpi.config.PropertiesReader;
import com.netcracker.solutions.kpi.persistence.dto.*;
import com.netcracker.solutions.kpi.persistence.model.*;
import com.netcracker.solutions.kpi.persistence.model.adapter.*;
import com.netcracker.solutions.kpi.persistence.model.enums.*;
import com.netcracker.solutions.kpi.service.*;
import com.netcracker.solutions.kpi.util.export.*;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import util.form.FormAnswerProcessor;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.Timestamp;
import java.util.*;

@RestController
@RequestMapping("/student")
public class StudentApplicationFormController {
    private static final String[] AVAILABLE_PHOTO_EXTENSIONS = {"jpg", "jpeg", "png"};
    private static Logger log = LoggerFactory.getLogger(StudentApplicationFormController.class);
    private static Gson gson = new Gson();
    private static final String WRONG_INPUT_MESSAGE = gson.toJson(new MessageDto("Wrong input.", MessageDtoType.ERROR));
    private static final String MUST_UPLOAD_PHOTO_MESSAGE = gson
            .toJson(new MessageDto("You must upload photo.", MessageDtoType.ERROR));
    private static final String WAS_UPDATED_MESSAGE = gson
            .toJson(new MessageDto("Your application form was updated.", MessageDtoType.SUCCESS));
    private static final String WAS_CREATED_MESSAGE = gson
            .toJson(new MessageDto("Your application form was created.", MessageDtoType.SUCCESS));
    private static final String CANNOT_UPLOAD_MESSAGE = gson
            .toJson(new MessageDto("Cannot upload photo", MessageDtoType.ERROR));
    private static final String PHOTO_HAS_WRONG_FORMAT_MESSAGE = gson
            .toJson(new MessageDto("Photo has wrong format", MessageDtoType.ERROR));
    private static final String MUST_FILL_IN_MANDATORY_MESSAGE = gson
            .toJson(new MessageDto("You must fill in all mandatory fields.", MessageDtoType.ERROR));
    private static final String REGISTRATION_DEADLINE = gson.toJson(new MessageDto(
            "You cannot update your application form after registration deadline.", MessageDtoType.ERROR));
    @Autowired
    private FormAnswerService formAnswerService;
    @Autowired
    private ApplicationFormService applicationFormService;
    @Autowired
    private UserService userService;
    @Autowired
    private FormQuestionService formQuestionService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private RecruitmentService recruitmentService;

    @Autowired
    private StatusService statusService;

    @Autowired
    private PropertiesReader propertiesReader;

    @Transactional
    @RequestMapping(value = "appform", method = RequestMethod.POST)
    public ApplicationForm getApplicationForm() {
        User student = userService.getAuthorizedUser();
        ApplicationForm applicationForm = applicationFormService.getApplicationFormByUserId(student.getId());
        Long roleId = RoleEnum.ROLE_STUDENT.getId();
        applicationForm.setQuestions(formQuestionService.getEnableByRole(roleId));
        return applicationForm;
    }


    @Transactional
    @RequestMapping(value = "saveApplicationForm", method = RequestMethod.POST)
    public String saveApplicationForm(@RequestParam("applicationForm") String applicationFormJson,
                                      @RequestParam("file") MultipartFile file) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.setVisibilityChecker(VisibilityChecker.Std.defaultInstance().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
        ApplicationForm applicationForm = null;
        System.out.println("JSON: " + applicationFormJson);
        try {
            applicationForm = mapper.readValue(applicationFormJson, ApplicationForm.class);
        } catch (IOException e) {
            log.error("Cannot parse json to object",e);
            return WRONG_INPUT_MESSAGE;
        }
        User user = userService.getAuthorizedUser();
        log.info("Save application form {} for user: {} ", applicationForm, user.getEmail());
//        updateUser(user, applicationForm.getUser());
//        Recruitment currentRecruitment = recruitmentService.getCurrentRecruitmnet();
//        boolean newForm = false;
//        Set<FormQuestion> remainedQuestions;
//        ApplicationForm applicationForm = getApplicationFormForSave(user);
//        log.info("Saving application form of user {}", user.getId());
//        if (applicationForm == null || !applicationForm.isActive()) {
//            newForm = true;
//            if (file.isEmpty()) {
//                return MUST_UPLOAD_PHOTO_MESSAGE;
//            }
//            applicationForm = createApplicationForm(user);
//            remainedQuestions = formQuestionService
//                    .getByEnableRoleAsSet(roleService.getRoleByTitle(RoleEnum.valueOf(RoleEnum.ROLE_STUDENT)));
//        } else {
//            Recruitment recruitment = applicationForm.getRecruitment();
//            if (recruitment != null) {
//                if (currentRecruitment != null && recruitment.getId().equals(currentRecruitment.getId()) && isRegistrationDeadlineEnded(recruitment)) {
//                    return REGISTRATION_DEADLINE;
//                }
//                remainedQuestions = formQuestionService.getByApplicationFormAsSet(applicationForm);
//            } else {
//                if (currentRecruitment != null && !isRegistrationDeadlineEnded(currentRecruitment)) {
//                    applicationForm.setRecruitment(currentRecruitment);
//                }
//                remainedQuestions = formQuestionService
//                        .getByEnableRoleAsSet(roleService.getRoleByTitle(RoleEnum.valueOf(RoleEnum.ROLE_STUDENT)));
//            }
//        }
//        String errorMessage = processAnswers(applicationForm, applicationForm.getQuestions(), remainedQuestions,
//                newForm);
//        if (errorMessage != null) {
//            return errorMessage;
//        }
//        if (newForm) {
//
//            applicationFormService.insertApplicationForm(applicationForm);
//        } else {
//            applicationFormService.updateApplicationFormWithAnswers(applicationForm);
//        }
//        if (!file.isEmpty()) {
            String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
//            if (!hasPhotoValidFormat(fileExtension)) {
//                return PHOTO_HAS_WRONG_FORMAT_MESSAGE;
//            }
//            if (!savePhoto(applicationForm, file, fileExtension)) {
//                return CANNOT_UPLOAD_MESSAGE;
//            }
//        }
//        if (newForm) {
//            return WAS_CREATED_MESSAGE;
//        } else {
//            return WAS_UPDATED_MESSAGE;
//        }
        return WAS_CREATED_MESSAGE;
    }



    private void updateUser(User user, UserDto userDto) {
        user.setLastName(userDto.getLastName());
        user.setFirstName(userDto.getFirstName());
        user.setSecondName(userDto.getSecondName());
        userService.updateUser(user);
    }

    private String processAnswers(ApplicationForm applicationForm, List<FormQuestion> questions,
                                  Set<FormQuestion> remainedQuestions, boolean newForm) {
//        FormAnswerProcessor formAnswerProcessor = new FormAnswerProcessor(applicationForm);
//        List<FormAnswer> finalAnswers = new ArrayList<>();
//        for (FormQuestion questionDto : questions) {
//            FormQuestion formQuestion = formQuestionService.getById(questionDto.getId());
//            formAnswerProcessor.setFormQuestion(formQuestion);
//            if (!isFormQuestionValid(formQuestion)) {
//                return WRONG_INPUT_MESSAGE;
//            }
//            if (formQuestion.isMandatory() && !isFilled(questionDto)) {
//                return MUST_FILL_IN_MANDATORY_MESSAGE;
//            }
//            if (!remainedQuestions.remove(formQuestion)) {
//                return WRONG_INPUT_MESSAGE;
//            }
//            System.out.println("APLICATION FORM QUESTION " + applicationForm.getQuestions());
////            if (!newForm && containsQuestionWithId(applicationForm.getQuestions(), formQuestion.getId())) {
////                List<FormAnswer> answers = formAnswerService.getByApplicationFormAndQuestion(applicationForm,
////                        formQuestion);
////                formAnswerProcessor.updateAnswers(questionDto.g(), answers);
////            } else {
////                formAnswerProcessor.insertNewAnswers(questionDto.getAnswers());
////            }
//        }
//        if (!remainedQuestions.isEmpty()) {
//            return WRONG_INPUT_MESSAGE;
//        }
//        finalAnswers.addAll(formAnswerProcessor.getAnswers());
//        applicationForm.setAnswers(finalAnswers);
        return null;
    }

    private boolean savePhoto(ApplicationForm applicationForm, MultipartFile file, String fileExtension) {
        try {
            String photoScope = applicationForm.getId() + "." + fileExtension;
            String photoDirPath = propertiesReader.propertiesReader("photodir.path");
            File photoFile = new File(photoDirPath, photoScope);
            log.info("Saving photo to {}", photoFile.getAbsolutePath());
            file.transferTo(photoFile);
            applicationForm.setPhotoScope(photoScope);
            applicationFormService.updateApplicationForm(applicationForm);
            return true;
        } catch (IOException e) {
            log.error("Cannot save photo", e);
            return false;
        }
    }

    @RequestMapping(value = "appform/{applicationFormId}", method = RequestMethod.GET)
    public void exportAppform(@PathVariable Long applicationFormId, HttpServletResponse response)
            throws IOException, DocumentException {
        ApplicationForm applicationForm = applicationFormService.getApplicationFormById(applicationFormId);
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=ApplicationForm.pdf");
        ExportApplicationForm pdfAppForm = new ExportApplicationFormImp();
        pdfAppForm.export(applicationForm, response);

    }

    private ApplicationForm createApplicationForm(User user) {
        log.info("Create Application Form for User {}",user);
        ApplicationForm applicationForm = new ApplicationForm();
        applicationForm.setUser(user);
        Recruitment recruitment = recruitmentService.getCurrentRecruitmnet();
        applicationForm.setStatus(statusService.getStatusById(StatusEnum.REGISTERED.getId()));
        applicationForm.setActive(true);
        applicationForm.setDateCreate(new Timestamp(System.currentTimeMillis()));
        applicationForm.setRecruitment(recruitment);
        return applicationForm;
    }

    private boolean isFormQuestionValid(FormQuestion formQuestion) {
        return !(formQuestion == null || !formQuestion.isEnable());
    }

    private boolean isFilled(StudentAppFormQuestionDto questionDto) {
        List<StudentAnswerDto> answersDto = questionDto.getAnswers();
        if (answersDto == null) {
            return false;
        }
        for (StudentAnswerDto answer : answersDto) {
            if (answer != null && answer.getAnswer() != null && !answer.getAnswer().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private boolean hasPhotoValidFormat(String fileExtension) {
        for (String photoExtension : AVAILABLE_PHOTO_EXTENSIONS) {
            if (photoExtension.equals(fileExtension)) {
                return true;
            }
        }
        return false;
    }

    private boolean containsQuestionWithId(List<FormQuestion> questions, Long id) {
        if (questions == null){
            return false;
        }
        for (FormQuestion formQuestion : questions) {
            if (formQuestion.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    private boolean isRegistrationDeadlineEnded(Recruitment recruitment) {
        Timestamp deadline = recruitment.getRegistrationDeadline();
        return deadline != null && deadline.before(new Date());
    }

    private ApplicationForm getApplicationFormForSave(User student) {
        ApplicationForm applicationForm = applicationFormService.getCurrentApplicationFormByUserId(student.getId());
        if (applicationForm == null) {
            applicationForm = applicationFormService.getLastApplicationFormByUserId(student.getId());
        }
        return applicationForm;
    }

}