package com.netcracker.solutions.kpi.persistence.dto;

/**
 * @author Korzh
 */
public class QuestionVariantDto {

    private  long id;
    private String variant;

    public QuestionVariantDto() {
    }

    public QuestionVariantDto(long id, String variant) {
        this.id = id;
        this.variant = variant;
    }

    public String getVariant() {
        return variant;
    }

    public void setVariant(String variant) {
        this.variant = variant;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "QuestionVariantDto{" +
                "id=" + id +
                ", variant='" + variant + '\'' +
                '}';
    }


}
