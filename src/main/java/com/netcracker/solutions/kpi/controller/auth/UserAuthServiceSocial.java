package com.netcracker.solutions.kpi.controller.auth;

import com.netcracker.solutions.kpi.persistence.model.SocialInformation;
import com.netcracker.solutions.kpi.persistence.model.User;
import com.netcracker.solutions.kpi.service.SocialInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserAuthServiceSocial {

    @Autowired
    private SocialInformationService socialInformationService;

   /* private UserAuthServiceSocial() {
        socialInformationService = ServiceFactory.getSocialInformationService();
    }*/

   /* private static class UserAuthServiceSocialHolder{
        private static final UserAuthServiceSocial HOLDER = new UserAuthServiceSocial();
    }

    public static UserAuthServiceSocial getInstance(){
        return UserAuthServiceSocialHolder.HOLDER;
    }
*/

    public User loadUserBySocialIdNetworkId(Long userId, Long socialNetworkId) throws UsernameNotFoundException {
        SocialInformation socialInformation = socialInformationService.getByIdUserInSocialNetworkSocialType(userId, socialNetworkId);
        if (null == socialInformation){
            return null;
        }else {
            return socialInformation.getUser();
        }
    }
}
