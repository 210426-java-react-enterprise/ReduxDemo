package com.revature.services;

import com.revature.models.SMS;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Component;




@Component
public class SMSService {
    private static final String ACCOUNT_SID;
    private static final String AUTH_TOKEN;
    private static final String FROM_NUMBER;



    static {
        ACCOUNT_SID = System.getenv("ACCOUNT_SID");
        AUTH_TOKEN = System.getenv("AUTH_TOKEN");
        FROM_NUMBER = System.getenv("FROM_NUMBER");
    }



    public  void send(SMS sms) {
        Twilio.init(ACCOUNT_SID,AUTH_TOKEN);
        Message.creator(new PhoneNumber(sms.getTo()), new PhoneNumber(FROM_NUMBER),sms.getMessage()).create();
    }

}
