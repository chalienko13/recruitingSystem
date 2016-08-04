package com.netcracker.solutions.kpi.service.impl;

import com.netcracker.solutions.kpi.persistence.dao.SocialInformationDao;
import com.netcracker.solutions.kpi.persistence.model.SocialInformation;
import com.netcracker.solutions.kpi.persistence.model.SocialNetwork;
import com.netcracker.solutions.kpi.persistence.model.User;
import com.netcracker.solutions.kpi.persistence.repository.SocialInformationRepository;
import com.netcracker.solutions.kpi.service.SocialInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class SocialInformationServiceImpl implements SocialInformationService {

    @Autowired
    private SocialInformationDao socialInformationDao;

    @Autowired
    private SocialInformationRepository socialInformationRepository;

    /*public SocialInformationServiceImpl(SocialInformationDao socialInformationDao) {
        this.socialInformationDao = socialInformationDao;
    }*/

    @Override
    public SocialInformation getById(Long id) {
        return socialInformationRepository.findOne(id);
    }

    @Override
    public Set<SocialInformation> getByUserId(Long id) {
        return socialInformationRepository.getByUserId(id);
    }

    @Override
    public Long insertSocialInformation(SocialInformation socialInformation, User user, SocialNetwork socialNetwork) {
        return socialInformationRepository.insertSocialInformation(socialInformation.getAccessInfo(), user.getId(),
                socialNetwork.getId(), socialInformation.getIdUserInSocialNetwork());
    }

    @Override
    public int updateSocialInformation(SocialInformation socialInformation) {
        return socialInformationRepository.updateSocialInformation(socialInformation.getAccessInfo(),
                socialInformation.getId());
    }

    @Override
    public void deleteSocialInformation(SocialInformation socialInformation) {
        socialInformationRepository.delete(socialInformation);
    }

    @Override
    public SocialInformation getByUserEmailSocialNetworkType(String email, Long socialNetworkId) {
        return socialInformationRepository.getByUserEmailSocialNetworkType(email, socialNetworkId);
    }

    @Override
    public boolean isExist(String email, Long idSocialNetwork) {
        return socialInformationRepository.isExist(email, idSocialNetwork);
    }

    @Override
    public boolean isExist(Long idUserInSocialNetwork, Long idSocialNetwork) {
        return socialInformationRepository.isExist(idUserInSocialNetwork,idSocialNetwork);
    }

    @Override
    public SocialInformation getByIdUserInSocialNetworkSocialType(Long idUserInSocialNetwork, Long idSocialNetwork) {
        return socialInformationRepository.getByIdUserInSocialNetworkSocialType(idUserInSocialNetwork,idSocialNetwork);
    }

    @Override
    public int updateSocialInformation(Long idNetwork, Long idUser, String info) {
        return socialInformationRepository.updateSocialInformation(idNetwork, idUser, info);
    }
}
