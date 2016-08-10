package com.netcracker.solutions.kpi.persistence.model.enums;

import com.netcracker.solutions.kpi.persistence.model.Role;

public enum RoleEnum {
    ROLE_ADMIN(1L), ROLE_STUDENT(3L), ROLE_SOFT(5L), ROLE_TECH(2L);

    private Long id;

    RoleEnum(Long id) {
        this.id = id;
    }

//    public static RoleEnum valueOf(final UserAuthority authority) {
//        switch (authority.getAuthority()) {
//            case "ROLE_ADMIN":
//                return ROLE_ADMIN;
//            case "ROLE_STUDENT":
//                return ROLE_STUDENT;
//        }
//        throw new IllegalArgumentException("No role defined for user: " + authority.getAuthority());
//    }

    public static String valueOf(RoleEnum role) {
        switch (role) {
            case ROLE_ADMIN:
                return "ROLE_ADMIN";
            case ROLE_STUDENT:
                return "ROLE_STUDENT";
        }
        throw new IllegalArgumentException("No role defined for");
    }

    public static Role getRole(RoleEnum role) {
        switch (role) {
            case ROLE_STUDENT:
                return new Role(
                        role.getId(),
                        valueOf(role)
                );
            default:
                throw new IllegalStateException("Role not found");
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
