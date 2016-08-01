package com.netcracker.solutions.kpi.persistence.dto;

/**
 * Created by IO on 06.05.2016.
 */
public class PasswordChangeDto {
    private String email;
    private String password;

    public PasswordChangeDto() {
    }

    public PasswordChangeDto(String password) {
        this.password = password;
    }

    public PasswordChangeDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
