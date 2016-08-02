package util.form;

import java.util.ArrayList;
import java.util.List;

import com.netcracker.solutions.kpi.persistence.dto.StudentAnswerDto;
import com.netcracker.solutions.kpi.persistence.model.*;
import com.netcracker.solutions.kpi.persistence.model.enums.FormQuestionTypeEnum;
import com.netcracker.solutions.kpi.service.FormAnswerVariantService;
import org.springframework.beans.factory.annotation.Autowired;

public class FormAnswerProcessor {

    @Autowired
    private FormAnswerVariantService formAnswerVariantService;// = ServiceFactory.getFormAnswerVariantService();

	private ApplicationForm applicationForm;
	private Interview interview;
	private FormQuestion formQuestion;
	private List<FormAnswer> answers = new ArrayList<>();

	public FormAnswerProcessor(ApplicationForm applicationForm) {
		this.applicationForm = applicationForm;
	}

	public FormAnswerProcessor(Interview interview) {
		this.interview = interview;
	}

	public FormQuestion getFormQuestion() {
		return formQuestion;
	}

	public void setFormQuestion(FormQuestion formQuestion) {
		this.formQuestion = formQuestion;
	}

	public ApplicationForm getApplicationForm() {
		return applicationForm;
	}

	public Interview getInterview() {
		return interview;
	}

	public List<FormAnswer> getAnswers() {
		return answers;
	}

	@Override
	public String toString() {
		return "FormAnswerProcessor [applicationForm=" + applicationForm + ", interview=" + interview
				+ ", formQuestion=" + formQuestion + "]";
	}

	private FormAnswer createFormAnswer() {
		FormAnswer answer = new FormAnswer();
		answer.setApplicationForm(applicationForm);
		answer.setInterview(interview);
		answer.setFormQuestion(formQuestion);
		return answer;
	}

	public void insertNewAnswers(List<StudentAnswerDto> answersDto) {
		String questionType = formQuestion.getQuestionType().getTypeTitle();
		if (FormQuestionTypeEnum.CHECKBOX.getTitle().equals(questionType)) {
			for (StudentAnswerDto answerDto : answersDto) {
				FormAnswerVariant variant = formAnswerVariantService
						.getAnswerVariantByTitleAndQuestion(answerDto.getAnswer(), formQuestion);
				if (variant != null) {
					FormAnswer formAnswer = createFormAnswer();
					formAnswer.setFormAnswerVariant(variant);
					answers.add(formAnswer);
				}
			}
			if (answers.isEmpty()) {
				answers.add(createFormAnswer());
			}
		} else {
			FormAnswer formAnswer = createFormAnswer();
			processSingleAnswer(formAnswer, answersDto, questionType);
		}
	}

	public void updateAnswers(List<StudentAnswerDto> answersDto, List<FormAnswer> existingAnswers) {
		String questionType = formQuestion.getQuestionType().getTypeTitle();
		if (FormQuestionTypeEnum.CHECKBOX.getTitle().equals(questionType)) {
			int i;
			for (i = 0; i < answersDto.size() && i < existingAnswers.size(); i++) {
				StudentAnswerDto answerDto = answersDto.get(i);
				FormAnswer answer = existingAnswers.get(i);
				FormAnswerVariant variant = formAnswerVariantService
						.getAnswerVariantByTitleAndQuestion(answerDto.getAnswer(), formQuestion);
				answer.setFormAnswerVariant(variant);
				answers.add(answer);
			}
			for (; i < answersDto.size(); i++) {
				StudentAnswerDto answerDto = answersDto.get(i);
				FormAnswer formAnswer = createFormAnswer();
				FormAnswerVariant variant = formAnswerVariantService
						.getAnswerVariantByTitleAndQuestion(answerDto.getAnswer(), formQuestion);
				formAnswer.setFormAnswerVariant(variant);
				answers.add(formAnswer);
			}
		} else {
			FormAnswer formAnswer = existingAnswers.get(0);
			processSingleAnswer(formAnswer, answersDto, questionType);
		}
	}

	private void processSingleAnswer(FormAnswer formAnswer, List<StudentAnswerDto> answersDto, String questionType) {
		if (FormQuestionTypeEnum.RADIO.getTitle().equals(questionType)
				|| FormQuestionTypeEnum.SELECT.getTitle().equals(questionType)) {

			StudentAnswerDto answerDto = answersDto.get(0);
			FormAnswerVariant variant = formAnswerVariantService
					.getAnswerVariantByTitleAndQuestion(answerDto.getAnswer(), formQuestion);
			formAnswer.setFormAnswerVariant(variant);
		} else {
			formAnswer.setAnswer(answersDto.get(0).getAnswer());
		}
		answers.add(formAnswer);
	}

}
