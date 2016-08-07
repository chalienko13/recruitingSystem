package com.netcracker.solutions.kpi.service.impl;

import com.netcracker.solutions.kpi.persistence.dao.DataSourceSingleton;
import com.netcracker.solutions.kpi.persistence.dao.FormAnswerVariantDao;
import com.netcracker.solutions.kpi.persistence.dao.FormQuestionDao;
import com.netcracker.solutions.kpi.persistence.model.ApplicationForm;
import com.netcracker.solutions.kpi.persistence.model.FormAnswerVariant;
import com.netcracker.solutions.kpi.persistence.model.FormQuestion;
import com.netcracker.solutions.kpi.persistence.model.Role;
import com.netcracker.solutions.kpi.persistence.model.enums.FormQuestionTypeEnum;
import com.netcracker.solutions.kpi.persistence.model.enums.RoleEnum;
import com.netcracker.solutions.kpi.service.FormQuestionService;
import com.netcracker.solutions.kpi.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class FormQuestionServiceImpl implements FormQuestionService {
    private static Logger log = LoggerFactory.getLogger(FormQuestionServiceImpl.class.getName());
    @Autowired
    private DataSource dataSource;

    @Autowired
    private RoleService roleService;

    @Autowired
    private FormQuestionDao formQuestionDao;

    @Autowired
    private FormAnswerVariantDao formAnswerVariantDao;

    @Override
    public FormQuestion getById(Long id) {
        return formQuestionDao.getById(id);
    }

    @Override
    public boolean insertFormQuestion(FormQuestion formQuestion, Role role,
                                      List<FormAnswerVariant> formAnswerVariants) {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            Long generatedFormQuestionId = formQuestionDao.insertFormQuestion(formQuestion, connection);
            formQuestion.setId(generatedFormQuestionId);
            formQuestionDao.addRole(formQuestion, role, connection);
            for (FormAnswerVariant formAnswerVariant : formAnswerVariants) {
                formAnswerVariant.setFormQuestion(formQuestion);
                formAnswerVariantDao.insertFormAnswerVariant(formAnswerVariant, connection);
            }
            connection.commit();
        } catch (SQLException e) {
            if (log.isWarnEnabled()) {
                log.warn("Transaction failed When Trying to add Form Question with Variants and Role");
            }
            return false;
        }
        return true;
    }

    @Override
    public int deleteFormQuestion(FormQuestion formQuestion) {
        return formQuestionDao.deleteFormQuestion(formQuestion);
    }

    @Override
    public List<FormQuestion> getByRole(Role role) {
        return formQuestionDao.getByRole(role);
    }

    @Override
    public List<FormQuestion> getByRoleNonText(Role role) {
        return formQuestionDao.getByRoleNonText(role);
    }

    @Override
    public List<FormQuestion> getAll() {
        return formQuestionDao.getAll();
    }

    @Override
    public int updateFormQuestion(FormQuestion formQuestion) {
        return formQuestionDao.updateFormQuestion(formQuestion);
    }

    @Override
    public boolean updateQuestions(FormQuestion formQuestion, List<FormAnswerVariant> formAnswerVariants) {
        try (Connection connection = DataSourceSingleton.getInstance().getConnection()) {
            connection.setAutoCommit(false);
            formQuestionDao.updateFormQuestion(formQuestion, connection);

            for (FormAnswerVariant formAnswerVariantFromDb : formAnswerVariantDao.getByQuestionId(formQuestion.getId())) {
                formAnswerVariantDao.deleteFormAnswerVariant(formAnswerVariantFromDb, connection);
            }
            if (formQuestion.getQuestionType().getTypeTitle().equals(FormQuestionTypeEnum.CHECKBOX.getTitle()) ||
                    formQuestion.getQuestionType().getTypeTitle().equals(FormQuestionTypeEnum.SELECT.getTitle()) ||
                    formQuestion.getQuestionType().getTypeTitle().equals(FormQuestionTypeEnum.RADIO.getTitle())) {
                for (FormAnswerVariant formAnswerVariant : formAnswerVariants) {
                    formAnswerVariant.setFormQuestion(formQuestion);
                    formAnswerVariantDao.insertFormAnswerVariant(formAnswerVariant, connection);
                }
            }
            connection.commit();
        } catch (SQLException e) {
            if (log.isWarnEnabled()) {
                log.warn("Transaction failed When Trying to update Form Question with Variants and Role");
            }
            return false;
        }
        return true;
    }

    @Override
    public Set<FormQuestion> getByEnableRoleAsSet(Role role) {
        return formQuestionDao.getEnableByRoleAsSet(role);
    }

    @Override
    public List<FormQuestion> getEnableByRole(Role role) {
        return formQuestionDao.getEnableByRole(role);
    }

    @Override
    public Set<FormQuestion> getByApplicationFormAsSet(ApplicationForm applicationForm) {
        return formQuestionDao.getByApplicationFormAsSet(applicationForm);
    }

    @Override
    public List<FormQuestion> getEnableUnconnectedQuestion(ApplicationForm applicationForm) {
        Role role = roleService.getRoleByTitle(RoleEnum.valueOf(RoleEnum.ROLE_STUDENT));
        return formQuestionDao.getEnableUnconnectedQuestion(role, applicationForm);
    }

    @Override
    public List<FormQuestion> getWithVariantsByRole(Role role) {
        return formQuestionDao.getWithVariantsByRole(role);
    }
}
