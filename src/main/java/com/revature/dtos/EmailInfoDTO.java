package com.revature.dtos;

public class EmailInfoDTO {
    private String userEmail;
    private Object emailContent;
    private String usersFirstName;
    private String usersLocation;
    private String mailContent;

    public EmailInfoDTO() {
        super();
    }

    public void setUsersFirstName(String usersFirstName) {
        this.usersFirstName = usersFirstName;
    }

    public void setUsersLocation(String usersLocation) {
        this.usersLocation = usersLocation;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Object getEmailContent() {
        return emailContent;
    }

    public void setEmailContent(Object emailContent) {
        this.emailContent = emailContent;
    }

    public Object getUsersFirstName() {
        return usersFirstName;
    }

    public Object getUsersLocation() {
        return usersLocation;
    }

    public String getMailContent() {
        return mailContent;
    }

    public void setMailContent(String mailContent) {
        this.mailContent = mailContent;
    }
}
