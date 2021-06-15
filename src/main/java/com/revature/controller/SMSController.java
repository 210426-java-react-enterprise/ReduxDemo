package com.revature.controller;

import com.revature.models.SMS;
import com.revature.services.SMSService;
import com.twilio.exception.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SMSController {
    @Autowired
    SMSService smsService;

    @Autowired
    private SimpMessagingTemplate webSocket;

    private final String TOPIC_DESTINATION = "/topic/sms";

    public void smsSubmit(SMS sms){
        try{
            smsService.send(sms);
        }catch(ApiException e){
            webSocket.convertAndSend(TOPIC_DESTINATION,getTimeStamp()+": SMS has been sent!: "+ e.getMessage());
            throw e;
        }
        webSocket.convertAndSend(TOPIC_DESTINATION,getTimeStamp()+" SMS has been sent!: "+sms.getTo());
    }
    private String getTimeStamp(){
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
    }
}
