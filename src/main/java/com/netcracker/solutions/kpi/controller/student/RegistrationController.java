package com.netcracker.solutions.kpi.controller.student;

import com.netcracker.solutions.kpi.persistence.dto.MessageDto;
import com.netcracker.solutions.kpi.persistence.dto.UserDto;
import com.netcracker.solutions.kpi.persistence.model.Role;
import com.netcracker.solutions.kpi.persistence.model.User;
import com.netcracker.solutions.kpi.persistence.model.enums.RoleEnum;
import com.netcracker.solutions.kpi.service.RoleService;
import com.netcracker.solutions.kpi.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HandlerMapping;

import javax.mail.MessagingException;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

@RestController
@RequestMapping(value = "/registrationStudent")
public class RegistrationController {

    private static final String USER_EXIST = "User with this email already exist";
    private static final String TOKEN_EXPIRED = "User token expired";
    private static Logger log = LoggerFactory.getLogger(RegistrationController.class.getName());
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity registerNewStudent(@RequestBody UserDto userDto, HttpServletRequest request) throws MessagingException {
        log.info("Looking user with email - {}", userDto.getEmail());
        if (userService.isExist(userDto.getEmail())) {
            log.info("User with email - {} already exist", userDto.getEmail());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageDto(USER_EXIST));
        } else {
            Role role = roleService.getRoleByTitle(RoleEnum.valueOf(RoleEnum.ROLE_STUDENT));
            Set<Role> roles = new HashSet<>();
            roles.add(role);

            User user = userService.createUser(userDto.getEmail(),
                    userDto.getFirstName(),
                    userDto.getSecondName(),
                    userDto.getLastName(),
                    userDto.getPassword(),
                    roles,
                    false);//isActive
            return ResponseEntity.ok(new UserDto(user.getEmail(), user.getFirstName()));
        }
    }

    @RequestMapping(value = "/{token}", method = RequestMethod.GET)
    public ResponseEntity registrationConfirm(@PathVariable("token") String token, HttpServletRequest request) {
        String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        String bestMatchTokenPattern = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        AntPathMatcher apm = new AntPathMatcher();
        token = apm.extractPathWithinPattern(bestMatchTokenPattern, path);
        if (userService.confirmByToken(token) == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageDto(TOKEN_EXPIRED));
        } else {
            return ResponseEntity.ok(null);
        }
    }

    @RequestMapping(value = "domainVerify", method = RequestMethod.GET)
    public boolean domainVerify(@RequestParam String email) {
        String[] splitEmail = email.split("@");
        String domain = splitEmail[1];
        try {
            if (doLookup(domain) != 0) {
                log.info("Mail server exist");
                return true;
            } else {
                log.info("Mail server not exist");
                return false;
            }
        } catch (Exception e) {
            log.info("DNS name not found");
            return false;
        }
    }

    private int doLookup(String hostName) throws NamingException {
        Hashtable<String, String> env = new Hashtable<>();
        env.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
        DirContext initialDirContext = new InitialDirContext(env);
        Attributes attrs = initialDirContext.getAttributes(hostName, new String[]{"MX"});
        Attribute attr = attrs.get("MX");
        if (attr == null) return (0);
        return (attr.size());
    }

}