package com.netcracker.solutions.kpi.persistence.repository;

import com.netcracker.solutions.kpi.persistence.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User getByEmail(String email);

    User getByConfirmToken(String confirmToken);

    @Query("select count(email) > 0 from User u where email = :email")
    Boolean isExistByEmail(@Param("email") String username);

    @Query("SELECT u FROM User u INNER JOIN u.roles r WHERE r.id = :idRole AND u.isActive = TRUE")
    List<User> getActiveStaffByRole(@Param("idRole") Long idRole);

    @Query("SELECT u FROM User u " +
            "JOIN ApplicationForm af " +
                "ON u.id = af.user.id " +
            "JOIN Recruitment r " +
                "ON af.recruitment.id = r.id " +
            "JOIN u.roles ur " +
            "WHERE r.endDate > current_date " +
            "AND af.status = 3 AND ur.id = 3")
    List<User> getAllNotScheduleStudents();
}
