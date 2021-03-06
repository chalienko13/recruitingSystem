package com.netcracker.solutions.kpi.persistence.repository;


import com.netcracker.solutions.kpi.persistence.model.EmailTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailTemplateRepository extends JpaRepository<EmailTemplate, Long> {
}
