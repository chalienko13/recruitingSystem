package com.netcracker.solutions.kpi.persistence.model.impl.real;

import com.netcracker.solutions.kpi.persistence.model.SocialInformation;
import com.netcracker.solutions.kpi.persistence.model.SocialNetwork;
import com.netcracker.solutions.kpi.persistence.model.User;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.sql.Timestamp;


/**
 * Created by Chalienko on 15.04.2016.
 */
public class SocialInformationImpl implements SocialInformation {
    private static final long serialVersionUID = -3471843543708949773L;
    private Long id;
    private String accessInfo;
    private User user;
    private SocialNetwork socialNetwork;
    private Long idUserInSocialNetwork;
    private Timestamp writeTime;

    public SocialInformationImpl(Long id, SocialNetwork socialNetwork, String accessInfo) {
        this.id = id;
        this.socialNetwork = socialNetwork;
        this.accessInfo = accessInfo;
    }

    public SocialInformationImpl(SocialNetwork socialNetwork, String accessInfo, Timestamp writeTime) {
        this.socialNetwork = socialNetwork;
        this.accessInfo = accessInfo;
        this.writeTime = writeTime;
    }



    public SocialInformationImpl(Long id, String accessInfo, User user, SocialNetwork socialNetwork, Long idUserInSocialNetwork) {
        this.id = id;
        this.accessInfo = accessInfo;
        this.user = user;
        this.socialNetwork = socialNetwork;
        this.idUserInSocialNetwork = idUserInSocialNetwork;
    }

    public SocialInformationImpl(String accessInfo, User user, SocialNetwork socialNetwork) {
        this.accessInfo = accessInfo;
        this.user = user;
        this.socialNetwork = socialNetwork;
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
    public String getAccessInfo() {
        return accessInfo;
    }

    @Override
    public void setAccessInfo(String accessInfo) {
        this.accessInfo = accessInfo;
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public SocialNetwork getSocialNetwork() {
        return socialNetwork;
    }

    @Override
    public void setSocialNetwork(SocialNetwork socialNetwork) {
        this.socialNetwork = socialNetwork;
    }

    @Override
    public Long getIdUserInSocialNetwork() {
        return idUserInSocialNetwork;
    }
    @Override
    public void setIdUserInSocialNetwork(Long idUserInSocialNetwork) {
        this.idUserInSocialNetwork = idUserInSocialNetwork;
    }

    @Override
    public String toString() {
        return "SocialInformationImpl{" +
                "accessInfo='" + accessInfo + '\'' +
                ", user=" + user +
                ", socialNetwork=" + socialNetwork +
                ", idUserInSocialNetwork=" + idUserInSocialNetwork +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        SocialInformationImpl that = (SocialInformationImpl) o;

        return new EqualsBuilder()
                .append(accessInfo, that.accessInfo)
                .append(user, that.user)
                .append(socialNetwork, that.socialNetwork)
                .append(idUserInSocialNetwork, that.idUserInSocialNetwork)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(accessInfo)
                .append(user)
                .append(socialNetwork)
                .append(idUserInSocialNetwork)
                .toHashCode();
    }
}
