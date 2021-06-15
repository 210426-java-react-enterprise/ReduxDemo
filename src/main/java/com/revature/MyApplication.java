package com.revature;


import com.revature.models.Mail;
import com.revature.models.SMS;
import com.revature.models.User;
import com.revature.services.MailServiceImpl;
import com.revature.services.SMSService;
import com.revature.services.UserService;
import com.revature.util.LoggerConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.List;

@SpringBootApplication
@EnableScheduling
public class MyApplication implements ApplicationRunner {

    @Autowired
    MailServiceImpl mailService;

    @Autowired
    UserService userService;

    public static void main(String[] args) {

        SpringApplication.run(MyApplication.class, args);
    }



    @Override
    //@Scheduled(cron = "0 30 9 ? * MON") TODO: commented for now
    public void run(ApplicationArguments args) throws Exception {
        LoggerConfiguration.configureLog4J();
        Mail mail = new Mail();

        List<User> subscriptionList = userService.getAllUsers();
        for (User subscribedUser : subscriptionList) {
            System.out.println(subscribedUser.getWantsWeeklyUpdates());
            try {
                if (subscribedUser.getWantsWeeklyUpdates() == null) {
                    continue;
                }else if(subscribedUser.getWantsWeeklyUpdates())
                    mail.setMailFrom("AlphaCast");
                    mail.setMailTo(subscribedUser.getEmail());
                    mail.setMailSubject("AlphaCast - Weather Update");
                    //                mail.setMailContent(emailInfo.getEmailContent());
                    //the block of code below is for my customEmail template
                    //TODO: troubleshoot why spring can not locate template, uncomment after fix
//                    Map<String, Object> model = new HashMap<>();
//                    model.put("name", subscribedUser.getFirstName());
//                    model.put("location", subscribedUser.getCity());
//                    model.put("sign", "Yours weatherly,\n" +
//                            "-The AlphaCast Team");
//                    mail.setProps(model);

                mail.setMailContent("Hi " + subscribedUser.getFirstName() +" !\n" +
                            "\tYour weekly forecast report for "+subscribedUser.getCity()+" is now available on your AlphaCast account !\n" +
                            "-AC Team\n" +
                            "[Insert WebSiteLink]");
                /** Comment out before presentations **/
//                    try {
//                        mailService.sendEmail(mail);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }


}