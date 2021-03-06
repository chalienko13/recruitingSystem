package com.netcracker.solutions.kpi.service.impl;

import com.netcracker.solutions.kpi.persistence.dao.*;
import com.netcracker.solutions.kpi.persistence.model.*;
import com.netcracker.solutions.kpi.persistence.model.enums.*;
import com.netcracker.solutions.kpi.persistence.repository.*;
import com.netcracker.solutions.kpi.service.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.util.*;
import java.util.Date;

import static com.netcracker.solutions.kpi.persistence.model.enums.StatusEnum.APPROVED;

@Service
@Transactional
public class ApplicationFormServiceImpl implements ApplicationFormService {

    private static Logger log = LoggerFactory.getLogger(ApplicationFormServiceImpl.class);

    @Autowired
    DecisionService decisionService;

    @Autowired
    InterviewService interviewService;

    @Autowired
    private ApplicationFormDao applicationFormDao;

    @Autowired
    private ApplicationFormRepository applicationFormRepository;

    @Autowired
    private FormAnswerDao formAnswerDao;

    @Autowired
    private JpaTransactionManager jpaTransactionManager;

    @Autowired
    private RecruitmentService recruitmentService;

    @Autowired
    private FormQuestionService formQuestionService;

    @Autowired
    private FormAnswerService formAnswerService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Autowired
    private StatusService statusService;

    @Override
    public ApplicationForm getApplicationFormById(Long id) {
        return applicationFormRepository.findOne(id);
    }

    @Override
    public Long getCountRejectedAppForm() {
        return applicationFormDao.getCountRejectedAppForm();
    }

    @Override
    public Long getCountToWorkAppForm() {
        return applicationFormDao.getCountToWorkAppForm();
    }

    @Override
    public Long getCountGeneralAppForm() {
        return applicationFormDao.getCountGeneralAppForm();
    }

    @Override
    public Long getCountAdvancedAppForm() {
        return applicationFormDao.getCountAdvancedAppForm();
    }

    // TODO: 09.08.2016  
    @Override
    public boolean insertApplicationForm(ApplicationForm applicationForm) {
        applicationFormRepository.save(applicationForm);
        return true;
    }

    @Override
    public List<ApplicationForm> getAll() {
        return applicationFormRepository.findAll();
    }

    @Override
    public void updateApplicationForm(ApplicationForm applicationForm) {
        applicationFormRepository.save(applicationForm);
    }

    @Override
    public ApplicationForm getCurrentApplicationFormByUserId(Long id) {
        return applicationFormRepository.getCurrentApplicationFormByUserId(id);
    }

    @Override
    public List<ApplicationForm> getOldApplicationFormsByUserId(Long id) {
        return applicationFormDao.getOldApplicationFormsByUserId(id);
    }

    @Override
    public ApplicationForm getLastApplicationFormByUserId(Long id) {
        return applicationFormRepository.getLastApplicationFormByUserId(id);
    }

    @Override
    public List<ApplicationForm> getByInterviewer(User interviewer) {
        return applicationFormDao.getByInterviewer(interviewer);
    }

    @Override
    public boolean isAssignedForThisRole(ApplicationForm applicationForm, Role role) {
        return applicationFormDao.isAssignedForThisRole(applicationForm, role);
    }

    @Override
    public int changeCurrentsAppFormStatus(Long fromIdStatus, Long toIdStatus) {
        return applicationFormDao.changeCurrentsAppFormStatus(fromIdStatus, toIdStatus);
    }

    @Override
    public Long getCountRecruitmentStudents(Long id) {
        return applicationFormDao.getCountRecruitmentStudents(id);
    }

    @Override
    public Long getCountApprovedStudentsByRecruitmentId(Long id) {
        return applicationFormDao.getApprovedStudentsByRecruitmentId(id);
    }

    @Override
    public List<ApplicationForm> getApplicationFormsSorted(Long fromRow, Long rowsNum, Long sortingCol, boolean increase) {
        return applicationFormDao.getApplicationFormsSorted(fromRow, rowsNum, sortingCol, increase);
    }

    @Override
    public List<ApplicationForm> getCurrentsApplicationFormsFiltered(Long fromRow, Long rowsNum, Long sortingCol,
                                                                     boolean increase, List<FormQuestion> questions,
                                                                     List<String> statuses, boolean isActive) {
        return applicationFormDao.getCurrentApplicationFormsFiltered(fromRow, rowsNum, sortingCol, increase, questions, statuses, isActive);
    }

    public List<ApplicationForm> getSearchAppFormByNameFromToRows(String lastName, Long fromRows, Long rowsNum) {
        return applicationFormDao.getSearchAppFormByNameFromToRows(lastName, fromRows, rowsNum);
    }

    @Override
    public Long getCountInReviewAppForm() {
        return applicationFormDao.getCountInReviewAppForm();
    }

    @Override
    public List<ApplicationForm> getByStatusAndRecruitment(Status status, Recruitment recruitment) {
        return applicationFormDao.getByStatusAndRecruitment(status, recruitment);
    }

    @Override
    public List<ApplicationForm> getByRecruitment(Recruitment recruitment) {
        return applicationFormDao.getByRecruitment(recruitment);
    }

    @Override
    public Long getCountApprovedAppForm() {
        return applicationFormDao.getCountApprovedAppForm();
    }

    // TODO: 09.08.2016
    @Override
    public ApplicationForm getApplicationFormByStudent(User student) {
        return null;
    }

    @Override
    public boolean updateApplicationFormWithAnswers(ApplicationForm applicationForm) {
        try (Connection connection = jpaTransactionManager.getDataSource().getConnection()) {
            connection.setAutoCommit(false);
            applicationFormDao.updateApplicationForm(applicationForm, connection);
            formAnswerDao.deleteNotPresented(applicationForm.getAnswers(), applicationForm, connection);
            for (FormAnswer formAnswer : applicationForm.getAnswers()) {
                if (formAnswer.getId() == null) {
                    formAnswerService.insertFormAnswerForApplicationForm(formAnswer, formAnswer.getFormQuestion(),
                            applicationForm);
                } else {
                    formAnswerDao.updateFormAnswer(formAnswer);
                }
            }
            connection.commit();
        } catch (SQLException e) {
            if (log.isWarnEnabled()) {
                log.error("Cannot update Application form", e);
            }
            return false;
        }
        return true;
    }

    public void calculateStatuses(Recruitment recruitment) {
        List<ApplicationForm> approvedForms = applicationFormDao.getByStatusAndRecruitment(APPROVED.getStatus(), recruitment);

        for (ApplicationForm applicationForm : approvedForms) {
            Interview interviewSoft = interviewService.getByApplicationFormAndInterviewerRoleId(applicationForm,
                    RoleEnum.ROLE_SOFT.getId());
            Interview interviewTech = interviewService.getByApplicationFormAndInterviewerRoleId(applicationForm,
                    RoleEnum.ROLE_TECH.getId());
            if (interviewSoft != null && interviewSoft.getMark() != null && interviewTech != null
                    && interviewTech.getMark() != null) {
                Integer softMark = interviewSoft.getMark();
                Integer techMark = interviewTech.getMark();
                int finalMark = decisionService.getByMarks(softMark, techMark).getFinalMark();
                if (finalMark > 0) {
                    Status finalStatus = decisionService.getStatusByFinalMark(finalMark);
                    applicationForm.setStatus(finalStatus);
                    updateApplicationForm(applicationForm);
                }
            }
        }
    }

    @Transactional
    @Override
    public ApplicationForm getApplicationFormByUserId(Long userId)
    {
        ApplicationForm applicationForm = getCurrentApplicationFormByUserId(userId);
        boolean newForm = false;
        if (applicationForm == null) {
            applicationForm = getLastApplicationFormByUserId(userId);
            if (applicationForm == null || !applicationForm.isActive()) {
                newForm = true;
                applicationForm = createApplicationFormFromOld(applicationForm, userService.getUserByID(userId));
            }
        }
        Recruitment recruitment = recruitmentService.getCurrentRecruitmnet();
        if (!newForm && applicationForm.getRecruitment() == null) {
            addNewFormQuestions(applicationForm);
        }
        if (recruitment == null || (recruitment != null && !isRegistrationDeadlineEnded(recruitment))) {
            applicationForm.setRecruitment(recruitment);
        }
        return applicationForm;
    }

    private ApplicationForm createApplicationFormFromOld(ApplicationForm oldApplicationForm, User user) {
        ApplicationForm applicationForm = createApplicationForm(user);

        List<FormAnswer> formAnswers = new ArrayList<>();

        List<FormQuestion> formQuestions = formQuestionService
                .getEnableByRole(RoleEnum.ROLE_STUDENT.getId());
        for (FormQuestion formQuestion : formQuestions) {
            boolean wasInOldForm = false;
            if (oldApplicationForm != null) {
                List<FormAnswer> oldAnswers = formAnswerService.getByApplicationFormAndQuestion(oldApplicationForm,
                        formQuestion);
                wasInOldForm = formAnswers.addAll(oldAnswers);
            }
            if (!wasInOldForm) {
                FormAnswer formAnswer = createFormAnswer(oldApplicationForm, formQuestion);
                formAnswers.add(formAnswer);
            }
        }
        applicationForm.setAnswers(formAnswers);
        return applicationForm;
    }

    private void addNewFormQuestions(ApplicationForm applicationForm) {
        List<FormQuestion> unconnectedQuestion = formQuestionService.getEnableUnconnectedQuestion(applicationForm);
        for (FormQuestion formQuestion : unconnectedQuestion) {
            FormAnswer formAnswer = createFormAnswer(applicationForm, formQuestion);
            applicationForm.getAnswers().add(formAnswer);
        }
    }

    private boolean isRegistrationDeadlineEnded(Recruitment recruitment) {
        Timestamp deadline = recruitment.getRegistrationDeadline();
        return deadline != null && deadline.before(new Date());
    }

    private FormAnswer createFormAnswer(ApplicationForm applicationForm, FormQuestion question) {
        FormAnswer answer = new FormAnswer();
        answer.setApplicationForm(applicationForm);
        answer.setFormQuestion(question);
        return answer;
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
}
