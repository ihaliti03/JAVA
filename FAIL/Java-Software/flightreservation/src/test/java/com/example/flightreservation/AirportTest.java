package com.example.flightreservation;

import com.example.flightreservation.pojo.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class AirportTest {

    @Test
    public void testGetters() {
        Airport airport = new Airport("Oslo International Airport", "Oslo", "Norway");
        assertEquals("Oslo International Airport", airport.getName());
        assertEquals("Oslo", airport.getCity());
        assertEquals("Norway", airport.getCountry());
    }
}
