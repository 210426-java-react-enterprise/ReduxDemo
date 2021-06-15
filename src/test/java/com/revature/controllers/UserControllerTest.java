package com.revature.controllers;

import com.revature.models.User;
import com.revature.security.TokenGenerator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.awt.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class UserControllerTest
{
    private MockMvc mockMvc;
    private WebApplicationContext webContext;
    private TokenGenerator tokenGenerator;

    @Autowired
    public UserControllerTest(WebApplicationContext webContext,TokenGenerator tokenGenerator)
    {
        this.webContext = webContext;
        this.tokenGenerator = tokenGenerator;
    }

    @BeforeEach
    public void setup()
    {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webContext).build();
    }

    @AfterEach
    public void breakdown()
    {
        this.mockMvc = null;
    }

    @Test
    void test_getUserByEmail() throws Exception
    {
        User mockAdminUser = new User();
        mockAdminUser.setId(1);
        mockAdminUser.setUsername("seantaba");
        mockAdminUser.setEmail("sean.taba@revature.net");
        String token = tokenGenerator.createJwt(mockAdminUser);

        this.mockMvc.perform(get("/users?email={email}","sean.taba@revature.net").header("Authorization", token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$[0].email").value("sean.taba@revature.net"));
    }

    @Test
    void test_getUserByUsername() throws Exception
    {
        User mockAdminUser = new User();
        mockAdminUser.setId(1);
        mockAdminUser.setUsername("seantaba");
        mockAdminUser.setEmail("sean.taba@revature.net");
        String token = tokenGenerator.createJwt(mockAdminUser);

        this.mockMvc.perform(get("/users?username={username}","seantaba").header("Authorization", token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$[0].username").value("seantaba"));
    }


}