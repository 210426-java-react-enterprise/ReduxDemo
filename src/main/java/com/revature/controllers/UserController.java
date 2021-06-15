package com.revature.controllers;

import com.revature.dtos.EmailInfoDTO;
import com.revature.models.Mail;
import com.revature.models.User;
import com.revature.repos.UserRepository;
import com.revature.services.MailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@RequestMapping("users")
public class UserController
{

    private final UserRepository userRepository;

    private final MailServiceImpl mailService;

    @Autowired
    public UserController(UserRepository userRepository,MailServiceImpl mailService)
    {
        this.mailService = mailService;
        this.userRepository = userRepository;
    }

    //this will be hit only when weather change == truthy on the UI side
    @PostMapping(name="/sendWeatherUpdate",consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<EmailInfoDTO> sendWeatherUpdate (@RequestBody EmailInfoDTO emailInfo, HttpServletRequest req) {
        Mail mail = new Mail();
        if (req.getHeader("Authorization") != null) {
            mail.setMailFrom("AlphaCast");
            mail.setMailTo(emailInfo.getUserEmail());
            mail.setMailSubject("AlphaCast - Weather Update");
            mail.setMailContent(emailInfo.getMailContent());
            try {
                mailService.sendEmail(mail);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.ok(emailInfo);
            }
        }
        return ResponseEntity.ok(emailInfo);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAllUsers()
        {
            return userRepository.findAll();
        }

}
