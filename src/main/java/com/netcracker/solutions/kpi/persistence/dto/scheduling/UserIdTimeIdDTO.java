package com.netcracker.solutions.kpi.persistence.dto.scheduling;

/**
 * Created by olil0716 on 8/10/2016.
 */
public class UserIdTimeIdDTO {
    private Long userId;
    private Short dayPointId;

    public UserIdTimeIdDTO() {
    }

    public UserIdTimeIdDTO(Long userId, Short dayPointId) {
        this.userId = userId;
        this.dayPointId = dayPointId;
    }

    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Short getDayPointId() {
        return dayPointId;
    }
    public void setDayPointId(Short dayPointId) {
        this.dayPointId = dayPointId;
    }
}
