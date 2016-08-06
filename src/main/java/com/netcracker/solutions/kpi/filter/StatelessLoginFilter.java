package com.netcracker.solutions.kpi.filter;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.netcracker.solutions.kpi.controller.auth.AuthenticationSuccessHandlerService;
import com.netcracker.solutions.kpi.controller.auth.TokenAuthenticationService;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configurable
public class StatelessLoginFilter extends AbstractAuthenticationProcessingFilter {

    private static final String LOGIN_TITLE = "email";
    private static final String PASSWORD_TITLE = "password";
    private TokenAuthenticationService tokenAuthenticationService;
    private AuthenticationSuccessHandlerService authenticationSuccessHandlerService;

    public StatelessLoginFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    public StatelessLoginFilter(String urlMapping, TokenAuthenticationService tokenAuthenticationService,
                                AuthenticationManager authenticationManager, AuthenticationSuccessHandler authenticationSuccessHandler,
                                AuthenticationSuccessHandlerService authenticationSuccessHandlerService) {
        super(new AntPathRequestMatcher(urlMapping));
        this.tokenAuthenticationService = tokenAuthenticationService;
        this.authenticationSuccessHandlerService = authenticationSuccessHandlerService;
        setAuthenticationManager(authenticationManager);
        setAuthenticationSuccessHandler(authenticationSuccessHandler);
//        setAuthenticationSuccessHandler(new SimpleUrlAuthenticationSuccessHandler());
    }

    private static String getLogin(JsonObject jsonObject) {
        return jsonObject.get(LOGIN_TITLE).getAsString();
    }

    private static String getPassword(JsonObject jsonObject) {
        return jsonObject.get(PASSWORD_TITLE).getAsString();
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        JsonObject obj = (JsonObject) new JsonParser().parse(request.getReader());
        UsernamePasswordAuthenticationToken loginToken = new UsernamePasswordAuthenticationToken(
                getLogin(obj), getPassword(obj));
        return getAuthenticationManager().authenticate(loginToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authentication) throws IOException, ServletException {
        tokenAuthenticationService.addAuthentication(response, authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //AuthenticationSuccessHandlerService.getInstance().onAuthenticationSuccess(request,response,authentication);
        authenticationSuccessHandlerService.onAuthenticationSuccess(request, response, authentication);
    }
}
