package com.netcracker.solutions.kpi.persistence.repository;


import com.netcracker.solutions.kpi.persistence.model.Interview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterviewRepository extends JpaRepository<Interview, Long> {
}
