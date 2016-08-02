package com.netcracker.solutions.kpi.controller.auth;

import com.netcracker.solutions.kpi.persistence.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class LoginPasswordAuthenticationManager implements AuthenticationManager {

    private static Logger log = LoggerFactory.getLogger(LoginPasswordAuthenticationManager.class.getName());

    @Autowired
    private UserAuthServiceLoginPassword userAuthServiceLoginPassword;
    @Autowired
    private PasswordEncoderGeneratorService passwordEncoderGeneratorService;

    /*private LoginPasswordAuthenticationManager() {
        this.userAuthServiceLoginPassword = UserAuthServiceLoginPassword.getInstance();
        this.passwordEncoderGeneratorService = PasswordEncoderGeneratorService.getInstance();
    }*/

    /*private static class LoginPasswordAuthenticationProviderHolder{
        private static final LoginPasswordAuthenticationManager HOLDER = new LoginPasswordAuthenticationManager();
    }*/

   /* public static LoginPasswordAuthenticationManager getInstance(){
        return LoginPasswordAuthenticationProviderHolder.HOLDER;
    }*/

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        log.info("Looking user - {} in data base", username);
        User user = userAuthServiceLoginPassword.loadUserByUsername(username);

        if(user == null) {
            throw new BadCredentialsException("User is not active");
        }

        if (!user.isActive()){
            log.info("User - {} is not active", username);
            throw new BadCredentialsException("User is not active");
        }

        if (!user.getUsername().equalsIgnoreCase(username)){
            log.info("User - {} not found", username);
            throw new BadCredentialsException("Username not found");
        }

        if (!passwordEncoderGeneratorService.matches(user.getPassword(), password )){
            log.info("User - {} have wrong password", username);
            throw new BadCredentialsException("Password wrong");
        }
        log.info("User - {} has been auth", username);
        return new UserAuthentication(user);
    }
}
