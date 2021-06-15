package com.revature.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.dtos.CityStateLocationDTO;
import com.revature.dtos.CoordinatesPair;
import com.revature.dtos.EventDTO;
import com.revature.exceptions.InvalidRequestException;
import com.revature.models.Event;
import com.revature.models.User;
import com.revature.repos.EventRepository;
import com.revature.security.JwtUtility;
import com.revature.services.EventAPIService;
import com.revature.services.LocationService;
import com.revature.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventRepository eventRepository;
    private final EventAPIService eventAPIService;
    private final LocationService locationService;
    private final JwtUtility jwtUtility;
    private final UserService userService;
    SimpleDateFormat jsFormat;

   @Autowired
    public EventController(EventRepository eventRepository, EventAPIService eventAPIService, LocationService locationService, JwtUtility utility, UserService userService)
    {
        this.eventRepository = eventRepository;
        this.eventAPIService = eventAPIService;
        this.locationService = locationService;
        this.jwtUtility = utility;
        this.userService = userService;
        jsFormat = new SimpleDateFormat(("EE MMM d y H:m:s 'GMT'Z (zz)"));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Event> getAllEvents()
    {
        return eventRepository.findAll();
    }

    @RequestMapping("/hometown")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getEventsAtHometown(HttpServletRequest req, HttpServletResponse res)throws IOException{
        String jwtHeader = req.getHeader("Authorization");
       String username = jwtUtility.getUserNameFromJwtToken(jwtHeader);
        ObjectMapper mapper = new ObjectMapper();
        try{
            User user = userService.getUserByUsername(username);
            CoordinatesPair<Double,Double> loc = locationService.getLatLonOfACity(user.getCity(),user.getState());
            String jsonRet = mapper.writeValueAsString(eventAPIService.getEvents(loc.getLatitude(),loc.getLongitude()));
            return ResponseEntity.accepted().body(jsonRet);
        }catch(InvalidRequestException | ParseException e){
            return ResponseEntity.badRequest().body(mapper.writeValueAsString(e));
        }
    }

    @RequestMapping("/location")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getEventsAtLocation(@RequestBody CityStateLocationDTO locationDTO) throws IOException, ParseException {
        CoordinatesPair<Double,Double> loc = locationService.getLatLonOfACity(locationDTO.getCity(),locationDTO.getState());
        ObjectMapper mapper = new ObjectMapper();
        String jsonRet = mapper.writeValueAsString(eventAPIService.getEvents(loc.getLatitude(),loc.getLongitude()));
        return ResponseEntity.accepted().body(jsonRet);
    }

    @RequestMapping("/id")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getSpecificEvent(@RequestBody String eventId) throws IOException, ParseException {
        ObjectMapper mapper = new ObjectMapper();
        return ResponseEntity.accepted().body(mapper.writeValueAsString(eventAPIService.getEvent(eventId)));
    }

    @RequestMapping("/user")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getUsersSavedEvents(HttpServletRequest req, HttpServletResponse res) throws JsonProcessingException {
        String jwtHeader = req.getHeader("Authorization");
        String username = jwtUtility.getUserNameFromJwtToken(jwtHeader);
        ObjectMapper mapper = new ObjectMapper();
        try{
            User user = userService.getUserByUsername(username);
            String jsonRet = mapper.writeValueAsString(eventAPIService.getUserEvents(user));
            return ResponseEntity.accepted().body(jsonRet);
        }catch(InvalidRequestException | JsonProcessingException e){
            return ResponseEntity.badRequest().body(mapper.writeValueAsString(e));
        }
    }

    @RequestMapping("/save")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Event> saveEvent(@RequestBody EventDTO event,HttpServletRequest req)throws ParseException{

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

       Event eventToSave = new Event();
       eventToSave.setEventURL(event.getEventUrl());
       eventToSave.setUserID(userService.getUserByUsername(userDetails.getUsername()).getId());
       long date = jsFormat.parse(event.getEventDate()).getTime();
       eventToSave.setEventDate(new Date(date));

        eventToSave.setEventTitle(event.getEventTitle());
        eventAPIService.saveEvent(eventToSave);

       return ResponseEntity.accepted().body(eventToSave);

    }

}
