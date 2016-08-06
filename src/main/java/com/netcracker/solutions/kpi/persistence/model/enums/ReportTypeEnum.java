package com.netcracker.solutions.kpi.persistence.model.enums;

public enum ReportTypeEnum {
    APPROVED(1L), ANSWERS(2L);

    private Long id;

    ReportTypeEnum(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
