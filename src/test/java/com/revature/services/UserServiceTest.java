package com.revature.services;

import com.revature.exceptions.AuthenticationException;
import com.revature.exceptions.EmailUnavailibleException;
import com.revature.exceptions.InvalidRequestException;
import com.revature.exceptions.UsernameUnavailibleException;
import com.revature.models.User;
import com.revature.repos.UserRepository;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.junit.Assert.*;


/**
 * UserService Tester.
 *
 * @author Nicholas Recino
 * @version 1.0
 * @since Jun 13, 2021
 */
public class UserServiceTest {

    @InjectMocks
    UserService sut;

    @Mock
    UserRepository mockUserRepository;

    @Before
    public void before() throws Exception {
        openMocks(this);
    }

    @After
    public void after() throws Exception {
        sut = null;
        mockUserRepository = null;
    }


    @Test
    public void testRegisterWithValidUser() throws Exception {
        User expectedUser = new User();
        expectedUser.setUsername("testusername");
        expectedUser.setPassword("testpassword");
        expectedUser.setEmail("testemail@email.com");
        expectedUser.setFirstName("firstName");
        expectedUser.setLastName("lastName");

        when(mockUserRepository.save(any())).thenReturn(expectedUser);
        when(mockUserRepository.existsByUsername(any())).thenReturn(Boolean.FALSE);
        when(mockUserRepository.existsByEmail(any())).thenReturn(Boolean.FALSE);

        assertEquals(expectedUser,sut.register(expectedUser));


    }

    @Test(expected = EmailUnavailibleException.class)
    public void testRegisterWithTakenEmail() throws Exception {
        User expectedUser = new User();
        expectedUser.setUsername("testusername");
        expectedUser.setPassword("testpassword");
        expectedUser.setEmail("testemail@email.com");
        expectedUser.setFirstName("firstName");
        expectedUser.setLastName("lastName");

        when(mockUserRepository.save(any())).thenReturn(expectedUser);
        when(mockUserRepository.existsByUsername(any())).thenReturn(Boolean.FALSE);
        when(mockUserRepository.existsByEmail(any())).thenReturn(Boolean.TRUE);
        sut.register(expectedUser);
    }

    @Test(expected = UsernameUnavailibleException.class)
    public void testRegisterWithTakenUsername() throws Exception {
        User expectedUser = new User();
        expectedUser.setUsername("testusername");
        expectedUser.setPassword("testpassword");
        expectedUser.setEmail("testemail@email.com");
        expectedUser.setFirstName("firstName");
        expectedUser.setLastName("lastName");

        when(mockUserRepository.save(any())).thenReturn(expectedUser);
        when(mockUserRepository.existsByUsername(any())).thenReturn(Boolean.TRUE);
        when(mockUserRepository.existsByEmail(any())).thenReturn(Boolean.FALSE);
        sut.register(expectedUser);
    }

    @Test(expected = InvalidRequestException.class)
    public void testRegisterWithInvalidUser() throws Exception {
        sut.register(null);
    }


    @Test
    public void testGetAllUsers() throws Exception {
        List<User> expectedList = new ArrayList<>();
        expectedList.add(new User());
        when(mockUserRepository.findAll()).thenReturn(expectedList);
        List<User> actualList = sut.getAllUsers();
        assertEquals(expectedList,actualList);
    }


    @Test
    public void testIsValidDefault() throws Exception {
        assertTrue(sut.isValid("test","fieldNotPresent"));
    }


    @Test(expected = InvalidRequestException.class)
    public void testIsUserInvalidUsername() throws Exception {
        User expectedUser = new User();
        expectedUser.setUsername("");
        expectedUser.setPassword("testpassword");
        expectedUser.setEmail("testemail@email.com");
        expectedUser.setFirstName("firstName");
        expectedUser.setLastName("lastName");

        sut.isUserValid(expectedUser);
    }

    @Test(expected = InvalidRequestException.class)
    public void testIsUserInvalidPassword() throws Exception {
        User expectedUser = new User();
        expectedUser.setUsername("testusername");
        expectedUser.setEmail("testemail@email.com");
        expectedUser.setFirstName("firstName");
        expectedUser.setLastName("lastName");

        sut.isUserValid(expectedUser);
    }

    @Test(expected = InvalidRequestException.class)
    public void testIsUserInvalidEmail() throws Exception {
        User expectedUser = new User();
        expectedUser.setUsername("testusername");
        expectedUser.setPassword("testpassword");
        expectedUser.setEmail("");
        expectedUser.setFirstName("firstName");
        expectedUser.setLastName("lastName");

        sut.isUserValid(expectedUser);
    }

    @Test(expected = InvalidRequestException.class)
    public void testIsUserInvalidFirstName() throws Exception {
        User expectedUser = new User();
        expectedUser.setUsername("testusername");
        expectedUser.setPassword("testpassword");
        expectedUser.setEmail("testemail");
        expectedUser.setFirstName("");
        expectedUser.setLastName("lastName");

        sut.isUserValid(expectedUser);
    }

    @Test(expected = InvalidRequestException.class)
    public void testIsUserInvalidLastName() throws Exception {
        User expectedUser = new User();
        expectedUser.setUsername("testusername");
        expectedUser.setPassword("testpassword");
        expectedUser.setEmail("testemail");
        expectedUser.setFirstName("firstName");
        expectedUser.setLastName("");

        sut.isUserValid(expectedUser);
    }


    @Test
    public void testAuthenticateValidUser() throws Exception {
        User expectedUser = new User();
        expectedUser.setUsername("username");
        expectedUser.setPassword("password");
        when(mockUserRepository.findUserByUsernameAndPassword(any(),any())).thenReturn(java.util.Optional.of(expectedUser));
        assertEquals(expectedUser,sut.authenticate("username","password"));
    }

    @Test(expected = AuthenticationException.class)
    public void testAuthenticateInValidUser() throws Exception {
        User expectedUser = new User();
        expectedUser.setUsername("username");
        expectedUser.setPassword("password");
        when(mockUserRepository.findUserByUsernameAndPassword(any(),any())).thenThrow(new AuthenticationException());
        sut.authenticate("username","password");
    }


    @Test
    public void testGetUserByUsername() throws Exception {
        User expectedUser = new User();
        expectedUser.setUsername("username");
        when(mockUserRepository.findUserByUsername(any())).thenReturn(java.util.Optional.of(expectedUser));
        assertEquals(expectedUser,sut.getUserByUsername("username"));
    }


} 
