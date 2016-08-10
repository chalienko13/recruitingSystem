package com.netcracker.solutions.kpi.controller.auth;

import com.netcracker.solutions.kpi.persistence.dto.CurrentAuthUser;
import com.netcracker.solutions.kpi.persistence.model.User;
import com.netcracker.solutions.kpi.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/currentUser")
public class AuthenticationController {
    private static Logger log = LoggerFactory.getLogger(AuthenticationController.class.getName());

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public CurrentAuthUser getCurrentUser() {
//        log.info("Looking authorized user");
//        if (SecurityContextHolder.getContext().getAuthentication() instanceof UserAuthentication) {
//            org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User)
//                    SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//            log.info("Authorized user found - {}", user.getUsername());
//            return new CurrentAuthUser(user.getId(), user.getFirstName(), user.getRoles());
//        } else {
//            log.info("Authorized user not found");
//            return null;
//        }
        return null;
    }
}
