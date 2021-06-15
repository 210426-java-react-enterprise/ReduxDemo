package com.revature.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.Event;
import com.revature.models.User;
import com.revature.repos.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Date;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
public class EventAPIService {

    private String clientId = "MjIyMDIzMzR8MTYyMzM2MzAzNC41MTgwMjA2";
    private String clientSecret = "c66dd9c8237fafc6723abc430068fef7563b040d1481779c7193c240b6dad6a0";
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
    private EventRepository eventRepository;

    @Autowired
    public EventAPIService(EventRepository eventRepository){
        this.eventRepository = eventRepository;
    }

    @SuppressWarnings("unchecked")
    public List<Event> getEvents(double lat, double lon) throws IOException, ParseException {
        List<Event> returnEvents = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        builder.append("https://api.seatgeek.com/2/events?per_page=50")
                .append(MessageFormat.format("&lat={0}&lon={1}&client_id={2}&client_secret={3}", lat, lon, clientId, clientSecret));
        URL urlRequest = new URL(builder.toString());
        HttpURLConnection connection = (HttpURLConnection) urlRequest.openConnection();

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            ObjectMapper mapper = new ObjectMapper();
            HashMap<String, Object> jsonMap = mapper.readValue(connection.getInputStream(), HashMap.class);
            LinkedHashMap<String, Object> totalAmount = (LinkedHashMap<String, Object>) jsonMap.get("meta");
            int pagesPresent = (Integer) totalAmount.get("total") / 50;
            pagesPresent = Math.min(pagesPresent, 3);
            for (int i = 1; i < pagesPresent; i++) {
                urlRequest = new URL(builder + MessageFormat.format("&page={0}", i));
                connection = (HttpURLConnection) urlRequest.openConnection();
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    jsonMap = mapper.readValue(connection.getInputStream(), HashMap.class);
                    List<Object> eventList = (ArrayList<Object>) jsonMap.get("events");
                    for (Object o : eventList) {
                        LinkedHashMap<String, Object> events = (LinkedHashMap<String, Object>) o;
                        Event event = new Event();
                        eventProcess(event, events);
                        returnEvents.add(event);
                    }
                }
            }
        }
        return returnEvents;
    }

    public Event getEvent(String eventId) throws IOException, ParseException {
        Event event = new Event();
        String builder = "https://api.seatgeek.com/2/events" +
                MessageFormat.format("/{0}?client_id={1}&client_secret={2}", eventId, clientId, clientSecret);
        URL urlRequest = new URL(builder);
        HttpURLConnection connection = (HttpURLConnection) urlRequest.openConnection();

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            ObjectMapper mapper = new ObjectMapper();
            LinkedHashMap<String, Object> events = mapper.readValue(connection.getInputStream(), LinkedHashMap.class);
            eventProcess(event, events);
        }
        return event;
    }

    public Set<Event> getUserEvents(User user){
        return eventRepository.getEventByUserId(user.getId());
    }

    public void saveEvent(Event event){
        eventRepository.save(event);
    }

    private void eventProcess(Event event, LinkedHashMap<String, Object> events) throws ParseException {
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        event.setEventDate(new Date(format.parse((String) events.get("datetime_utc")).getTime()));
        event.setEventTitle((String) events.get("title"));
        event.setEventURL((String) events.get("url"));
        event.setEventID((Integer) events.get("id"));
    }
}
