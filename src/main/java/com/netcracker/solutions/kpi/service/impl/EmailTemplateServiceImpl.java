package com.netcracker.solutions.kpi.service.impl;

import com.netcracker.solutions.kpi.persistence.dao.EmailTemplateDao;
import com.netcracker.solutions.kpi.persistence.model.EmailTemplate;
import com.netcracker.solutions.kpi.persistence.model.NotificationType;
import com.netcracker.solutions.kpi.service.EmailTemplateService;
import com.netcracker.solutions.kpi.service.ScheduleTimePointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmailTemplateServiceImpl implements EmailTemplateService {

    @Autowired
	private EmailTemplateDao emailTemplateDao;

    @Autowired
	private ScheduleTimePointService scheduleTimePointService; //= ServiceFactory.getScheduleTimePointService();

	/*public EmailTemplateServiceImpl(EmailTemplateDao emailTemplateDao) {
		this.emailTemplateDao = emailTemplateDao;
	}*/

	@Override
	public EmailTemplate getById(Long id) {
		return emailTemplateDao.getById(id);
	}

	@Override
	public EmailTemplate getByTitle(String title) {
		return emailTemplateDao.getByTitle(title);
	}

	@Override
	public EmailTemplate getByNotificationType(NotificationType notificationType) {
		return emailTemplateDao.getByNotificationType(notificationType);
	}

	@Override
	public int updateEmailTemplate(EmailTemplate emailTemplate) {
		return emailTemplateDao.updateEmailTemplate(emailTemplate);
	}

	@Override
	public int deleteEmailTemplate(EmailTemplate emailTemplate) {
		return emailTemplateDao.deleteEmailTemplate(emailTemplate);
	}

	@Override
	public String showTemplateParams(String inputText, User user) {
		List<ScheduleTimePoint> finalTimePoints = scheduleTimePointService.getFinalTimePointByUserId(user.getId());
		StringBuilder finalTimePoint = new StringBuilder();
		for (ScheduleTimePoint s: finalTimePoints) {
			finalTimePoint.append(s);
		}
		Map<String, String> varMap = new HashMap<>();
		varMap.put("firstName", user.getFirstName());
		varMap.put("secondName", user.getSecondName());
		varMap.put("lastName", user.getLastName());
		varMap.put("email", user.getEmail());
		varMap.put("id", String.valueOf(user.getId()));
		varMap.put("password", user.getPassword());
		varMap.put("userInterviewTime", String.valueOf(finalTimePoint));
		for (Map.Entry<String, String> entry : varMap.entrySet()) {
			inputText = inputText.replace('%' + entry.getKey() + '%', entry.getValue());
		}
		return inputText;
	}
}
