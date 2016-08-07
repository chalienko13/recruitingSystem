package com.netcracker.solutions.kpi.config;

import com.netcracker.solutions.kpi.controller.auth.*;
import com.netcracker.solutions.kpi.filter.StatelessAuthenticationFilter;
import com.netcracker.solutions.kpi.filter.StatelessLoginFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.PostConstruct;

@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = "com.netcracker.solutions.kpi")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String SECRET_KEY = "verySecret";

    @Autowired
    private LoginPasswordAuthenticationManager loginPasswordAuthenticationManager;// = LoginPasswordAuthenticationManager.getInstance();

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;// = AuthenticationSuccessHandlerService.getInstance();

    @Autowired
    private UserAuthServiceLoginPassword userAuthServiceLoginPassword;// = UserAuthServiceLoginPassword.getInstance();

    @Autowired
    @Qualifier("TokenHandlerLoginPassword")
    private TokenHandlerLoginPassword tokenHandlerLoginPassword;// = TokenHandlerLoginPassword.getInstance();


    @Autowired
    private AuthenticationSuccessHandlerService authenticationSuccessHandlerService;

    private TokenAuthenticationService tokenAuthenticationServiceLoginPassword;

    @PostConstruct
    public void init() {
        tokenAuthenticationServiceLoginPassword = new TokenAuthenticationService(tokenHandlerLoginPassword);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
                .antMatchers("/home").anonymous()

                .and()
                .authorizeRequests()
                .antMatchers("/frontend/module/admin/view/**").hasRole("ADMIN")
                .antMatchers("**/reports/**").hasRole("ADMIN")

                .and()
                .authorizeRequests()
                .antMatchers("/frontend/module/student/view/**").hasRole("STUDENT")
                .antMatchers("/student/appform/**").permitAll()
                .antMatchers("/student/**").hasRole("STUDENT")

                .and()
                .authorizeRequests()
                .antMatchers("/frontend/module/staff/view/**").hasAnyRole("SOFT", "TECH")
                .antMatchers("/staff/appForm/**").permitAll()
                .antMatchers("/staff/**").hasAnyRole("SOFT", "TECH")

                .and()

                .addFilterBefore(new StatelessAuthenticationFilter(tokenAuthenticationServiceLoginPassword),
                        UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new StatelessLoginFilter("/loginIn", tokenAuthenticationServiceLoginPassword,
                                loginPasswordAuthenticationManager, authenticationSuccessHandler, authenticationSuccessHandlerService),
                        UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling().and()
                .csrf().disable()
                .servletApi().and()
                .headers().cacheControl();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userAuthServiceLoginPassword).passwordEncoder(new BCryptPasswordEncoder());
    }
}