package com.netcracker.solutions.kpi.controller.auth;


import com.netcracker.solutions.kpi.persistence.dto.*;
import com.netcracker.solutions.kpi.persistence.model.User;
import com.netcracker.solutions.kpi.service.*;
import com.netcracker.solutions.kpi.util.TokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ForgotPassword {
    private static final String SESSION_ERROR = "Link session has been expired";
    private static final String USER_NOT_FOUND = "User with this email not found";
    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoderGeneratorService;

    @RequestMapping(value = "/recoverPassword", method = RequestMethod.POST)
    public ResponseEntity<UserDto> sendRecoverURL(@RequestBody UserDto userDto) {
        emailService.sendPasswordRecovery(userDto.getEmail());
        userDto.setFirstName("user");
        return ResponseEntity.ok(userDto);
    }

    @RequestMapping(value = "/recoverPassword", method = RequestMethod.GET)
    public ResponseEntity doRecover(@RequestParam("token") String token, HttpServletRequest request) {
        try {
            String email = tokenUtil.extractEmail(token);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Location", "/frontend/index.html#/recoverPasswordPage");
            request.getSession().setAttribute("email", email);
            return ResponseEntity.ok().headers(httpHeaders).body(null);
        } catch (ExpiredJwtException e) {
            return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body(new MessageDto(SESSION_ERROR));
        }
    }

    @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
    public ResponseEntity doChange(@RequestBody PasswordChangeDto passwordChangeDto, HttpServletRequest request) {
        String email = (String) request.getSession().getAttribute("email");
        if (null == email) {
            return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body(new MessageDto(SESSION_ERROR));
        }
        User user = userService.getUserByUsername(email);
        if (null == user) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageDto(USER_NOT_FOUND));
        } else {
            user.setPassword(passwordEncoderGeneratorService.encode(passwordChangeDto.getPassword()));
            userService.updateUser(user);
            return ResponseEntity.ok(new UserDto(user.getEmail(), user.getFirstName()));
        }
    }
}
