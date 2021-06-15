package com.revature.services; 

import com.revature.models.Mail;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.internet.MimeMessage;

import java.util.HashMap;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

/** 
* MailServiceImpl Tester. 
* 
* @author Nicholas Recino
* @since Jun 13, 2021
* @version 1.0 
*/ 
public class MailServiceImplTest {

    @InjectMocks
    MailServiceImpl sut;

    @Mock
    JavaMailSender mockMailSender;

    @Mock
    SpringTemplateEngine mockTemplateEngine;

@Before
public void before() throws Exception {
    openMocks(this);
} 

@After
public void after() throws Exception {
    sut = null;
    mockMailSender = null;
    mockTemplateEngine = null;
} 


@Test
public void testSendEmail() throws Exception {
    Mail mail = new Mail();
    mail.setMailFrom("test@gmail.com");
    mail.setMailTo("test2@gmail.com");
    mail.setMailSubject("Subject");
    mail.setMailContent("Content");
    mail.setProps(new HashMap<>());
    MimeMessage mockedMessage = mock(MimeMessage.class);
    when(mockMailSender.createMimeMessage()).thenReturn(mockedMessage);
    sut.sendEmail(mail);
    verify(mockMailSender).send(any(MimeMessage.class));
} 


} 
