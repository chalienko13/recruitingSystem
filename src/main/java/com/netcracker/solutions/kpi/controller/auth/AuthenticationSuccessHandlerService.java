package com.netcracker.solutions.kpi.controller.auth;

import com.google.gson.Gson;
import com.netcracker.solutions.kpi.persistence.dto.AuthUserDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthenticationSuccessHandlerService implements AuthenticationSuccessHandler {

   /* private static AuthenticationSuccessHandlerService customAuthenticationSuccessHandler;

    private AuthenticationSuccessHandlerService() {

    }

    public static AuthenticationSuccessHandlerService getInstance() {
        if (customAuthenticationSuccessHandler == null) {
            customAuthenticationSuccessHandler = new AuthenticationSuccessHandlerService();
        }
        return customAuthenticationSuccessHandler;
    }*/

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication) throws
            ServletException, IOException {
        System.out.println("authentication " + authentication);
        System.out.println("ID: " + ((User) authentication.getDetails()).getId());
        System.out.println("username: " + ((User) authentication.getDetails()).getUsername());
        response.getWriter().write(new Gson().toJson(
                new AuthUserDto(
                        ((User) authentication.getDetails()).getId(),
                        ((User) authentication.getDetails()).getUsername(),
                        determineTargetUrl(authentication),
                        new HashSet(authentication.getAuthorities().stream().map(
                                GrantedAuthority::getAuthority).collect(Collectors.toSet())).toString()
                )
        ));
    }

    private String determineTargetUrl(Authentication authentication) {
        System.out.println(authentication.getAuthorities());
        Set<String> authorities = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        System.out.println(authorities);
        if (authorities.contains("ROLE_ADMIN")) {
            return "admin/main";
        } else if (authorities.contains("ROLE_STUDENT")) {
            return "student/appform";
        } else if (authorities.contains("ROLE_SOFT")) {
            return "staff/main";
        } else if (authorities.contains("ROLE_TECH")) {
            return "staff/main";
        } else {
            throw new IllegalStateException();
        }
    }
}