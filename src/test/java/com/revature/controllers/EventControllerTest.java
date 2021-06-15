package com.revature.controllers;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.dtos.CityStateLocationDTO;
import com.revature.dtos.EventDTO;
import com.revature.models.Event;
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import java.sql.Date;
import java.text.SimpleDateFormat;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class EventControllerTest {

    private MockMvc mockMvc;
    private WebApplicationContext webContext;
    private TokenGenerator tokenGenerator;
    SimpleDateFormat jsFormat = new SimpleDateFormat(("EE MMM d y H:m:s 'GMT'Z (zz)"));


    @Autowired
    public EventControllerTest(WebApplicationContext webContext, TokenGenerator tokenGenerator) {
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
     void test_getEventsInHomeTownValidCityAndState() throws Exception{
        User mockAdminUser = new User();
        mockAdminUser.setId(1);
        mockAdminUser.setUsername("seantaba");
        mockAdminUser.setPassword("password");
        mockAdminUser.setRoles(Role.ADMIN);
        String token = tokenGenerator.createJwt(mockAdminUser);

        this.mockMvc.perform(get("/events/hometown").header("Authorization", token))
                .andDo(print())
                .andExpect(content().contentType(MediaType.TEXT_PLAIN + ";charset=ISO8859-1"));

    }

    @Test
     void test_getEventsBasedOnLocation() throws Exception{

        CityStateLocationDTO mockCity = new CityStateLocationDTO();
        mockCity.setCity("Raleigh");
        mockCity.setState("North Carolina");

        this.mockMvc.perform(post("/events/location").content(asJsonString(mockCity)).contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));

    }

    @Test
    void test_getEventsBasedOnId() throws Exception{

        String eventId = "5295569";
        this.mockMvc.perform(post("/events/id").content(asJsonString(eventId)).contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));

    }

    @Test
    void test_getEventsByUsername() throws Exception{
        User mockAdminUser = new User();
        mockAdminUser.setId(1);
        mockAdminUser.setUsername("seantaba");
        mockAdminUser.setPassword("password");
        mockAdminUser.setRoles(Role.ADMIN);
        String token = tokenGenerator.createJwt(mockAdminUser);

        Event mockEvent = new Event();
        mockEvent.setEventURL("https://seatgeek.com/phish-tickets/eugene-oregon-matthew-knight-arena-2021-07-13-3-30-am/concert/5295569");
        mockEvent.setEventTitle("testTitle");
        this.mockMvc.perform(get("/events/user").header("Authorization", token))
                .andDo(print())
                .andExpect(content().contentType(MediaType.TEXT_PLAIN + ";charset=ISO8859-1"));

    }
    @Test
    @WithUserDetails("seantaba")
    void testSaveEvent() throws Exception {
        User mockAdminUser2 = new User();
        mockAdminUser2.setId(1);
        mockAdminUser2.setUsername("seantaba");
        mockAdminUser2.setPassword("password");
        mockAdminUser2.setRoles(Role.ADMIN);
        String token = tokenGenerator.createJwt(mockAdminUser2);
        this.mockMvc.perform(post("/events/save").header("Authorization", token)).andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithUserDetails("seantaba")
    void test_saveEventByUsername() throws Exception{
        User mockAdminUser2 = new User();
        mockAdminUser2.setId(1);
        mockAdminUser2.setUsername("seantaba");
        mockAdminUser2.setPassword("password");
        mockAdminUser2.setRoles(Role.ADMIN);
        String token = tokenGenerator.createJwt(mockAdminUser2);
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(mockAdminUser2.getUsername(), mockAdminUser2.getPassword());

        // Authenticate the user
//        Authentication authentication = Mockito.mock(Authentication.class);
//// Mockito.whens() for your authorization object
//        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
//        SecurityContextHolder.getContext();
//        UserDetails mockPrincipal = Mockito.mock(UserDetails.class);
//        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
//        Mockito.when(authentication.getPrincipal()).thenReturn(mockPrincipal);
//        Mockito.when(mockPrincipal.getUsername()).thenReturn("seantaba");
//        Mockito.when(mockPrincipal.getUsername()).thenReturn("password");



//        SecurityContextHolder.setContext(securityContext);

        Event mockEvent = new Event();
        mockEvent.setEventURL("https://seatgeek.com/phish-tickets/eugene-oregon-matthew-knight-arena-2021-07-13-3-30-am/concert/5295569");
        mockEvent.setEventTitle("testTitle");
        mockEvent.setEventDate(new Date(123230400));
        mockEvent.setUserID(1);
        mockEvent.setEventID(100000);
        EventDTO mockDTO = new EventDTO();
        mockDTO.setEventDate("Sun Jun 13 2021 19:00:00 GMT-0400 (Eastern Daylight Time)");
        mockDTO.setEventTitle("testTitle");
        mockDTO.setEventUrl("https://seatgeek.com/phish-tickets/eugene-oregon-matthew-knight-arena-2021-07-13-3-30-am/concert/5295569");
        mockDTO.setUserId(1);
        mockDTO.setToken(token);
        this.mockMvc.perform(post("/events/save").content(new ObjectMapper().writeValueAsString(mockDTO)).contentType(MediaType.APPLICATION_JSON_VALUE).header("Authorization", token))
                .andDo(print()).andExpect(status().is2xxSuccessful());

    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testGetAllEvents() throws Exception{
        User mockAdminUser = new User();
        mockAdminUser.setId(1);
        mockAdminUser.setUsername("seantaba");
        mockAdminUser.setPassword("password");
        mockAdminUser.setRoles(Role.ADMIN);
        String token = tokenGenerator.createJwt(mockAdminUser);

        Event mockEvent = new Event();
        mockEvent.setEventURL("https://seatgeek.com/phish-tickets/eugene-oregon-matthew-knight-arena-2021-07-13-3-30-am/concert/5295569");
        mockEvent.setEventTitle("testTitle");
        this.mockMvc.perform(get("/events").header("Authorization", token))
                .andExpect(status().is2xxSuccessful());

    }

    @Test
    void test_getEventsByInvalidUsername() throws Exception{
        User mockAdminUser = new User();
        mockAdminUser.setId(1);
        mockAdminUser.setUsername("");
        mockAdminUser.setPassword("password");
        mockAdminUser.setRoles(Role.ADMIN);
        String token = tokenGenerator.createJwt(mockAdminUser);

        Event mockEvent = new Event();
        mockEvent.setEventURL("https://seatgeek.com/phish-tickets/eugene-oregon-matthew-knight-arena-2021-07-13-3-30-am/concert/5295569");
        mockEvent.setEventTitle("testTitle");
        this.mockMvc.perform(get("/events/user").header("Authorization", token))
                .andDo(print())
                .andExpect(content().contentType(MediaType.TEXT_PLAIN + ";charset=ISO8859-1"));

    }




}
