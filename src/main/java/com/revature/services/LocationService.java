package com.revature.services;

import com.revature.dtos.CoordinatesPair;
import com.revature.models.Location;
import com.revature.repos.LocationRepository;
import org.springframework.stereotype.Service;
import java.util.Set;

@Service
public class LocationService {
    LocationRepository locationRepository;

    public LocationService(LocationRepository repository){
        this.locationRepository = repository;
    }
    public CoordinatesPair<Double,Double> getLatLonOfACity(String city, String state){
        Set<Location> locSet = locationRepository.findLocationByCityAndState(city.toLowerCase(),state.toLowerCase());
        return locSet.size()>1 ? null : new CoordinatesPair<>(locSet.toArray(new Location[0])[0].getLatitude(),locSet.toArray(new Location[0])[0].getLongitude());
    }
}
