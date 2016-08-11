package com.netcracker.solutions.kpi.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

import javax.annotation.Resource;


@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = "com.netcracker.solutions.kpi")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    public void registerGlobalAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordencoder());
    }

    @Autowired
    private AuthenticationSuccessHandler successHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().csrfTokenRepository(csrfTokenRepository())
                .and()
                .authorizeRequests()
                .antMatchers("/home").anonymous()
                .and()
                .authorizeRequests()
                .antMatchers("/frontend/module/admin/view/**").hasRole("ADMIN")
                .antMatchers("**/reports/**").hasRole("ADMIN")


                .antMatchers("/frontend/module/student/view/**").hasRole("STUDENT")
                .antMatchers("/student/appform/**").permitAll()
                .antMatchers("/student/**").hasRole("STUDENT")


                .antMatchers("/frontend/module/staff/view/**").hasAnyRole("SOFT", "TECH")
                .antMatchers("/staff/appForm/**").permitAll()
                .antMatchers("/staff/**").hasAnyRole("SOFT", "TECH")
                .and()
                .addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class);

        http
                .csrf().csrfTokenRepository(csrfTokenRepository())
                .and()
                .formLogin()
                .loginPage("/")
                .loginProcessingUrl("/j_spring_security_check")
                .usernameParameter("username")
                .passwordParameter("password")
                .successHandler(successHandler)
                .failureUrl("/login?error")
                .permitAll()
                .and()
                .addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class);


        http.logout()
                .permitAll()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true);

    }

    private CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setHeaderName("X-XSRF-TOKEN");
        return repository;
    }

    @Bean(name = "passwordEncoder")
    public PasswordEncoder passwordencoder() {
        return new BCryptPasswordEncoder();
    }

}