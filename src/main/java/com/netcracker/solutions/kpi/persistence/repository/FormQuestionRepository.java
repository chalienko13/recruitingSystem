package com.netcracker.solutions.kpi.persistence.repository;

import com.netcracker.solutions.kpi.persistence.model.FormQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormQuestionRepository extends JpaRepository<FormQuestion, Long> {
}
