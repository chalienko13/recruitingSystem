package com.netcracker.solutions.kpi.persistence.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;


/**
 * Created by Chalienko on 15.04.2016.
 */
@Entity
@Table(name = "social_information")
public class SocialInformation implements Serializable {
    private static final long serialVersionUID = -3471843543708949773L;

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "access_info")
    private String accessInfo;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_user")
    private User user;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_social_network")
    private SocialNetwork socialNetwork;

    @Column(name = "id_user_in_social_network")
    private Long idUserInSocialNetwork;
    @Transient
    private Timestamp writeTime;

    public SocialInformation(Long id, SocialNetwork socialNetwork, String accessInfo) {
        this.id = id;
        this.socialNetwork = socialNetwork;
        this.accessInfo = accessInfo;
    }

    public SocialInformation(SocialNetwork socialNetwork, String accessInfo, Timestamp writeTime) {
        this.socialNetwork = socialNetwork;
        this.accessInfo = accessInfo;
        this.writeTime = writeTime;
    }


    public SocialInformation(Long id, String accessInfo, User user, SocialNetwork socialNetwork, Long idUserInSocialNetwork) {
        this.id = id;
        this.accessInfo = accessInfo;
        this.user = user;
        this.socialNetwork = socialNetwork;
        this.idUserInSocialNetwork = idUserInSocialNetwork;
    }

    public SocialInformation(String accessInfo, User user, SocialNetwork socialNetwork) {
        this.accessInfo = accessInfo;
        this.user = user;
        this.socialNetwork = socialNetwork;
    }

    public SocialInformation(Long id) {
        this.id = id;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccessInfo() {
        return accessInfo;
    }

    public void setAccessInfo(String accessInfo) {
        this.accessInfo = accessInfo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public SocialNetwork getSocialNetwork() {
        return socialNetwork;
    }

    public void setSocialNetwork(SocialNetwork socialNetwork) {
        this.socialNetwork = socialNetwork;
    }

    public Long getIdUserInSocialNetwork() {
        return idUserInSocialNetwork;
    }

    public void setIdUserInSocialNetwork(Long idUserInSocialNetwork) {
        this.idUserInSocialNetwork = idUserInSocialNetwork;
    }

    public Timestamp getWriteTime() {
        return writeTime;
    }

    public void setWriteTime(Timestamp writeTime) {
        this.writeTime = writeTime;
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

        SocialInformation that = (SocialInformation) o;

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
