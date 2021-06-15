package com.revature.controllers;

import com.revature.models.Role;
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
class ApiDocControllerTest {
    private MockMvc mockMvc;
    private WebApplicationContext webContext;
    private TokenGenerator tokenGenerator;

    @Autowired
    public ApiDocControllerTest(WebApplicationContext webContext, TokenGenerator tokenGenerator) {
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
    void redirectTest() throws Exception {
        User mockAdminUser = new User();
        mockAdminUser.setId(1);
        mockAdminUser.setUsername("seantaba");
        mockAdminUser.setPassword("password");
        mockAdminUser.setRoles(Role.ADMIN);
        String token = tokenGenerator.createJwt(mockAdminUser);
        this.mockMvc.perform(get("/api/docs").header("Authorization", token)).andExpect(status().is3xxRedirection());
    }
}
