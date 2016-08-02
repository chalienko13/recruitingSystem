package com.netcracker.solutions.kpi.service.impl;

import com.netcracker.solutions.kpi.persistence.dao.FormAnswerVariantDao;
import com.netcracker.solutions.kpi.service.FormAnswerVariantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FormAnswerVariantServiceImpl implements FormAnswerVariantService {

	private static Logger log = LoggerFactory.getLogger(FormAnswerVariantServiceImpl.class.getName());

    @Autowired
	private FormAnswerVariantDao formAnswerVariantDao;

	/*public FormAnswerVariantServiceImpl(FormAnswerVariantDao formAnswerVariantDao, FormQuestionDao formQuestionDao) {
		this.formAnswerVariantDao = formAnswerVariantDao;
	}*/

	@Override
	public List<FormAnswerVariant> getAnswerVariantsByQuestion(FormQuestion question) {
		return formAnswerVariantDao.getByQuestionId(question.getId());
	}

	@Override
	public Long addAnswerVariant(FormAnswerVariant formatVariant) {
		return formAnswerVariantDao.insertFormAnswerVariant(formatVariant);
	}

	@Override
	public boolean changeAnswerVariant(FormAnswerVariant formAnswerVariant) {
		return formAnswerVariantDao.updateFormAnswerVariant(formAnswerVariant) != 0;
	}

	@Override
	public boolean deleteAnswerVariant(FormAnswerVariant formVariant) {
		return formAnswerVariantDao.deleteFormAnswerVariant(formVariant) != 0;
	}

	@Override
	public FormAnswerVariant getAnswerVariantById(Long id) {
		return formAnswerVariantDao.getById(id);
	}

	@Override
	public FormAnswerVariant getAnswerVariantByTitleAndQuestion(String title, FormQuestion question) {
		return formAnswerVariantDao.getAnswerVariantByTitleAndQuestion(title, question);
	}
}
