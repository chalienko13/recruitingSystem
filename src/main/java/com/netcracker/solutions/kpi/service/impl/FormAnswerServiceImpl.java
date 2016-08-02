package com.netcracker.solutions.kpi.service.impl;

import com.netcracker.solutions.kpi.persistence.dao.FormAnswerDao;
import com.netcracker.solutions.kpi.service.FormAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FormAnswerServiceImpl implements FormAnswerService {

    @Autowired
    private FormAnswerDao formAnswerDao;

    /*public FormAnswerServiceImpl(FormAnswerDao formAnswerDao) {
        this.formAnswerDao = formAnswerDao;
    }*/

    @Override
    public FormAnswer getFormAnswerByID(Long id) {
        return formAnswerDao.getById(id);
    }

    @Override
    public int deleteFormAnswer(FormAnswer formAnswer) {
        return formAnswerDao.deleteFormAnswer(formAnswer);
    }

    @Override
    public List<FormAnswer> getByInterviewAndQuestion(Interview interview, FormQuestion question) {
        return formAnswerDao.getByInterviewAndQuestion(interview,question);
    }

    @Override
    public Long insertBlankFormAnswerForApplicationForm(FormAnswer formAnswer) {
        return formAnswerDao.insertBlankFormAnswerForApplicationForm(formAnswer);
    }

    @Override
    public int updateFormAnswer(FormAnswer formAnswer) {
        return formAnswerDao.updateFormAnswer(formAnswer);
    }

    @Override
    public Long insertFormAnswerForApplicationForm(FormAnswer formAnswer) {
        return formAnswerDao.insertFormAnswerForApplicationForm(formAnswer);
    }

	@Override
	public List<FormAnswer> getByApplicationFormAndQuestion(ApplicationForm applicationForm, FormQuestion question) {
		return formAnswerDao.getByApplicationFormAndQuestion(applicationForm, question);
	}

	@Override
	public Long insertFormAnswerForInterview(FormAnswer formAnswer) {
		return formAnswerDao.insertFormAnswerForInterview(formAnswer);
	}
	
	
}
