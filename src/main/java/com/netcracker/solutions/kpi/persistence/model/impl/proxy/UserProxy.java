package com.netcracker.solutions.kpi.persistence.model.impl.proxy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.netcracker.solutions.kpi.persistence.model.Role;
import com.netcracker.solutions.kpi.persistence.model.SocialInformation;
import com.netcracker.solutions.kpi.persistence.model.User;
import com.netcracker.solutions.kpi.persistence.model.UserTimePriority;
import com.netcracker.solutions.kpi.persistence.model.impl.real.UserImpl;
import com.netcracker.solutions.kpi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.core.GrantedAuthority;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Configurable
public class UserProxy implements User {

    private static final long serialVersionUID = -707606021441077440L;
    @JsonIgnore
    private Long id;

    @JsonIgnore
    private UserImpl user;

    @Autowired
    private UserService userService;

    public UserProxy() {
        super();
    }

    public UserProxy(Long id) {
        this();
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getEmail() {
        checkUserForExist();
        return user.getEmail();
    }

    @Override
    public void setEmail(String email) {
        checkUserForExist();
        user.setEmail(email);
    }

    @Override
    public String getFirstName() {
        checkUserForExist();
        return user.getFirstName();
    }

    @Override
    public void setFirstName(String firstName) {
        checkUserForExist();
        user.setFirstName(firstName);
    }

    @Override
    public String getConfirmToken() {
        checkUserForExist();
        return user.getConfirmToken();
    }

    @Override
    public void setConfirmToken(String confirmToken) {
        checkUserForExist();
        user.setConfirmToken(confirmToken);

    }

    @Override
    public boolean isActive() {
        checkUserForExist();
        return user.isActive();
    }

    @Override
    public void setActive(boolean active) {
        checkUserForExist();
        user.setActive(active);

    }

    @Override
    public Timestamp getRegistrationDate() {
        checkUserForExist();
        return user.getRegistrationDate();
    }

    @Override
    public void setRegistrationDate(Timestamp registrationDate) {
        checkUserForExist();
        user.setRegistrationDate(registrationDate);

    }

    @Override
    public List<UserTimePriority> getUserTimePriorities() {
        checkUserForExist();
        return user.getUserTimePriorities();
    }

    @Override
    public void setUserTimePriorities(List<UserTimePriority> userTimePriorities) {
        checkUserForExist();
        user.setUserTimePriorities(userTimePriorities);
    }

    @Override
    public String getSecondName() {
        checkUserForExist();
        return user.getSecondName();
    }

    @Override
    public void setSecondName(String secondName) {
        checkUserForExist();
        user.setSecondName(secondName);
    }

    @Override
    public String getLastName() {
        checkUserForExist();
        return user.getLastName();
    }

    @Override
    public void setLastName(String lastName) {
        checkUserForExist();
        user.setLastName(lastName);
    }

    @Override
    public Set<Role> getRoles() {
        checkUserForExist();
        return user.getRoles();
    }

    @Override
    public void setRoles(Set<Role> roles) {
        checkUserForExist();
        user.setRoles(roles);
    }

    @Override
    public String getPassword() {
        checkUserForExist();
        return user.getPassword();
    }

    @Override
    public void setPassword(String password) {
        checkUserForExist();
        user.setPassword(password);
    }

    @Override
    public Set<SocialInformation> getSocialInformations() {
        checkUserForExist();
        return user.getSocialInformations();
    }

    @Override
    public void setSocialInformations(Set<SocialInformation> socialInformations) {
        checkUserForExist();
        user.setSocialInformations(socialInformations);
    }

    @Override
    public Long getExpireDate() {
        checkUserForExist();
        return user.getExpireDate();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        checkUserForExist();
        return user.getAuthorities();
    }

    //TODO
    @Override
    public String getUsername() {
        checkUserForExist();
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }


    private void checkUserForExist() {
        if (user == null) {
            user = downloadUser();
        }
    }

    private UserImpl downloadUser() {
        return (UserImpl) userService.getUserByID(id);
    }

    @Override
    public String getUserId() {
        checkUserForExist();
        return user.getEmail();
    }

}
