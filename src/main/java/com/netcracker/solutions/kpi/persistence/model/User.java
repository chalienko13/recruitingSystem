package com.netcracker.solutions.kpi.persistence.model;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.security.SocialUserDetails;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

/**
 * Created by Chalienko on 13.04.2016.
 */
public interface User extends Serializable, UserDetails, SocialUserDetails {

    Long getId();

    void setId(Long id);

    String getEmail();

    void setEmail(String email);

    String getFirstName();

    void setFirstName(String firstName);

    String getConfirmToken();

    void setConfirmToken(String confirmToken);

    boolean isActive();

    void setActive(boolean active);

    Timestamp getRegistrationDate();

    void setRegistrationDate(Timestamp registrationDate);

    List<UserTimePriority> getUserTimePriorities();

    void setUserTimePriorities(List<UserTimePriority> userTimePriorities);

    String getSecondName();

    void setSecondName(String secondName);

    String getLastName();

    void setLastName(String lastName);

    Set<Role> getRoles();

    void setRoles(Set<Role> roles);

    String getPassword();

    void setPassword(String password);

    Set<SocialInformation> getSocialInformations();

    void setSocialInformations(Set<SocialInformation> socialInformations);

    Long getExpireDate();

}
