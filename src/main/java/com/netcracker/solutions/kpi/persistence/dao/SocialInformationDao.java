package com.netcracker.solutions.kpi.persistence.dao;

import com.netcracker.solutions.kpi.persistence.model.SocialInformation;
import com.netcracker.solutions.kpi.persistence.model.SocialNetwork;
import com.netcracker.solutions.kpi.persistence.model.User;

import java.util.Set;

/**
 * Created by Chalienko on 15.04.2016.
 */
public interface SocialInformationDao {

	SocialInformation getById(Long id);

	SocialInformation getByUserEmailSocialType(String email, Long idSocialType);

	Set<SocialInformation> getByUserId(Long id);

	Long insertSocialInformation(SocialInformation socialInformation, User user, SocialNetwork socialNetwork);

	int updateSocialInformation(SocialInformation socialInformation);

	int deleteSocialInformation(SocialInformation socialInformation);

	boolean isExist(String email, Long idSocialNetwork);

	boolean isExist(Long idUserInSocialNetwork, Long idSocialNetwork);

	SocialInformation getByIdUserInSocialNetworkSocialType(Long idUserInSocialNetwork, Long idSocialNetwork);

	int updateSocialInformation(Long idNetwork, Long idUser, String info);
}
