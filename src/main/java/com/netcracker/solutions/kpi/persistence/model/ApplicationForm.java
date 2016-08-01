package com.netcracker.solutions.kpi.persistence.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Алексей on 21.04.2016.
 */
public interface ApplicationForm extends Serializable {

	Long getId();

	void setId(Long id);

	Status getStatus();

	void setStatus(Status status);

	boolean isActive();

	void setActive(boolean active);

	Recruitment getRecruitment();

	void setRecruitment(Recruitment recruitment);

	String getPhotoScope();

	void setPhotoScope(String photoScope);

	User getUser();

	void setUser(User user);

	Timestamp getDateCreate();

	void setDateCreate(Timestamp dateCreate);

	String getFeedback();

	void setFeedback(String feedback);

	List<Interview> getInterviews();

	void setInterviews(List<Interview> interviews);

	List<FormAnswer> getAnswers();

	void setAnswers(List<FormAnswer> answers);

	List<FormQuestion> getQuestions();

	void setQuestions(List<FormQuestion> questions);
}
