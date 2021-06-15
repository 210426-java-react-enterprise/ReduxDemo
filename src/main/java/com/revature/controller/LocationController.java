package com.revature.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.Location;
import com.revature.repos.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/locations")
public class LocationController
{
    private final LocationRepository locationRepository;

    @Autowired
    public LocationController(LocationRepository locationRepository)
    {
        this.locationRepository = locationRepository;
    }

//    Use this format to secure methods to only be available to specific users, will check JWT permissions

//    @PreAuthorize("hasRole('BASIC_USER')")
    @RequestMapping("/coordinates")
    public String getCoordinates(@RequestParam String country, @RequestParam String state, @RequestParam String city)
    {
        ObjectMapper mapper = new ObjectMapper();
        try
        {
            return mapper.writeValueAsString(locationRepository.findCoordinates(country, state, city));
        } catch (JsonProcessingException e)
        {
            e.printStackTrace();
            return "";
        }
    }

    @RequestMapping()
    public Set<String> listCountries()
    {
        try
        {
            return locationToString(locationRepository.findAll(), Location.class.getMethod("getCountry"));
        } catch (NoSuchMethodException e)
        {
            e.printStackTrace();
            return new HashSet<>();
        }
    }

    @RequestMapping("/{country}")
    public Set<String> getCountries(@PathVariable String country)
    {
        try
        {
            return locationToString(locationRepository.findLocationByCountry(country), Location.class.getMethod("getState"));
        } catch (NoSuchMethodException e)
        {
            e.printStackTrace();
            return new HashSet<>();
        }
    }

    @RequestMapping(path = "/{country}/{state}")
    public Set<String> getCounties(@PathVariable String country, @PathVariable String state)
    {
        try
        {
            return locationToString(locationRepository.findLocationByCountryAndState(country,state), Location.class.getMethod("getCounty"));
        } catch (NoSuchMethodException e)
        {
            e.printStackTrace();
            return new HashSet<>();
        }
    }

    @RequestMapping(path = "/{country}/{state}/{county}")
    public Set<String> getCities(@PathVariable String country, @PathVariable String state, @PathVariable String county)
    {
        try
        {
            return locationToString(locationRepository.findLocationByCountryAndStateAndCounty(country,state,county), Location.class.getMethod("getCity"));
        } catch (NoSuchMethodException e)
        {
            e.printStackTrace();
            return new HashSet<>();
        }
    }

    @RequestMapping(path = "/{country}/{state}/{county}/{city}")
    public Set<String> getCoordinates(@PathVariable String country, @PathVariable String state, @PathVariable String county, @PathVariable String city)
    {
        try
        {
            return locationToString(locationRepository.findLocationByCountryAndStateAndCountyAndCity(country,state,county,city), Location.class.getMethod(
                                                                                                                    "getCoordinatesString"));
        } catch (NoSuchMethodException e)
        {
            e.printStackTrace();
            return new HashSet<>();
        }
    }

    @RequestMapping("/getLocationByCountryAndState")
    public Set<Location> getLocationByCountryAndState(@RequestParam String cr, @RequestParam String st)
    {
       return locationRepository.findLocationByCountryAndState(cr, st);
    }

    private Set<String> locationToString(Collection<Location> locations, Method method)
    {
        Set<String> strings = new HashSet<>();
        locations.forEach(location ->
                          {
                              try
                              {
                                  strings.add((String) method.invoke(location));
                              } catch (IllegalAccessException | InvocationTargetException e)
                              {
                                  e.printStackTrace();
                              }
                          });
        return strings;
    }
}
