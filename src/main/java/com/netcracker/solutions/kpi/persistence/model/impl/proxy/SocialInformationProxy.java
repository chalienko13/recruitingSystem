package com.netcracker.solutions.kpi.persistence.model.impl.proxy;

import com.netcracker.solutions.kpi.persistence.model.SocialNetwork;
import com.netcracker.solutions.kpi.persistence.model.impl.real.SocialInformationImpl;
import com.netcracker.solutions.kpi.service.SocialInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

@Configurable
public class SocialInformationProxy implements SocialInformation {

    private static final long serialVersionUID = 8463245677164450837L;
    private Long id;

    private SocialInformationImpl socialInformation;

    @Autowired
    private SocialInformationService socialInformationService;

    public SocialInformationProxy(Long id) {
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
    public String getAccessInfo() {
        checkSocialInformationForExist();
        return socialInformation.getAccessInfo();
    }

    @Override
    public void setAccessInfo(String accessInfo) {
        checkSocialInformationForExist();
        socialInformation.setAccessInfo(accessInfo);
    }

    @Override
    public User getUser() {
        checkSocialInformationForExist();
        return socialInformation.getUser();
    }

    @Override
    public void setUser(User user) {
        checkSocialInformationForExist();
        socialInformation.setUser(user);
    }

    @Override
    public SocialNetwork getSocialNetwork() {
        checkSocialInformationForExist();
        return socialInformation.getSocialNetwork();
    }

    @Override
    public void setSocialNetwork(SocialNetwork socialNetwork) {
        checkSocialInformationForExist();
        socialInformation.setSocialNetwork(socialNetwork);
    }

    @Override
    public Long getIdUserInSocialNetwork() {
        checkSocialInformationForExist();
        return socialInformation.getIdUserInSocialNetwork();
    }

    @Override
    public void setIdUserInSocialNetwork(Long idUserInSocialNetwork) {
        checkSocialInformationForExist();
        socialInformation.setIdUserInSocialNetwork(idUserInSocialNetwork);
    }

    private void checkSocialInformationForExist(){
        if (socialInformation == null) {
            socialInformation = downloadSocialInformation();
        }
    }

    private SocialInformationImpl downloadSocialInformation() {
        return (SocialInformationImpl) socialInformationService.getById(id);
    }
}
