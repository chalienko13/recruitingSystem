package com.netcracker.solutions.kpi.persistence.dto;

import java.util.List;

/**
 * Created by Alona on 27.05.2016.
 */
public class StaffFiltrationParamsDto {
    private int pageNum;
    private Long rowsNum;
    private Long sortingCol;
    private boolean increase;
    private Long idStart;
    private Long idFinish;
    private List<Long> rolesId;
    private boolean interviewer;
    private boolean notInterviewer;
    private boolean notEvaluated;

    public StaffFiltrationParamsDto() {
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

    public Long getIdStart() {
        return idStart;
    }

    public void setIdStart(Long idStart) {
        this.idStart = idStart;
    }

    public Long getIdFinish() {
        return idFinish;
    }

    public void setIdFinish(Long idFinish) {
        this.idFinish = idFinish;
    }

    public List<Long> getRolesId() {
        return rolesId;
    }

    public void setRolesId(List<Long> rolesId) {
        this.rolesId = rolesId;
    }

    public boolean isInterviewer() {
        return interviewer;
    }

    public void setInterviewer(boolean interviewer) {
        this.interviewer = interviewer;
    }

    public boolean isNotInterviewer() {
        return notInterviewer;
    }

    public void setNotInterviewer(boolean notInterviewer) {
        this.notInterviewer = notInterviewer;
    }

    public boolean isNotEvaluated() {
        return notEvaluated;
    }

    public void setNotEvaluated(boolean notEvaluated) {
        this.notEvaluated = notEvaluated;
    }
}
