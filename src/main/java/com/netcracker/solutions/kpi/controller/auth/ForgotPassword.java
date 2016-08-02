package com.netcracker.solutions.kpi.controller.auth;


import com.netcracker.solutions.kpi.persistence.dto.MessageDto;
import com.netcracker.solutions.kpi.persistence.dto.PasswordChangeDto;
import com.netcracker.solutions.kpi.persistence.dto.UserDto;
import com.netcracker.solutions.kpi.service.EmailTemplateService;
import com.netcracker.solutions.kpi.service.UserService;
import com.netcracker.solutions.kpi.service.util.SenderService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
public class ForgotPassword {
    private static Logger log = LoggerFactory.getLogger(ForgotPassword.class.getName());

    private static final String secret = "verySecretStringForRecover";
    private static final String SESSION_ERROR = "Link session has been expired";
    private static final String USER_NOT_FOUND = "User with this email not found";
    private static final long expireTme = 60 * 1000 * 60;

    @Autowired
    private UserService userService;// = ServiceFactory.getUserService();
    @Autowired
    private SenderService senderService;// = SenderServiceImpl.getInstance();
    @Autowired
    private PasswordEncoderGeneratorService passwordEncoderGeneratorService;// = PasswordEncoderGeneratorService.getInstance();
    @Autowired
    private EmailTemplateService emailTemplateService;// = ServiceFactory.getEmailTemplateService();

    @RequestMapping(value = "/recoverPassword", method = RequestMethod.POST)
    public ResponseEntity<UserDto> sendRecoverURL(@RequestBody UserDto userDto, HttpServletRequest request) throws MessagingException {
        log.info("Looking user with email - {}", userDto.getEmail());
        User user = userService.getUserByUsername(userDto.getEmail());
        if (null == user) {
            log.info("User with email - {} not found", userDto.getEmail());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(userDto);
        } else {
            userDto.setFirstName(user.getFirstName());
            log.info("User with email - {} found", userDto.getEmail());
            String token = createToken(userDto.getEmail());
            String url = String.format("%s://%s:%d/frontend/index.html#/recoverPasswordPage/?token=%s",request.getScheme(),  request.getServerName(), request.getServerPort(), token);
            String text = url;
            String subject = "Recover your password";
            log.info("Letter sent on email - {}", user.getEmail());
            senderService.send(userDto.getEmail(), subject, text);
            return ResponseEntity.ok(userDto);
        }
    }


    @RequestMapping(value = "/recoverPassword", method = RequestMethod.GET)
    public ResponseEntity doRecover(@RequestParam("token") String token, HttpServletRequest request){
        log.info("Begin parse recover password token");
        String email = parseToken(token);
        HttpHeaders httpHeaders = new HttpHeaders();
        if (null == email){
            log.info("Token session has benn expired");
            return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body(new MessageDto(SESSION_ERROR));
        }else {
            log.info("Token parsed");
            httpHeaders.add("Location", "/frontend/index.html#/recoverPasswordPage");
            request.getSession().setAttribute("email", email);
            return ResponseEntity.ok().headers(httpHeaders).body(null);
        }
    }

    @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
    public ResponseEntity doChange(@RequestBody PasswordChangeDto passwordChangeDto, HttpServletRequest request){
        log.info("Looking user email");
        String email = (String) request.getSession().getAttribute("email");
        if (null == email){
            log.info("Session has been expired");
            return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body(new MessageDto(SESSION_ERROR));
        }
        log.info("User email found");
        User user = userService.getUserByUsername(email);
        if (null == user){
            log.info("User with email - {} not found", email);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageDto(USER_NOT_FOUND));
        }else {
            log.info("Change password for user - {}", email);
            user.setPassword(passwordEncoderGeneratorService.encode(passwordChangeDto.getPassword()));
            userService.updateUser(user);
            return ResponseEntity.ok(new UserDto(user.getEmail(), user.getFirstName()));
        }
    }

    private String createToken(String email) {
        log.info("Create recover token for - {}", email);
        return Jwts.builder()
                .setSubject(email)
                .signWith(SignatureAlgorithm.HS512, secret)
                .setExpiration(new Date(new Date().getTime() + expireTme))
                .compact();
    }

    private String parseToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
            log.info("Token - {} parsing success", token);
            return claims.getSubject();
        } catch (ExpiredJwtException e) {
            log.info("Token - {} parsing failed {}", token, e);
        }
        return null;
    }
}
