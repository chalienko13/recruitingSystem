package com.netcracker.solutions.kpi.persistence.model.impl.proxy;

import com.netcracker.solutions.kpi.persistence.model.*;
import com.netcracker.solutions.kpi.persistence.model.impl.real.InterviewImpl;
import com.netcracker.solutions.kpi.service.InterviewService;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import java.sql.Timestamp;
import java.util.List;

@Configurable
public class InterviewProxy implements Interview {

	private static final long serialVersionUID = 5406265452413495228L;

	private Long id;

	private InterviewImpl interview;

    @Autowired
	private InterviewService interviewService;

	public InterviewProxy() {
	}

	public InterviewProxy(Long id) {
		super();
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getMark() {
		checkInterview();
		return interview.getMark();
	}

	public void setMark(Integer mark) {
		checkInterview();
		interview.setMark(mark);
	}

	public Timestamp getDate() {
		checkInterview();
		return interview.getDate();
	}

	public void setDate(Timestamp date) {
		checkInterview();
		interview.setDate(date);
	}

	public User getInterviewer() {
		checkInterview();
		return interview.getInterviewer();
	}

	public void setInterviewer(User user) {
		checkInterview();
		interview.setInterviewer(user);
	}

	public Role getRole() {
		checkInterview();
		return interview.getRole();
	}

	public void setRole(Role role) {
		checkInterview();
		interview.setRole(role);
	}

	public Boolean isAdequateMark() {
		checkInterview();
		return interview.isAdequateMark();
	}

	public void setAdequateMark(Boolean adequateMark) {
		checkInterview();
		interview.setAdequateMark(adequateMark);
	}

	public ApplicationForm getApplicationForm() {
		checkInterview();
		return interview.getApplicationForm();
	}

	public void setApplicationForm(ApplicationForm applicationForm) {
		checkInterview();
		interview.setApplicationForm(applicationForm);
	}

	@Override
	public List<FormAnswer> getAnswers() {
		checkInterview();
		return interview.getAnswers();
	}

	@Override
	public void setAnswers(List<FormAnswer> answers) {
		checkInterview();
		interview.setAnswers(answers);
	}

	private void checkInterview(){
		if (interview == null) {
			interview = downloadInterview();
		}
	}

	private InterviewImpl downloadInterview() {
		return (InterviewImpl) interviewService.getById(id);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (o == null || getClass() != o.getClass()) return false;

		InterviewProxy that = (InterviewProxy) o;

		return new EqualsBuilder()
				.append(id, that.id)
				.append(interview, that.interview)
				.append(interviewService, that.interviewService)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
				.append(id)
				.append(interview)
				.append(interviewService)
				.toHashCode();
	}
}
