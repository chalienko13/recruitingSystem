package com.netcracker.solutions.kpi.controller.auth;

import com.netcracker.solutions.kpi.persistence.model.User;
import com.netcracker.solutions.kpi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;

/**
 * Created by IO on 27.05.2016.
 */
public class SocialAuthService implements SocialUserDetailsService {

    @Autowired
    private UserService userService;

   /* private SocialAuthService() {
        userService = ServiceFactory.getUserService();
    }

    private static class SocialAuthServiceHolder{
        private static final SocialAuthService HOLDER = new SocialAuthService();
    }

    public static SocialAuthService getInstance(){
        return SocialAuthServiceHolder.HOLDER;
    }*/

    //TODO log

    @Override
    public SocialUserDetails loadUserByUserId(String s) throws UsernameNotFoundException, DataAccessException {
        User user = userService.getUserByUsername(s);
        if (user == null){
            return null;
        }else {
            return (SocialUserDetails) user;
        }

    }
}
