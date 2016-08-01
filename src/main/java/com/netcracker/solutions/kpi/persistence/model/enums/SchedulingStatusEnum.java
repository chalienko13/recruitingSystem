package com.netcracker.solutions.kpi.persistence.model.enums;

import com.netcracker.solutions.kpi.persistence.model.SchedulingStatus;

import java.util.Objects;

/**
 * @author Korzh
 */
public enum SchedulingStatusEnum {

    DATES(1L), TIME_POINTS(2L), STAFF_SCHEDULING(3L), STUDENT_SCHEDULING(4L), NOT_STARTED(5L);
    Long id;

    public static String valueOf(SchedulingStatusEnum schedulingStatusEnum) {
        switch (schedulingStatusEnum) {
            case DATES:
                return "dates";
            case TIME_POINTS:
                return "time_points";
            case STAFF_SCHEDULING:
                return "staff_scheduling";
            case STUDENT_SCHEDULING:
                return "student_scheduling";
            case NOT_STARTED:
                return "not_started";
        }
        throw new IllegalArgumentException("No status defined for");
    }

    public static SchedulingStatus getStatus(Long id) {
        if (Objects.equals(id, DATES.getId())) {
            return new SchedulingStatus(valueOf(DATES), id);
        } else if (Objects.equals(id, TIME_POINTS.getId())) {
            return new SchedulingStatus(valueOf(TIME_POINTS), id);
        } else if (Objects.equals(id, STAFF_SCHEDULING.getId())) {
            return new SchedulingStatus(valueOf(STAFF_SCHEDULING), id);
        } else if (Objects.equals(id, STUDENT_SCHEDULING.getId())) {
            return new SchedulingStatus(valueOf(STUDENT_SCHEDULING), id);
        } else if (Objects.equals(id, NOT_STARTED.getId())) {
            return new SchedulingStatus(valueOf(NOT_STARTED), id);
        } else {
            throw new IllegalStateException("status not found");
        }
    }

    public static SchedulingStatusEnum getStatusEnum(Long id){
        if (Objects.equals(id, DATES.getId())) {
            return SchedulingStatusEnum.DATES;
        } else if (Objects.equals(id, TIME_POINTS.getId())) {
            return SchedulingStatusEnum.TIME_POINTS;
        } else if (Objects.equals(id, STAFF_SCHEDULING.getId())) {
            return SchedulingStatusEnum.STAFF_SCHEDULING;
        } else if (Objects.equals(id, STUDENT_SCHEDULING.getId())) {
            return SchedulingStatusEnum.STUDENT_SCHEDULING;
        } else if (Objects.equals(id, NOT_STARTED.getId())) {
            return SchedulingStatusEnum.NOT_STARTED;
        } else {
            throw new IllegalStateException("status not found");
        }
    }

    SchedulingStatusEnum(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
