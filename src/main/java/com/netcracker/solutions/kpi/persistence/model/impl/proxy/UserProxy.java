package com.netcracker.solutions.kpi.persistence.model.impl.proxy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.netcracker.solutions.kpi.persistence.model.Role;
import com.netcracker.solutions.kpi.persistence.model.SocialInformation;
import com.netcracker.solutions.kpi.persistence.model.UserTimePriority;
import com.netcracker.solutions.kpi.persistence.model.User;
import com.netcracker.solutions.kpi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.core.GrantedAuthority;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Configurable
public class UserProxy  {

    private static final long serialVersionUID = -707606021441077440L;
    @JsonIgnore
    private Long id;

    @JsonIgnore
    private User user;

    @Autowired
    private UserService userService;

    public UserProxy() {
        super();
    }

    public UserProxy(Long id) {
        this();
        this.id = id;
    }


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getEmail() {
        checkUserForExist();
        return user.getEmail();
    }


    public void setEmail(String email) {
        checkUserForExist();
        user.setEmail(email);
    }


    public String getFirstName() {
        checkUserForExist();
        return user.getFirstName();
    }


    public void setFirstName(String firstName) {
        checkUserForExist();
        user.setFirstName(firstName);
    }


    public String getConfirmToken() {
        checkUserForExist();
        return user.getConfirmToken();
    }


    public void setConfirmToken(String confirmToken) {
        checkUserForExist();
        user.setConfirmToken(confirmToken);

    }


    public boolean isActive() {
        checkUserForExist();
        return user.isActive();
    }


    public void setActive(boolean active) {
        checkUserForExist();
        user.setActive(active);

    }


    public Timestamp getRegistrationDate() {
        checkUserForExist();
        return user.getRegistrationDate();
    }


    public void setRegistrationDate(Timestamp registrationDate) {
        checkUserForExist();
        user.setRegistrationDate(registrationDate);

    }


    public List<UserTimePriority> getUserTimePriorities() {
        checkUserForExist();
        return user.getUserTimePriorities();
    }


    public void setUserTimePriorities(List<UserTimePriority> userTimePriorities) {
        checkUserForExist();
        user.setUserTimePriorities(userTimePriorities);
    }


    public String getSecondName() {
        checkUserForExist();
        return user.getSecondName();
    }


    public void setSecondName(String secondName) {
        checkUserForExist();
        user.setSecondName(secondName);
    }


    public String getLastName() {
        checkUserForExist();
        return user.getLastName();
    }


    public void setLastName(String lastName) {
        checkUserForExist();
        user.setLastName(lastName);
    }


    public Set<Role> getRoles() {
        checkUserForExist();
        return user.getRoles();
    }


    public void setRoles(Set<Role> roles) {
        checkUserForExist();
        user.setRoles(roles);
    }


    public String getPassword() {
        checkUserForExist();
        return user.getPassword();
    }


    public void setPassword(String password) {
        checkUserForExist();
        user.setPassword(password);
    }


    public List<SocialInformation> getSocialInformations() {
        checkUserForExist();
        return user.getSocialInformations();
    }


    public void setSocialInformations(List<SocialInformation> socialInformations) {
        checkUserForExist();
        user.setSocialInformations(socialInformations);
    }


    public Long getExpireDate() {
        checkUserForExist();
        return user.getExpireDate();
    }



    public Collection<? extends GrantedAuthority> getAuthorities() {
        checkUserForExist();
        return user.getAuthorities();
    }

    //TODO

    public String getUsername() {
        checkUserForExist();
        return user.getUsername();
    }


    public boolean isAccountNonExpired() {
        return false;
    }


    public boolean isAccountNonLocked() {
        return false;
    }


    public boolean isCredentialsNonExpired() {
        return false;
    }


    public boolean isEnabled() {
        return false;
    }


    private void checkUserForExist() {
        if (user == null) {
            user = downloadUser();
        }
    }

    private User downloadUser() {
        return (User) userService.getUserByID(id);
    }


    public String getUserId() {
        checkUserForExist();
        return user.getEmail();
    }

}
