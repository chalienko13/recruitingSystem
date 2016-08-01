package com.netcracker.solutions.kpi.persistence.model.impl.proxy;

import com.netcracker.solutions.kpi.persistence.model.*;
import com.netcracker.solutions.kpi.persistence.model.impl.real.ApplicationFormImpl;
import com.netcracker.solutions.kpi.service.ApplicationFormService;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import java.sql.Timestamp;
import java.util.List;

@Configurable
public class ApplicationFormProxy implements ApplicationForm {

	private static final long serialVersionUID = -6416370223822123449L;

	private Long id;

	private ApplicationFormImpl applicationFormImpl;

    @Autowired
	private ApplicationFormService service;

	public ApplicationFormProxy() {
	}

	public ApplicationFormProxy(Long id) {
		this.id = id;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public Status getStatus() {
		checkApplicationForm();
		return applicationFormImpl.getStatus();
	}

	@Override
	public void setStatus(Status status) {
		checkApplicationForm();
		applicationFormImpl.setStatus(status);
	}

	@Override
	public boolean isActive() {
		checkApplicationForm();
		return applicationFormImpl.isActive();
	}

	@Override
	public void setActive(boolean active) {
		checkApplicationForm();
		applicationFormImpl.setActive(active);
	}

	@Override
	public Recruitment getRecruitment() {
		checkApplicationForm();
		return applicationFormImpl.getRecruitment();
	}

	@Override
	public void setRecruitment(Recruitment recruitment) {
		checkApplicationForm();
		applicationFormImpl.setRecruitment(recruitment);
	}

	@Override
	public String getPhotoScope() {
		checkApplicationForm();
		return applicationFormImpl.getPhotoScope();
	}

	@Override
	public void setPhotoScope(String photoScope) {
		checkApplicationForm();
		applicationFormImpl.setPhotoScope(photoScope);
	}

	@Override
	public User getUser() {
		checkApplicationForm();
		return applicationFormImpl.getUser();
	}

	@Override
	public void setUser(User user) {
		checkApplicationForm();
		applicationFormImpl.setUser(user);
	}

	@Override
	public Timestamp getDateCreate() {
		checkApplicationForm();
		return applicationFormImpl.getDateCreate();
	}

	@Override
	public void setDateCreate(Timestamp dateCreate) {
		checkApplicationForm();
		applicationFormImpl.setDateCreate(dateCreate);
	}

	@Override
	public String getFeedback() {
		checkApplicationForm();
		return applicationFormImpl.getFeedback();
	}

	@Override
	public void setFeedback(String feedback) {
		checkApplicationForm();
		applicationFormImpl.setFeedback(feedback);
	}

	@Override
	public List<Interview> getInterviews() {
		checkApplicationForm();
		return applicationFormImpl.getInterviews();
	}

	@Override
	public void setInterviews(List<Interview> interviews) {
		checkApplicationForm();
		applicationFormImpl.setInterviews(interviews);
	}

	@Override
	public List<FormAnswer> getAnswers() {
		checkApplicationForm();
		return applicationFormImpl.getAnswers();
	}

	@Override
	public void setAnswers(List<FormAnswer> answers) {
		checkApplicationForm();
		applicationFormImpl.setAnswers(answers);
	}

	@Override
	public List<FormQuestion> getQuestions() {
		checkApplicationForm();
		return applicationFormImpl.getQuestions();
	}

	@Override
	public void setQuestions(List<FormQuestion> questions) {
		checkApplicationForm();
		applicationFormImpl.setQuestions(questions);
	}

	private void checkApplicationForm(){
		if (applicationFormImpl == null) {
			applicationFormImpl = downloadApplicationForm(id);
		}
	}

	private ApplicationFormImpl downloadApplicationForm(Long id) {
		return applicationFormImpl = (ApplicationFormImpl) service.getApplicationFormById(id);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (o == null || getClass() != o.getClass()) return false;

		ApplicationFormProxy that = (ApplicationFormProxy) o;

		return new EqualsBuilder()
				.append(id, that.id)
				.append(applicationFormImpl, that.applicationFormImpl)
				.append(service, that.service)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
				.append(id)
				.append(applicationFormImpl)
				.append(service)
				.toHashCode();
	}
}
