package com.netcracker.solutions.kpi.persistence.repository;

import com.netcracker.solutions.kpi.persistence.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface RoleRepository extends JpaRepository<Role, Long> {

    public Role getByRoleName(String roleName);

    /*@Query("FROM Role r " +
            "JOIN r.users u " +
            "WHERE r.id IN (2,5) " +
            "AND u.id = :uid " +
            "AND r.id NOT IN (SELECT int_role.id  FROM Interview i JOIN i.role int_role WHERE i.id = :formId)")*/
    @Query(value = "SELECT r.* FROM \"role\" r "
            + " INNER JOIN user_role ur ON ur.id_role = r.id WHERE r.id IN (2,5) AND "
            + "  ur.id_user = ?1 AND " + " r.id NOT IN "
            + "(SELECT i.interviewer_role  FROM interview i WHERE i.id_application_form = ?2)", nativeQuery = true)
    public List<Role> getPossibleInterviewsRoles(Long uid, Long formId);
}
