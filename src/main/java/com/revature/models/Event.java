package com.revature.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;


@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private int eventID;

    @NotNull
    @Column(nullable = false, name = "user_id")
    private int userId;

    @NotNull
    @Column(name = "event_url",nullable = false)
    private String eventURL;

    @Column(name ="event_date",nullable = false)
    private Date eventDate;

    @Column(name = "event_title", nullable = false)
    private String eventTitle;


    public Event() {

    }

    public Event(int eventId, int userId, String eventURL, Date eventDate, String eventTitle) {
        this.eventID = eventId;
        this.userId = userId;
        this.eventURL = eventURL;
        this.eventDate = eventDate;
        this.eventTitle = eventTitle;
    }



    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    public int getUserID() {
        return userId;
    }

    public void setUserID(int userID) {
        this.userId = userID;
    }

    public String getEventURL() {
        return eventURL;
    }

    public void setEventURL(String eventURL) {
        this.eventURL = eventURL;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
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
        return "Event{" +
                "event_id=" + eventID +
                ", user_id=" + userId +
                ", event_url='" + eventURL + '\'' +
                ", event_date=" + eventDate +
                ", event_title='" + eventTitle + '\'' +
                '}';
    }
}
