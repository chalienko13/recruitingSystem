package com.netcracker.solutions.kpi.controller.student;

import com.netcracker.solutions.kpi.controller.auth.PasswordEncoderGeneratorService;
import com.netcracker.solutions.kpi.persistence.dto.MessageDto;
import com.netcracker.solutions.kpi.persistence.dto.UserDto;
import com.netcracker.solutions.kpi.persistence.model.EmailTemplate;
import com.netcracker.solutions.kpi.persistence.model.Role;
import com.netcracker.solutions.kpi.persistence.model.enums.EmailTemplateEnum;
import com.netcracker.solutions.kpi.persistence.model.enums.RoleEnum;
import com.netcracker.solutions.kpi.persistence.model.User;
import com.netcracker.solutions.kpi.service.EmailTemplateService;
import com.netcracker.solutions.kpi.service.RoleService;
import com.netcracker.solutions.kpi.service.UserService;
import com.netcracker.solutions.kpi.service.util.SenderService;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

@RestController
@RequestMapping(value = "/registrationStudent")
public class RegistrationController {

    private static Logger log = LoggerFactory.getLogger(RegistrationController.class.getName());
    private static final String USER_EXIST = "User with this email already exist";
    private static final String TOKEN_EXPIRED = "User token expired";

    @Autowired
    private UserService userService;// = ServiceFactory.getUserService();
    @Autowired
    private RoleService roleService;// = ServiceFactory.getRoleService();
    @Autowired
    private EmailTemplateService emailTemplateService;// = ServiceFactory.getEmailTemplateService();
    @Autowired
    private SenderService senderService;// = SenderServiceImpl.getInstance();
    @Autowired
    private PasswordEncoderGeneratorService passwordEncoderGeneratorService;// = PasswordEncoderGeneratorService.getInstance();


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
            String token = RandomStringUtils.randomAlphabetic(50);
            com.netcracker.solutions.kpi.persistence.model.User user = new User(userDto.getEmail(),
                    userDto.getFirstName(),
                    userDto.getSecondName(),
                    userDto.getLastName(),
                    passwordEncoderGeneratorService.encode(userDto.getPassword()),
                    false,
                    new Timestamp(System.currentTimeMillis()),
                    token);
            log.trace("Inserting user with email - {} in data base", userDto.getEmail());
            userService.insertUser(user, new ArrayList<>(roles));

            String url = String.format("%s://%s:%d/frontend/index.html#/registrationStudent/%s", request.getScheme(), request.getServerName(), request.getServerPort(), token);

            EmailTemplate emailTemplate = emailTemplateService.getById(EmailTemplateEnum.STUDENT_REGISTRATION.getId());

            String template = emailTemplateService.showTemplateParams(emailTemplate.getText(), user);

            String text = template + "\n" + url;

            String subject = emailTemplate.getTitle();

            log.info("Lending email");
            senderService.send(user.getEmail(), subject, text);

            return ResponseEntity.ok(new UserDto(user.getEmail(), user.getFirstName()));
        }
    }

    @RequestMapping(value = "/{token}", method = RequestMethod.GET)
    public ResponseEntity registrationConfirm(@PathVariable("token") String token) {
        log.info("Looking user with token - {}", token);
        com.netcracker.solutions.kpi.persistence.model.User user = userService.getUserByToken(token);
        if (null == user) {
            log.info("Token expired");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageDto(TOKEN_EXPIRED));
        } else {
            log.info("Make user with email - {} active", user.getEmail());
            user.setActive(true);
            userService.updateUser(user);
            userService.deleteToken(user.getId());
        }
        return ResponseEntity.ok(null);
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
