package com.netcracker.solutions.kpi.controller.auth;


import com.netcracker.solutions.kpi.persistence.model.User;
import com.netcracker.solutions.kpi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserAuthServiceLoginPassword implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public User loadUserByUsername(String userName) throws UsernameNotFoundException {
        return userService.getUserByUsername(userName);
    }
}

