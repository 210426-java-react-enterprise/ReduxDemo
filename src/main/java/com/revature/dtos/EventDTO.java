package com.revature.dtos;

public class EventDTO {

    private  int userId;
    private  String eventUrl;
    private  String eventDate;
    private  String eventTitle;
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public EventDTO(){
        super();
    }

    public EventDTO(int userId, String eventUrl, String eventDate, String eventTitle) {
        this.userId = userId;
        this.eventUrl = eventUrl;
        this.eventDate = eventDate;
        this.eventTitle = eventTitle;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEventUrl() {
        return eventUrl;
    }

    public void setEventUrl(String eventUrl) {
        this.eventUrl = eventUrl;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    @Override
    public String toString() {
        return "EventDTO{" +
                "userId=" + userId +
                ", eventUrl='" + eventUrl + '\'' +
                ", eventDate='" + eventDate + '\'' +
                ", eventTitle='" + eventTitle + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
