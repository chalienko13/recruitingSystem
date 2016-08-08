package com.netcracker.solutions.kpi.config.auth;

import com.netcracker.solutions.kpi.persistence.model.Role;
import com.netcracker.solutions.kpi.persistence.model.User;
import com.netcracker.solutions.kpi.persistence.repository.UserRepository;
import com.netcracker.solutions.kpi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by dmch0716 on 05.08.2016.
 */
@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userService.getUserByUsername(username);
        if(null == user){
            throw new UsernameNotFoundException("No user present with username: "+username);
        }else{
            Set<Role> userRoles = user.getRoles();
            Set<GrantedAuthority> roles = new HashSet();
            for (Role role : userRoles){
                roles.add(new SimpleGrantedAuthority(role.getRoleName()));            }
            return new org.springframework.security.core.userdetails.User(user.getUsername(),
                    user.getPassword(), roles);
        }
    }
}