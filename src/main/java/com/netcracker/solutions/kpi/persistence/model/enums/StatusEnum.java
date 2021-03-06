package com.netcracker.solutions.kpi.persistence.model.enums;

import com.netcracker.solutions.kpi.persistence.model.Status;
import com.netcracker.solutions.kpi.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.EnumSet;

public enum StatusEnum {
    REGISTERED(1L), IN_REVIEW(2L), APPROVED(3L), PENDING_RESULTS(4L), APPROVED_TO_JOB(5L), APPROVED_TO_GENERAL_COURSES(
            6L), APPROVED_TO_ADVANCED_COURSES(7L), REJECTED(8L);
    Long id;

    StatusEnum(Long id) {
        this.id = id;
    }

    public static String valueOf(StatusEnum statusEnum) {
        switch (statusEnum) {
            case REGISTERED:
                return "Registered";
            case IN_REVIEW:
                return "In review";
            case APPROVED:
                return "Approved";
            case PENDING_RESULTS:
                return "Pending result";
            case APPROVED_TO_JOB:
                return "Approved to job";
            case APPROVED_TO_GENERAL_COURSES:
                return "Approved to general group";
            case APPROVED_TO_ADVANCED_COURSES:
                return "Approved to advanced courses";
            case REJECTED:
                return "Rejected";
        }
        throw new IllegalArgumentException("No role defined for");
    }

    public Long getId() {
        return id;
    }

    Status status;

    @Component
    public static class ServiceInjection{
        @Autowired
        public StatusService statusService;

        @PostConstruct
        public void postConstruct() {
            for (StatusEnum statusEnum : EnumSet.allOf(StatusEnum.class))
                statusEnum.status = statusService.getStatusById(statusEnum.getId());
        }
    }

    public Status getStatus() {
        return status;
    }
}
