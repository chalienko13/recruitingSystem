package com.netcracker.solutions.kpi.filter;

import com.netcracker.solutions.kpi.controller.auth.TokenAuthenticationService;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configurable
public class StatelessAuthenticationFilter extends GenericFilterBean {

    private static final String AUTH_HEADER_NAME_LOGIN_PASSWORD = "X-AUTH-TOKEN_LOGIN_PASSWORD";

    private final TokenAuthenticationService tokenAuthenticationServiceLoginPassword;

    public StatelessAuthenticationFilter(TokenAuthenticationService tokenAuthenticationServiceLoginPassword) {
        this.tokenAuthenticationServiceLoginPassword = tokenAuthenticationServiceLoginPassword;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException,
            ServletException {
        HttpServletRequest request = ((HttpServletRequest) req);
        Authentication authentication = null;
        if (request.getHeader(AUTH_HEADER_NAME_LOGIN_PASSWORD) != null) {
            authentication = tokenAuthenticationServiceLoginPassword.getAuthentication((HttpServletRequest) req,
                    (HttpServletResponse) res);
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }
}
