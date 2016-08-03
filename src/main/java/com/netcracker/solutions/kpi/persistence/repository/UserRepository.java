package com.netcracker.solutions.kpi.persistence.repository;

import com.netcracker.solutions.kpi.persistence.model.Role;
import com.netcracker.solutions.kpi.persistence.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by dmch0716 on 03.08.2016.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User getByEmail(String email);

//    @Query("select exists(SELECT email from User u where u.email = :email)")
//    Boolean isExistByEmail(@Param("email") String username);

    @Query("SELECT u FROM User u INNER JOIN u.roles r WHERE r.id = :idRole AND u.isActive = TRUE")
    List<User> getActiveStaffByRole(@Param("idRole") Long idRole);
}
