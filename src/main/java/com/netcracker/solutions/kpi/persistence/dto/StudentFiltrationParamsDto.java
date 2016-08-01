package com.netcracker.solutions.kpi.persistence.dto;

import java.util.List;

/**
 * Created by Alona on 23.05.2016.
 */
public class StudentFiltrationParamsDto {
    private int pageNum;
    private Long rowsNum;
    private Long sortingCol;
    private boolean increase;
    //    private Long idStart;
//    private Long idFinish;
//    private List<Long> rolesId;
//    private boolean interviewer;
//    private boolean notInterviewer;
//    private boolean notEvaluated;
    private List<String> restrictions;
    private List<String> statuses;
    private boolean active;

    public StudentFiltrationParamsDto() {
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public Long getRowsNum() {
        return rowsNum;
    }

    public void setRowsNum(Long rowsNum) {
        this.rowsNum = rowsNum;
    }

    public Long getSortingCol() {
        return sortingCol;
    }

    public void setSortingCol(Long sortingCol) {
        this.sortingCol = sortingCol;
    }

    public boolean isIncrease() {
        return increase;
    }

    public void setIncrease(boolean increase) {
        this.increase = increase;
    }

    public List<String> getRestrictions() {
        return restrictions;
    }

    public void setRestrictions(List<String> restrictions) {
        this.restrictions = restrictions;
    }

    public List<String> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<String> statuses) {
        this.statuses = statuses;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
