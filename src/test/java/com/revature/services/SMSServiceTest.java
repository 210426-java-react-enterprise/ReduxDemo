package com.revature.services; 

import com.revature.models.SMS;
import com.twilio.exception.ApiException;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.junit.Assert.*;

/** 
* SMSService Tester. 
* 
* @author Nicholas Recino
* @since Jun 13, 2021
* @version 1.0 
*/ 
public class SMSServiceTest {

    SMSService sut;

@Before
public void before() throws Exception {
    sut = new SMSService();
} 

@After
public void after() throws Exception {
    sut = null;
} 


@Test
public void testConfig() throws Exception {
    assertNotNull(sut);
}
    @Test(expected = ApiException.class)
    public void testSend() throws Exception {
        SMS sms = mock(SMS.class);
        sut.send(sms);
    }





} 
