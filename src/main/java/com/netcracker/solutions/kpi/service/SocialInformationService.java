package com.netcracker.solutions.kpi.service;

import com.netcracker.solutions.kpi.persistence.model.SocialNetwork;

import java.util.Set;

/**
 * @author Chalienko 15.04.2016.
 */
public interface SocialInformationService {
    SocialInformation getById(Long id);

    Set<SocialInformation> getByUserId(Long id);

    Long insertSocialInformation(SocialInformation socialInformation, User user, SocialNetwork socialNetwork);

    int updateSocialInformation(SocialInformation socialInformation);

    int deleteSocialInformation(SocialInformation socialInformation);

    boolean isExist(String email, Long idSocialNetwork);

    SocialInformation getByUserEmailSocialNetworkType(String email, Long socialNetworkId);

    boolean isExist(Long idUserInSocialNetwork, Long idSocialNetwork);

    SocialInformation getByIdUserInSocialNetworkSocialType(Long idUserInSocialNetwork, Long idSocialNetwork);

    int updateSocialInformation(Long idNetwork, Long idUser, String info);
}
