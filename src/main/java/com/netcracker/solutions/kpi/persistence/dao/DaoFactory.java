/*
package com.netcracker.solutions.kpi.persistence.dao;


import com.netcracker.solutions.kpi.persistence.dao.impl.*;
import ua.kpi.nc.persistence.dao.impl.*;

*/
/**
 * Created by Chalienko on 20.04.2016.
 *//*

@Deprecated
public class DaoFactory {
    private static ApplicationFormDao applicationFormDao;
    private static DecisionDao decisionDao;
    private static EmailTemplateDao emailTemplateDao;
    private static FormAnswerVariantDao formAnswerVariantDao;
    private static FormAnswerDao formAnswerDao;
    private static FormQuestionDao formQuestionDao;
    private static InterviewDao interviewDao;
    private static NotificationTypeDao notificationTypeDao;
    private static QuestionTypeDao questionTypeDao;
    private static RecruitmentDAO recruitmentDAO;
    private static ReportDao reportDao;
    private static SendMessageDao sendMessageDao;
    private static RoleDao roleDao;
    private static ScheduleTimePointDao scheduleTimePointDao;
    private static SocialInformationDao socialInformationDao;
    private static SocialNetworkDao socialNetworkDao;
    private static StatusDao statusDao;
    private static TimePriorityTypeDao timePriorityTypeDao;
    private static UserDao userDao;
    private static UserTimePriorityDao userTimePriorityDao;
    private static SchedulingSettingsDao schedulingSettingsDao;
    private static DaoUtil daoUtil;

    private DaoFactory() {
    }

    public static RecruitmentDAO getRecruitmentDao() {
        if (recruitmentDAO == null) {
            recruitmentDAO = new RecruitmentDaoImpl(DataSourceSingleton.getInstance());
        }
        return recruitmentDAO;
    }

    public static UserDao getUserDao() {
        if (userDao == null) {
            userDao = new UserDaoImpl(DataSourceSingleton.getInstance());
        }
        return userDao;
    }

    public static ReportDao getReportDao() {
        if (reportDao == null) {
            reportDao = new ReportDaoImpl(DataSourceSingleton.getInstance());
        }
        return reportDao;
    }

    public static EmailTemplateDao getEmailTemplateDao() {
        if (emailTemplateDao == null) {
            emailTemplateDao = new EmailTemplateDaoImpl(DataSourceSingleton.getInstance());
        }
        return emailTemplateDao;
    }

    public static ApplicationFormDao getApplicationFormDao() {
        if (applicationFormDao == null) {
            applicationFormDao = new ApplicationFormDaoImpl(DataSourceSingleton.getInstance());
        }
        return applicationFormDao;
    }

    public static DecisionDao getDecisionDao() {
        if (decisionDao == null) {
            decisionDao = new DecisionDaoImpl(DataSourceSingleton.getInstance());
        }
        return decisionDao;
    }

    public static FormAnswerVariantDao getFormAnswerVariantDao() {
        if (formAnswerVariantDao == null) {
            formAnswerVariantDao = new FormAnswerVariantDaoImpl(DataSourceSingleton.getInstance());
        }
        return formAnswerVariantDao;
    }

    public static InterviewDao getInterviewDao() {
        if (interviewDao == null) {
            interviewDao = new InterviewDaoImpl(DataSourceSingleton.getInstance());
        }
        return interviewDao;
    }

    public static FormQuestionDao getFormQuestionDao() {
        if (formQuestionDao == null) {
            formQuestionDao = new FormQuestionDaoImpl(DataSourceSingleton.getInstance());
        }
        return formQuestionDao;
    }

    public static FormAnswerDao getFormAnswerDao() {
        if (formAnswerDao == null) {
            formAnswerDao = new FormAnswerDaoImpl(DataSourceSingleton.getInstance());
        }
        return formAnswerDao;
    }

    public static StatusDao getStatusDao() {
        if (statusDao == null) {
            statusDao = new StatusDaoImpl(DataSourceSingleton.getInstance());
        }
        return statusDao;
    }

    public static ScheduleTimePointDao getScheduleTimePointDao() {
        if (scheduleTimePointDao == null) {
            scheduleTimePointDao = new ScheduleTimePointDaoImpl(DataSourceSingleton.getInstance());
        }
        return scheduleTimePointDao;
    }

    public static NotificationTypeDao getNotificationTypeDao() {
        if (notificationTypeDao == null) {
            notificationTypeDao = new NotificationTypeDaoImpl(DataSourceSingleton.getInstance());
        }
        return notificationTypeDao;
    }

    public static TimePriorityTypeDao getTimePriorityDao() {
        if (timePriorityTypeDao == null) {
            timePriorityTypeDao = new TimePriorityTypeDaoImpl(DataSourceSingleton.getInstance());
        }
        return timePriorityTypeDao;
    }

    public static RoleDao getRoleDao() {
        if (roleDao == null) {
            roleDao = new RoleDaoImpl(DataSourceSingleton.getInstance());
        }
        return roleDao;
    }

    public static SocialNetworkDao getSocialNetworkDao() {
        if (socialNetworkDao == null) {
            socialNetworkDao = new SocialNetworkDaoImpl(DataSourceSingleton.getInstance());
        }
        return socialNetworkDao;
    }

    public static SocialInformationDao getSocialInformationDao() {
        if (socialInformationDao == null) {
            socialInformationDao = new SocialInformationDaoImpl(DataSourceSingleton.getInstance());
        }
        return socialInformationDao;
    }

    public static UserTimePriorityDao getUserTimePriorityDao() {
        if (userTimePriorityDao == null) {
            userTimePriorityDao = new UserTimePriorityDaoImpl(DataSourceSingleton.getInstance());
        }
        return userTimePriorityDao;
    }

    public static QuestionTypeDao getQuestionTypeDao() {
        if (questionTypeDao == null) {
            questionTypeDao = new QuestionTypeDaoImpl(DataSourceSingleton.getInstance());
        }
        return questionTypeDao;
    }

    public static SendMessageDao getResendMessageDao() {
        if (sendMessageDao == null) {
            sendMessageDao = new SendMessageDaoImpl(DataSourceSingleton.getInstance());
        }
        return sendMessageDao;
    }

    public static SchedulingSettingsDao getSchedulingSettingsDao() {
        if(schedulingSettingsDao == null) {
            schedulingSettingsDao = new SchedulingSettingsDaoImpl(DataSourceSingleton.getInstance());
        }
        return schedulingSettingsDao;
    }

    public static DaoUtil getDaoUtil(){
        if (daoUtil == null){
            daoUtil = new DaoUtilImpl(DataSourceSingleton.getInstance());
        }
        return daoUtil;
    }
}

*/
