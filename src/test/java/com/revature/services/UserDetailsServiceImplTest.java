package com.revature.services; 

import com.fasterxml.jackson.annotation.JacksonInject;
import com.revature.models.Role;
import com.revature.models.User;
import com.revature.repos.UserRepository;
import com.revature.security.UserDetailsImpl;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.junit.Assert.*;

/** 
* UserDetailsServiceImpl Tester. 
* 
* @author Nicholas Recino
* @since Jun 13, 2021
* @version 1.0 
*/ 
public class UserDetailsServiceImplTest {

    @InjectMocks
    UserDetailsServiceImpl sut;

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
public void testLoadUserByUsername() throws Exception {
    User expectedUser = new User();
    expectedUser.setUsername("Username");
    expectedUser.setId(1);
    expectedUser.setEmail("test@gmail.com");
    expectedUser.setPassword("password");
    expectedUser.setRoles(Role.ADMIN);


    when(mockUserRepository.findUserByUsername(any())).thenReturn(java.util.Optional.of(expectedUser));

    UserDetails actualUser = sut.loadUserByUsername("Username");

    assertEquals(actualUser.getUsername(),expectedUser.getUsername());
    assertEquals(actualUser.getPassword(),expectedUser.getPassword());
}
    @Test(expected = UsernameNotFoundException.class)
    public void testLoadUserByUsernameNoUserFound() throws Exception {


        when(mockUserRepository.findUserByUsername(any())).thenThrow(new UsernameNotFoundException("User Not Found with username: "));

        UserDetails actualUser = sut.loadUserByUsername("Username");


    }


} 
