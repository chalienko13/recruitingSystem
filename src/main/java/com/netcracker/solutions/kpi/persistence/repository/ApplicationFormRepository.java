package com.netcracker.solutions.kpi.persistence.repository;

import com.netcracker.solutions.kpi.persistence.model.ApplicationForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationFormRepository extends JpaRepository<ApplicationForm, Long> {


    @Query(value = "SELECT a.id,  a.id_status, a.is_active,a.\n" +
            "id_recruitment, a.photo_scope, a.id_user, a.date_create\n" +
            ", a.feedback, s.title \n" +
            "FROM application_form a INNER JOIN status s ON s.id = a.id_status \n" +
            "WHERE a.id_user = ?1 AND a.id_recruitment = (SELECT r.id FROM recruitment r WHERE r.end_date > CURRENT_DATE)",
            nativeQuery = true)
    ApplicationForm getCurrentApplicationFormByUserId(Long id);

    @Query(value = "SELECT a.id,  a.id_status, a.is_active,a.\n" +
            "id_recruitment, a.photo_scope, a.id_user, a.date_create\n" +
            ", a.feedback, s.title \n" +
            "FROM application_form a INNER JOIN status s ON s.id = a.id_status \n" +
            "WHERE a.id_user = ?1 AND a.date_create = (SELECT MAX(a_in.date_create)" +
            " from application_form a_in where a_in.id_user = ?1)", nativeQuery = true)
    ApplicationForm getLastApplicationFormByUserId(Long id);
}

