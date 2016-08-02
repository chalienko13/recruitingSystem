package com.netcracker.solutions.kpi.controller.auth;

import com.netcracker.solutions.kpi.persistence.dto.CurrentAuthUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/currentUser")
public class AuthenticationController {
    private static Logger log = LoggerFactory.getLogger(AuthenticationController.class.getName());

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public CurrentAuthUser getCurrentUser() {
        log.info("Looking authorized user");
        if (SecurityContextHolder.getContext().getAuthentication() instanceof UserAuthentication){
            User user = ((UserAuthentication) SecurityContextHolder.getContext().getAuthentication()).getDetails();
            log.info("Authorized user found - {}", user.getEmail());
            return new  CurrentAuthUser(user.getId(), user.getFirstName(), user.getRoles());
        }else {
            log.info("Authorized user not found");
            return null;
        }
    }
}
