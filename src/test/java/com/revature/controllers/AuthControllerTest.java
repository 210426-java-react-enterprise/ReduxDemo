package com.revature.controllers;



import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.config.WebSecurityConfig;
import com.revature.dtos.CityStateLocationDTO;
import com.revature.dtos.CredentialsDTO;
import com.revature.dtos.EventDTO;
import com.revature.dtos.UserDTO;
import com.revature.models.Event;
import com.revature.models.Role;
import com.revature.models.User;
import com.revature.security.TokenGenerator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.NestedServletException;


import java.security.Principal;
import java.sql.Date;
import java.text.SimpleDateFormat;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class AuthControllerTest {

    private MockMvc mockMvc;
    private WebApplicationContext webContext;
    private TokenGenerator tokenGenerator;
    SimpleDateFormat jsFormat = new SimpleDateFormat(("EE MMM d y H:m:s 'GMT'Z (zz)"));


    @Autowired
    public AuthControllerTest(WebApplicationContext webContext, TokenGenerator tokenGenerator) {
        this.webContext = webContext;
        this.tokenGenerator = tokenGenerator;
    }

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webContext).build();

    }

    @AfterEach
    public void breakDown() {
        this.mockMvc = null;
    }


    @Test
    void test_authenticate() throws Exception{
        User mockAdminUser = new User();
        mockAdminUser.setId(1);
        mockAdminUser.setUsername("seantaba");
        mockAdminUser.setPassword("password");
        mockAdminUser.setRoles(Role.ADMIN);
        String token = tokenGenerator.createJwt(mockAdminUser);
        CredentialsDTO loginDTO = new CredentialsDTO();
        ObjectMapper mapper = new ObjectMapper();
        loginDTO.setPassword("password");
        loginDTO.setUsername("seantaba");
        this.mockMvc.perform(post("/auth/login").content(mapper.writeValueAsString(loginDTO)).contentType((MediaType.APPLICATION_JSON_VALUE)))
                .andExpect(status().is2xxSuccessful());

    }

    @Test
    void testRegister() throws Exception {
        UserDTO expectedUser = new UserDTO();
        expectedUser.setUsername("test13340username");
        expectedUser.setPassword("test2password");
        expectedUser.setEmail("test19134email@email.com");
        expectedUser.setFirstName("firstName");
        expectedUser.setLastName("lastName");
        expectedUser.setCity("New York City");
        expectedUser.setState("New York");
        expectedUser.setWantsWeeklyUpdates(true);
        ObjectMapper mapper = new ObjectMapper();
        this.mockMvc.perform(post("/auth/register").content(mapper.writeValueAsString(expectedUser)).contentType((MediaType.APPLICATION_JSON_VALUE)))
                .andExpect(status().is2xxSuccessful());

        expectedUser = new UserDTO();
        expectedUser.setUsername("");
        expectedUser.setPassword("test2password");
        expectedUser.setEmail("test0email@email.com");
        expectedUser.setFirstName("firstName");
        expectedUser.setLastName("lastName");
        expectedUser.setCity("New York City");
        expectedUser.setState("New York");
        expectedUser.setWantsWeeklyUpdates(true);
        this.mockMvc.perform(post("/auth/register").content(mapper.writeValueAsString(expectedUser)).contentType((MediaType.APPLICATION_JSON_VALUE)))
                .andExpect(status().is4xxClientError());


        expectedUser.setUsername("seantaba");
        expectedUser.setPassword("test2password");
        expectedUser.setEmail("test9email@email.com");
        expectedUser.setFirstName("firstName");
        expectedUser.setLastName("lastName");
        expectedUser.setCity("New York City");
        expectedUser.setState("New York");
        expectedUser.setWantsWeeklyUpdates(true);
        this.mockMvc.perform(post("/auth/register").content(mapper.writeValueAsString(expectedUser)).contentType((MediaType.APPLICATION_JSON_VALUE)))
                .andExpect(status().is4xxClientError());


    }

    @Test
     void testRegisterWithTakenEmail() throws Exception {
        UserDTO expectedUser = new UserDTO();
        expectedUser.setUsername("test7username");
        expectedUser.setPassword("test3password");
        expectedUser.setEmail("sean.taba@gmail.com");
        expectedUser.setFirstName("firstName");
        expectedUser.setLastName("lastName");
        expectedUser.setCity("New York City");
        expectedUser.setState("New York");
        expectedUser.setWantsWeeklyUpdates(true);
        ObjectMapper mapper = new ObjectMapper();
        this.mockMvc.perform(post("/auth/register").content(mapper.writeValueAsString(expectedUser)).contentType((MediaType.APPLICATION_JSON_VALUE)))
                .andExpect(status().is4xxClientError());


    }

}