package com.netcracker.solutions.kpi.controller.auth;

import com.netcracker.solutions.kpi.controller.auth.Utils.FaceBookUtils;
import com.netcracker.solutions.kpi.persistence.model.Role;
import com.netcracker.solutions.kpi.persistence.model.SocialInformation;
import com.netcracker.solutions.kpi.persistence.model.SocialNetwork;
import com.netcracker.solutions.kpi.persistence.model.User;
import com.netcracker.solutions.kpi.persistence.model.enums.RoleEnum;
import com.netcracker.solutions.kpi.persistence.model.enums.SocialNetworkEnum;
import com.netcracker.solutions.kpi.persistence.model.impl.real.UserImpl;
import com.netcracker.solutions.kpi.service.SocialInformationService;
import com.netcracker.solutions.kpi.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;

@Component
public class SocialNetworkAuthenticationManager implements AuthenticationManager {

    private static Logger log = LoggerFactory.getLogger(SocialNetworkAuthenticationManager.class.getName());
    private final static boolean IS_ACTIVE = true;
    private final static String NO_PASSWORD = "";
    private final static String NO_CONFIRM_TOKEN = "";

    @Autowired
    private UserAuthServiceSocial userAuthServiceSocial;
    @Autowired
    private UserService userService;
    @Autowired
    private SocialInformationService socialInformationService;
    @Autowired
    private UserAuthServiceLoginPassword userDetailsService;// = UserAuthServiceLoginPassword.getInstance();


   /* private SocialNetworkAuthenticationManager() {
        userAuthServiceSocial = UserAuthServiceSocial.getInstance();
        socialInformationService = ServiceFactory.getSocialInformationService();
        userService = ServiceFactory.getUserService();
    }

    private static class SocialNetworkAuthenticationManagerHolder {
        private static final SocialNetworkAuthenticationManager HOLDER = new SocialNetworkAuthenticationManager();
    }

    public static SocialNetworkAuthenticationManager getInstance() {
        return SocialNetworkAuthenticationManagerHolder.HOLDER;
    }*/


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserAuthentication userAuthentication = ((UserAuthentication) authentication);
        SocialInformation socialInformation = getSocialInformation(userAuthentication);
        socialInformation.setIdUserInSocialNetwork(FaceBookUtils.getSocialUserId(socialInformation.getAccessInfo()));
        SocialNetwork socialNetwork = SocialNetworkEnum.getSocialNetwork(getSocialNetworkId(socialInformation));
        if (Objects.equals(socialNetwork.getTitle(), SocialNetworkEnum.FaceBook.getTitle())) {
            User user = userAuthServiceSocial.loadUserBySocialIdNetworkId(socialInformation.getIdUserInSocialNetwork(), socialNetwork.getId());
            if (null == user) {
                user = userDetailsService.loadUserByUsername(socialInformation.getUser().getEmail());
                if (null != user){
                    socialInformationService.insertSocialInformation(socialInformation, user, socialInformation.getSocialNetwork());
                }
                return registerFaceBookUser(socialInformation);
            } else {
                updateFaceBookUser(socialNetwork.getId(), socialInformation.getIdUserInSocialNetwork(), socialInformation.getAccessInfo());
                changeSocialInformation(socialInformation.getSocialNetwork().getId(), user, socialInformation.getAccessInfo());
                return new UserAuthentication(user, socialInformation.getIdUserInSocialNetwork(), socialNetwork.getId());
            }
        }
        return null;
    }

    private UserAuthentication registerFaceBookUser(SocialInformation socialInformation) {
        Facebook facebook = new FacebookTemplate(FaceBookUtils.getFaceBookAccessToken(socialInformation.getAccessInfo()));
        org.springframework.social.facebook.api.User profile = facebook.userOperations().getUserProfile();
        User user = createNewUser(profile);
        userService.insertUser(user, new ArrayList<>(Collections.singletonList(RoleEnum.getRole(RoleEnum.ROLE_STUDENT))));
        socialInformation.setUser(user);
        socialInformationService.insertSocialInformation(socialInformation, user, socialInformation.getSocialNetwork());
        return new UserAuthentication(user, socialInformation.getIdUserInSocialNetwork(), socialInformation.getSocialNetwork().getId());
    }

    private void updateFaceBookUser(Long idSocialNetwork, Long idSocialUser, String info) {
        int i = socialInformationService.updateSocialInformation(idSocialNetwork, idSocialUser, info);
        if (0 == i){
            throw new AuthenticationServiceException("Can not update social information");
        }
    }

    private User createNewUser(org.springframework.social.facebook.api.User profile) {
        User user = new UserImpl(profile.getEmail(),
                profile.getFirstName(),
                profile.getMiddleName(),
                profile.getLastName(),
                NO_PASSWORD,
                IS_ACTIVE,
                new Timestamp(System.currentTimeMillis()),
                NO_CONFIRM_TOKEN
        );
        user.setRoles(new HashSet<Role>() {{
            add(RoleEnum.getRole(RoleEnum.ROLE_STUDENT));
        }});
        return user;
    }

    private Long getSocialNetworkId(SocialInformation socialInformation) {
        return socialInformation.getSocialNetwork().getId();
    }

    private static SocialInformation getSocialInformation(UserAuthentication userAuthentication) {
        return userAuthentication.getDetails().getSocialInformations().iterator().next();
    }

    private static void changeSocialInformation(Long id, User user, String info){
        user.getSocialInformations().stream().filter(socialInformation -> Objects.equals(socialInformation.getSocialNetwork().getId(), id)).forEach(socialInformation -> {
            socialInformation.setAccessInfo(info);
        });
    }
}

