package com.revature.services; 

import com.revature.dtos.CoordinatesPair;
import com.revature.models.Location;
import com.revature.repos.LocationRepository;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;
import org.mockito.InjectMocks;
import org.mockito.Mock;


import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.junit.Assert.*;

/** 
* LocationService Tester. 
* 
* @author Nicholas Recino
* @since Jun 13, 2021
* @version 1.0 
*/ 
public class LocationServiceTest {

    @InjectMocks
    LocationService sut;

    @Mock
    LocationRepository mockLocationRepository;
@Before
public void before() throws Exception { 
    openMocks(this);
}

@After
public void after() throws Exception { 
    sut = null;
    mockLocationRepository = null;
}


@Test
public void testGetLatLonOfACity() throws Exception {
    Set<Location> locationSet = new HashSet<>();
    Location loc = new Location();
    loc.setLatitude(39.739);
    loc.setLongitude(-104.990);
    locationSet.add(loc);
    when(mockLocationRepository.findLocationByCityAndState(any(),any())).thenReturn(locationSet);

    CoordinatesPair<Double,Double> expectedResult = new CoordinatesPair<>(39.739,-104.990);
    CoordinatesPair<Double,Double> actualResult = sut.getLatLonOfACity("test","city");

    assertEquals(expectedResult.getLatitude(),actualResult.getLatitude());
    assertEquals(expectedResult.getLongitude(), actualResult.getLongitude());

}

    @Test
    public void testGetLatLonOfMultipleCities() throws Exception {
        Set<Location> locationSet = new HashSet<>();
        Location loc = new Location();
        loc.setLatitude(39.739);
        loc.setLongitude(-104.990);
        Location loc2 = new Location();
        loc.setLatitude(39.729);
        loc.setLongitude(-104.990);
        Location loc3 = new Location();
        loc.setLatitude(39.731);
        loc.setLongitude(-104.990);
        locationSet.add(loc);
        locationSet.add(loc2);
        locationSet.add(loc3);
        when(mockLocationRepository.findLocationByCityAndState(any(),any())).thenReturn(locationSet);

        CoordinatesPair<Double,Double> actualResult = sut.getLatLonOfACity("test","city");

        assertNull(actualResult);

    }


} 
