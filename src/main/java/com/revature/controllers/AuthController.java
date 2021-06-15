package com.revature.controllers;

import javax.validation.Valid;

import com.revature.dtos.CredentialsDTO;
import com.revature.dtos.UserDTO;
import com.revature.exceptions.EmailUnavailibleException;
import com.revature.exceptions.InvalidRequestException;
import com.revature.exceptions.UsernameUnavailibleException;

import com.revature.models.User;
import com.revature.security.JwtConfig;
import com.revature.security.TokenGenerator;
import com.revature.services.MailServiceImpl;
import com.revature.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import static org.springframework.http.MediaType.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/auth")
public class AuthController {

   private UserService userService;
   private TokenGenerator tokenGenerator;
   private JwtConfig jwtConfig;

    @Autowired
    public AuthController(UserService userService, TokenGenerator tokenGenerator, JwtConfig jwtConfig, MailServiceImpl mailService) {
        this.userService = userService;
        this.tokenGenerator = tokenGenerator;
        this.jwtConfig = jwtConfig;
        this.mailService = mailService;

    }

    @RequestMapping("/login")
    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> authenticate(@RequestBody @Valid CredentialsDTO credentials, HttpServletResponse resp) {
        User user = userService.authenticate(credentials.getUsername(), credentials.getPassword());
        user.setAuthorizationLevel(user.getAuthorizationLevel());
        String jwt = tokenGenerator.createJwt(user);
        resp.setHeader(jwtConfig.getHeader(), jwt);
        user.setToken(jwt);
        return ResponseEntity.ok(user);
    }

    @RequestMapping("/register")
    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> registerUser(@RequestBody UserDTO signUpRequest, HttpServletResponse resp) {
        User registerUser = new User();
        registerUser.setUsername(signUpRequest.getUsername());
        registerUser.setPassword(signUpRequest.getPassword());
        registerUser.setEmail(signUpRequest.getEmail());
        registerUser.setFirstName(signUpRequest.getFirstName());
        registerUser.setLastName(signUpRequest.getLastName());
        registerUser.setCity(signUpRequest.getCity());
        registerUser.setState(signUpRequest.getState());
        registerUser.setAuthorizationLevel(2);
        registerUser.setWantsWeeklyUpdates(signUpRequest.getWantsWeeklyUpdates());
        try{
            userService.register(registerUser);
        }catch (UsernameUnavailibleException e){
            return ResponseEntity
                    .badRequest()
                    .body("Error username is taken");
        }
        catch (EmailUnavailibleException e){
            return ResponseEntity
                    .badRequest()
                    .body("Error email is taken");
        }catch (InvalidRequestException e){
            return ResponseEntity
                    .badRequest()
                    .body("Invalid information sent to API");
        }

        CredentialsDTO credentialsDTO = new CredentialsDTO();
        credentialsDTO.setUsername(registerUser.getUsername());
        credentialsDTO.setPassword(registerUser.getPassword());
        return ResponseEntity.ok(authenticate(credentialsDTO,resp));
        }


    }

