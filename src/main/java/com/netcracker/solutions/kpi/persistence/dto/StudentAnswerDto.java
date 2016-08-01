package com.netcracker.solutions.kpi.persistence.dto;

/**
 * @author Korzh
 */
public class StudentAnswerDto {
    private String answer;
    private long id;

    public StudentAnswerDto() {
    }

    public StudentAnswerDto(String answer, long id) {
        this.answer = answer;
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public long getID() {
        return id;
    }

    public void setID(long ID) {
        this.id = ID;
    }

    @Override
    public String toString() {
        return "StudentAnswerDto{" +
                "ID=" + id +
                ", answer='" + answer + '\'' +
                '}';
    }
}
