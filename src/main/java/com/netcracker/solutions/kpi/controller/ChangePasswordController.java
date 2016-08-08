package com.netcracker.solutions.kpi.controller;

import com.netcracker.solutions.kpi.persistence.model.User;
import com.netcracker.solutions.kpi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChangePasswordController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;// = PasswordEncoderGeneratorService.getInstance();


    @RequestMapping(value = "changepassword", method = RequestMethod.POST)
    public ResponseEntity<String> changePassword(@RequestParam String oldPassword, @RequestParam String newPassword) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        User user = userService.getUserByUsername(name);
        if (passwordEncoder.matches(user.getPassword(),oldPassword)) {
            user.setPassword(passwordEncoder.encode(newPassword));
            userService.updateUser(user);
        }
        return ResponseEntity.ok(null);
    }
}
