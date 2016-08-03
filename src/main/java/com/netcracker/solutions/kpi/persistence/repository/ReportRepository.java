package com.netcracker.solutions.kpi.persistence.repository;

import com.netcracker.solutions.kpi.report.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
}
