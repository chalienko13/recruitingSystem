package com.netcracker.solutions.kpi.service.impl;

import com.netcracker.solutions.kpi.persistence.dao.SocialInformationDao;
import com.netcracker.solutions.kpi.persistence.model.SocialInformation;
import com.netcracker.solutions.kpi.persistence.model.SocialNetwork;
import com.netcracker.solutions.kpi.persistence.model.User;
import com.netcracker.solutions.kpi.service.SocialInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class SocialInformationServiceImpl implements SocialInformationService {

    @Autowired
    private SocialInformationDao socialInformationDao;

    /*public SocialInformationServiceImpl(SocialInformationDao socialInformationDao) {
        this.socialInformationDao = socialInformationDao;
    }*/

    @Override
    public SocialInformation getById(Long id) {
        return socialInformationDao.getById(id);
    }

    @Override
    public Set<SocialInformation> getByUserId(Long id) {
        return socialInformationDao.getByUserId(id);
    }

    @Override
    public Long insertSocialInformation(SocialInformation socialInformation, User user, SocialNetwork socialNetwork) {
        return socialInformationDao.insertSocialInformation(socialInformation, user, socialNetwork);
    }

    @Override
    public int updateSocialInformation(SocialInformation socialInformation) {
        return socialInformationDao.updateSocialInformation(socialInformation);
    }

    @Override
    public int deleteSocialInformation(SocialInformation socialInformation) {
        return socialInformationDao.deleteSocialInformation(socialInformation);
    }

    @Override
    public SocialInformation getByUserEmailSocialNetworkType(String email, Long socialNetworkId) {
        return socialInformationDao.getByUserEmailSocialType(email, socialNetworkId);
    }

    @Override
    public boolean isExist(String email, Long idSocialNetwork) {
        return socialInformationDao.isExist(email, idSocialNetwork);
    }

    @Override
    public boolean isExist(Long idUserInSocialNetwork, Long idSocialNetwork) {
        return socialInformationDao.isExist(idUserInSocialNetwork,idSocialNetwork);
    }

    @Override
    public SocialInformation getByIdUserInSocialNetworkSocialType(Long idUserInSocialNetwork, Long idSocialNetwork) {
        return socialInformationDao.getByIdUserInSocialNetworkSocialType(idUserInSocialNetwork,idSocialNetwork);
    }

    @Override
    public int updateSocialInformation(Long idNetwork, Long idUser, String info) {
        return socialInformationDao.updateSocialInformation(idNetwork, idUser, info);
    }
}
