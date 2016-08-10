package com.netcracker.solutions.kpi.config.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;

/**
 * Created by dmch0716 on 05.08.2016.
 */
@Component(value = "successHandler")
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication) throws IOException {
        handle(request, response, authentication);
        clearAuthenticationAttributes(request);
    }

    protected void handle(HttpServletRequest request,
                          HttpServletResponse response, Authentication authentication) throws IOException {
        String targetUrl = determineTargetUrl(authentication);
/*        response.addHeader("redirecturl", targetUrl);
        request.setAttribute("redirecturl", targetUrl);*/
        redirectStrategy.sendRedirect(request, response, "/frontend/index.html");
    }

    /**
     * Builds the target URL according to the logic defined in the main class Javadoc.
     */
    private String determineTargetUrl(Authentication authentication) {

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority grantedAuthority : authorities) {
            if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
                return "/frontend/index.html";
            } else if (grantedAuthority.getAuthority().equals("ROLE_TECH")) {
                return "/staff/main";
            }else if (grantedAuthority.getAuthority().equals("ROLE_SOFT")) {
                return "/staff/main";
            }else if (grantedAuthority.getAuthority().equals("ROLE_STUDENT")) {
                return "/student/appform";
            }
            return "/frontend/index.html";
        }
        return "/frontend/index.html";
    }

    private void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }

    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }

    protected RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }
}
