package com.revature.services; 

import com.revature.models.Event;
import com.revature.models.User;
import com.revature.repos.EventRepository;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

/** 
* EventAPIService Tester. 
* 
* @author Nicholas Recino
* @since Jun 13, 2021
* @version 1.0 
*/

public class EventAPIServiceTest {

    @InjectMocks
    EventAPIService sut;

    @Mock
    EventRepository mockEventRepository;




    @Before
    public void before() throws Exception {
        openMocks(this);
    }

    @After
    public void after() throws Exception {
        sut = null;
        mockEventRepository = null;
    }


    @Test
    public void testGetEventsWithValidLongAndLat() throws Exception {
        assertTrue(sut.getEvents(40.712, -74.006).size() > 0);

    }


    @Test
    public void testGetEventWithInvalidEvent() throws Exception {
        Event event = sut.getEvent("fgfd");
        assertNull(event.getEventURL());
    }

    @Test
    public void testGetEventWithValidEvent() throws Exception {
        Event event = sut.getEvent("721901");
        System.out.println(event);
        assertEquals(721901,event.getEventID());
    }


    @Test
    public void testGetUserEventsWithNullUser() throws Exception {
        User user = new User();
        assertEquals(0,sut.getUserEvents(user).size());

    }

    @Test
    public void testGetUserEventsWithValidUser() throws Exception {
        User user = new User();
        sut.getUserEvents(new User(1, "password", "fname", "email@gmail.com"));
        verify(mockEventRepository, times(1)).getEventByUserId(1);
    }

    @Test
    public void testSaveEvent() throws Exception {
        Event event = new Event();
        sut.saveEvent(event);
        verify(mockEventRepository, times(1)).save(event);

    }

}



