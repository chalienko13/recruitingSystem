package com.netcracker.solutions.kpi.persistence.dto;

/**
 * Created by IO on 20.05.2016.
 */
public class AuthUserDto {

    private Long id;
    private String username;
    private String redirectURL;
    private String roles;

    public AuthUserDto(Long id, String username, String redirectURL, String roles) {
        this.id = id;
        this.username = username;
        this.redirectURL = redirectURL;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRedirectURL() {
        return redirectURL;
    }

    public void setRedirectURL(String redirectURL) {
        this.redirectURL = redirectURL;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "AuthUserDto{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", redirectURL='" + redirectURL + '\'' +
                ", roles='" + roles + '\'' +
                '}';
    }
}
