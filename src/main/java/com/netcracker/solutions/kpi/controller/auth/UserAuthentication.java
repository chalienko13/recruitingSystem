package com.netcracker.solutions.kpi.controller.auth;

import com.netcracker.solutions.kpi.persistence.model.SocialInformation;
import com.netcracker.solutions.kpi.persistence.model.SocialNetwork;
import com.netcracker.solutions.kpi.persistence.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;

/**
 * Created by IO on 23.04.2016.
 */
public class UserAuthentication implements Authentication {


    private User user;
    private boolean authenticated = true;
    private Long idUserSocialNetwork;
    private Long idNetwork;

    public UserAuthentication(String email, SocialNetwork socialNetwork, String accessToken, Long userSocialId) {
        user = new User();
        SocialInformation socialInformation = new SocialInformation
                (
                        socialNetwork, accessToken, new Timestamp(System.currentTimeMillis())
                );
        socialInformation.setIdUserInSocialNetwork(userSocialId);
        user.setSocialInformations(new ArrayList<>());
        user.getSocialInformations().add(socialInformation);
        user.setEmail(email);
        socialInformation.setUser(user);
        this.idUserSocialNetwork = userSocialId;
        this.idNetwork = socialNetwork.getId();
    }

    public UserAuthentication(User user) {
        this.user = user;
//        user.getQuestionRoles();
    }

    public UserAuthentication(User user, Long idUserSocialNetwork, Long idNetwork) {
        this.user = user;
        this.idUserSocialNetwork = idUserSocialNetwork;
        this.idNetwork = idNetwork;
    }

    @Override
    public String getName() {
        return user.getUsername();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getAuthorities();
    }

    @Override
    public Object getCredentials() {
        return user.getPassword();
    }

    @Override
    public User getDetails() {
        return user;
    }

    @Override
    public Object getPrincipal() {
        return user.getUsername();
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    public Long getIdUserSocialNetwork() {
        return idUserSocialNetwork;
    }

    public void setIdUserSocialNetwork(Long idUserSocialNetwork) {
        this.idUserSocialNetwork = idUserSocialNetwork;
    }

    public Long getIdNetwork() {
        return idNetwork;
    }

    public void setIdNetwork(Long idNetwork) {
        this.idNetwork = idNetwork;
    }
}
