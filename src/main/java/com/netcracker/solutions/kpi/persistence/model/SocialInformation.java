package com.netcracker.solutions.kpi.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

public interface SocialInformation extends Serializable{

    Long getId();

    void setId(Long id);

    @JsonIgnore
    String getAccessInfo();

    void setAccessInfo(String accessInfo);

    @JsonIgnore
    User getUser();

    void setUser(User user);
    @JsonIgnore
    SocialNetwork getSocialNetwork();

    void setSocialNetwork(SocialNetwork socialNetwork);

    Long getIdUserInSocialNetwork();

    void setIdUserInSocialNetwork(Long idUserInSocialNetwork);
}
